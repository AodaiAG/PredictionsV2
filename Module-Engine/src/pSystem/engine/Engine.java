package pSystem.engine;

import Requests.RequestManager;
import Requests.SimulationRequestDetails;
import Requests.SimulationRequestExecuter.SimulationReadyForExecution;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.*;
import pDTOS.*;
import pDTOS.ActionsDTO.ActionDTO;
import pEntity.*;
import pEntity.Entity;
import pEnvironment.EnvironmentInstance;
import pExceptionHandler.ExceptionHandler;
import pExpression.AuxiliaryMethods;
import pRules.pActionTypes.*;
import pRules.Rule;
import pRules.Activation;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import pExceptionHandler.PropertyExceptionHandler;
import pExceptionHandler.RuleExceptionHandler;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.SimulationExecutionHelper;
import pSystem.ThreadPoolManager.ThreadPoolManager;
import users.UserManager;

public class Engine implements IEngine
{
    private static boolean programRunning = true;
    private final Map<UUID, SimulationResult> simulationResults = new HashMap<>();
    private final Map<String, aSimulation> AllSimulations = new HashMap<>();
    private Map<UUID,SimulationRequestExecuter> UUIdTORequestExecuter=new HashMap<>();
    private UserManager userManager=new UserManager();
    private RequestManager requestManager=new RequestManager();
    public Random r = new Random();
    public World originalWorld;
    public World FileWorld;
    Map<String, List<Integer>> entityPopulationHistory = new HashMap<>();
    private File currentXMLFilePath;
    private WorldDTO worldBeforeChanging = null;
    private AuxiliaryMethods f;
    private int numbOfThreads = 1;
    private volatile Integer currTicksAmount = 0;
    ThreadPoolManager threadPoolManager=new ThreadPoolManager();

    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        return simulationResults.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), dateFormat.format(entry.getValue().getRunningDate())))
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
    }

    public ThreadPoolManager getThreadPoolManager()
    {
        return threadPoolManager;
    }


    public UserManager getUserManager()
    {
        return userManager;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public Integer getCurrTicksAmount()
    {
        return currTicksAmount;
    }

    public Map<String, aSimulation> getAllSimulations()
    {
        return AllSimulations;
    }

    public void setCurrTicksAmount(Integer currTicksAmount) {
        this.currTicksAmount = currTicksAmount;
    }

    public int getNumThreads() {
        return numbOfThreads;
    }

    public SimulationDTO convertSimulationToDTO(aSimulation aSimulation)
    {
        return new SimulationDTO(convertWorldToDTO(aSimulation.getWorld()),aSimulation.getNameofSimulation());
    }

    @Override
    public WorldDTO convertWorldToDTO(World world)
    {
        List<EntityDTO> entityDTOSet = new ArrayList<>();
        List<RulesDTO> rulesDTOSet = new ArrayList<>();
        List<EnvironmentDTO> envSet = new ArrayList<>();
        TerminationDTO terminationDTO = new TerminationDTO(world.getTerminationTicks(), world.getTerminationSeconds());
        for (Entity e : world.getEntities()) {
            entityDTOSet.add(convertEntityToDTO(e));
        }
        for (Rule r : world.getRules())
        {
            rulesDTOSet.add(convertRuleToDTO(r));
        }
        for (EnvironmentInstance env : world.getName2Env().values()) {
            PropertyDTO pDto = convertPropertyToDTO(env.getEnvironmentProperty());
            EnvironmentDTO dto = new EnvironmentDTO(pDto);
            envSet.add(dto);
        }
        return new WorldDTO(entityDTOSet, envSet, rulesDTOSet, terminationDTO, world.getGrid().getNumRows(), world.getGrid().getNumCols(), world.ticksCounter);
    }

    public WorldDTO getWorldBeforeChanging() {
        return worldBeforeChanging;
    }

    @Override
    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception
    {
        EnvironmentInstance environmentInstance = this.originalWorld.getName2Env().get(environmentDTO.getEnProperty().getNameOfProperty());
        try
        {
            environmentInstance.getEnvironmentProperty().getData().setNewValue(userValue);
            environmentInstance.getEnvironmentProperty().setRandomInitialize(false);
            environmentInstance.setInitByUser(true);
        } catch (Exception e)
        {
            throw e;
        }
    }
    public void setDataToEnvironmentVarGivenWorld(EnvironmentDTO environmentDTO, String userValue,World world) throws Exception
    {
        EnvironmentInstance environmentInstance = world.getName2Env().get(environmentDTO.getEnProperty().getNameOfProperty());
        try
        {
            environmentInstance.getEnvironmentProperty().getData().setNewValue(userValue);
            environmentInstance.getEnvironmentProperty().setRandomInitialize(false);
            environmentInstance.setInitByUser(true);
        } catch (Exception e)
        {
            throw e;
        }
    }
    public void initEnviromentVariables()
    {
        for(EnvironmentInstance environmentInstance:originalWorld.getName2Env().values())
        {
            environmentInstance.randomlyInitEnvironmentData();
        }
    }
    public void initEnviromentVariablesToWorld(World world)
    {
        for(EnvironmentInstance environmentInstance:world.getName2Env().values())
        {
            environmentInstance.randomlyInitEnvironmentData();
        }
    }

    @Override
    public UUID startSimulation( SimulationConditions simulationConditions, Consumer<String> consumer, EntityWrapper entityWrapper) {
        return null;
    }

    //command #3



        // wanted
        public void executeSimulation(SimulationRequestExecuter requestExecuter,UUID simToExecute)
        {
         try
         {

             System.out.println("in startExecution in engine");
             SimulationReadyForExecution simulationReadyForExecution=requestExecuter.getUuidSimulationReadyForExecutionMap().get(simToExecute);
            SimulationExecutionHelper simulationExecutionHelper=simulationReadyForExecution.getSimulationExecutionHelper();
            World originalWorld=simulationReadyForExecution.getWorld();
            initEnviromentVariablesToWorld(originalWorld) ;//
            World clonedWorld = originalWorld.clone();
            World toBeExecutedWorld = originalWorld.clone();
            f.setWorld(clonedWorld);
            WorldDTO oldWorldDTO = convertWorldToDTO(clonedWorld);
            clonedWorld.initCoordinates();
            UUID simulationId = UUID.randomUUID();
            String reasonForTermination = runS(clonedWorld, simulationExecutionHelper);
            WorldDTO worldAfter = convertWorldToDTO(clonedWorld);
            SimulationResult simulationResult = new SimulationResult(oldWorldDTO, worldAfter, simulationId);
            simulationResult.setWorldTobeExecuted(toBeExecutedWorld);
            simulationResult.setEntityPopulationHistory(simulationExecutionHelper.getEntityPopulationHistory());
            Date currentDate = new Date(); // Replace this with the actual date you want to use
            simulationResult.setRunningDate(currentDate);
            simulationResult.setReasonForTermination(reasonForTermination);
            simulationResults.put(simulationId, simulationResult);
            simulationReadyForExecution.setSimulationResult(simulationResult);
            simulationReadyForExecution.setExecutionFinshed(true);
            requestExecuter.getSimulationResultUUID().add(simulationId);
         }
        catch (Exception e)
        {

        }
    }




    public String runS(World clonedWorld ,SimulationExecutionHelper simulationExecutionHelper)
    {
        SimulationConditions simulationConditions=simulationExecutionHelper.getSimulationConditions();
        Consumer<String> consumer=simulationExecutionHelper.getConsumer();
        EntityWrapper entityWrapper=simulationExecutionHelper.getEntityWrapper();
        entityPopulationHistory = new HashMap<>();
        double generatedProbability;
        generatedProbability = r.nextDouble();
        clonedWorld.ticksCounter = 0;
        Timer timer = new Timer();
        System.out.println(Thread.currentThread());
        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                programRunning = false;

                timer.cancel();
            }
        };
        long startTime = System.nanoTime(); // Record the start time
        int ticksAmount = clonedWorld.getTerminationTicks();
        long delay = (long) clonedWorld.getTerminationSeconds() * 1000; // Delay in milliseconds (5 seconds)
        timer.schedule(task, delay);
        // Graph //

        entityPopulationHistory.clear();
        boolean ticksAsTermination = true;

        while (ticksAsTermination && simulationConditions.getSimulationRunning())
        {
            //move
            clonedWorld.moveAllInstances();

            //check if ticks
            for (Rule rule : clonedWorld.getRules())
            {
                rule.isActivated(clonedWorld.ticksCounter, clonedWorld.getEntities(), clonedWorld.ticksCounter, generatedProbability);
                generatedProbability = r.nextDouble();
            }

            List<Entity> entityList = clonedWorld.getEntities();

            // Iterate over each entity and save the population in its history list
            for (Entity entity : entityList)
            {

                if(clonedWorld.ticksCounter%100==0)
                {
                    String entityName = entity.getNameOfEntity(); // Get the name of the entity
                    List<Integer> populationHistory = entityPopulationHistory.getOrDefault(entity.getNameOfEntity(), new ArrayList<>());
                    populationHistory.add(entity.getEntities().size());
                    entityPopulationHistory.put(entityName, populationHistory);
                }

                Optional<ObservableEntity> existingEntity = entityWrapper.getEntityList()
                        .stream()
                        .filter(e -> e.getName().equals(entity.getNameOfEntity()))
                        .findFirst();
                if (existingEntity.isPresent()) {
                    // Update the existing entity's population
                    existingEntity.get().setPopulation(String.valueOf(entity.getEntities().size()));
                } else {
                    // Create a new entity and add it to the wrapper
                    ObservableEntity newEntity = new ObservableEntity();
                    newEntity.setName(entity.getNameOfEntity());
                    newEntity.setPopulation(String.valueOf(entity.getEntities().size()));
                    entityWrapper.addEntity(newEntity);
                }
            }
            // if the user choses to pause
            while (simulationConditions.getPauseSimulation())
            {
                try
                {
                    Thread.sleep(100);   // Sleep for a short time while paused
                    if(!simulationConditions.getSimulationRunning())
                    {
                        break;
                    }
                }
                catch (InterruptedException e)
                {
                    // Handle interruption if needed
                }
            }

            long currentTime = System.nanoTime();
            double runningTimeInSeconds = (currentTime - startTime) / 1_000_000_000.0;

            clonedWorld.ticksCounter++;
            ticksAsTermination = (ticksAmount > 0 && clonedWorld.ticksCounter < ticksAmount) || (ticksAmount == 0);
            currTicksAmount = clonedWorld.ticksCounter;

           simulationExecutionHelper.setPopAndTicks("Ticks: " + clonedWorld.ticksCounter + '\n' + "Running Time: " + runningTimeInSeconds + " seconds");
        }

        timer.cancel(); // Cancel the timer when simulation is done
        if (clonedWorld.ticksCounter == ticksAmount)
        {
            return "ticks";
        }
        return "seconds";
    }





    public void setWorldToFunctions(World world)
    {
        this.f.setWorld(world);
    }

    @Override
    public void setWorldFromExecution(SimulationResult simulationResult)
    {
        this.originalWorld= simulationResult.getWorldTobeExecuted();
        setWorldToFunctions(originalWorld);

    }



    @Override
    public Boolean isWordNull()
    {
        return originalWorld == null;
    }

    @Override
    public Map<UUID, SimulationResult> getSimulationResults() {
        return this.simulationResults;
    }

    @Override
    public World cloneWorld() {
        return this.originalWorld.clone();
    }

    public String runSimulation(World clonedWorld, SimulationConditions simulationConditions, Consumer<String> consumer, EntityWrapper entityWrapper)
    {
        entityPopulationHistory = new HashMap<>();
        double generatedProbability;
        generatedProbability = r.nextDouble();
        clonedWorld.ticksCounter = 0;
        Timer timer = new Timer();
        System.out.println(Thread.currentThread());
        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                programRunning = false;

                timer.cancel();
            }
        };
        long startTime = System.nanoTime(); // Record the start time
        int ticksAmount = clonedWorld.getTerminationTicks();
        long delay = (long) clonedWorld.getTerminationSeconds() * 1000; // Delay in milliseconds (5 seconds)
        timer.schedule(task, delay);
        // Graph //

        entityPopulationHistory.clear();
        boolean ticksAsTermination = true;

        while (ticksAsTermination && simulationConditions.getSimulationRunning())
        {
            //move
            clonedWorld.moveAllInstances();

            //check if ticks
            for (Rule rule : clonedWorld.getRules())
            {
                rule.isActivated(clonedWorld.ticksCounter, clonedWorld.getEntities(), clonedWorld.ticksCounter, generatedProbability);
                generatedProbability = r.nextDouble();
            }

            List<Entity> entityList = clonedWorld.getEntities();

            // Iterate over each entity and save the population in its history list
            for (Entity entity : entityList)
            {

                if(clonedWorld.ticksCounter%100==0)
                {
                    String entityName = entity.getNameOfEntity(); // Get the name of the entity
                    List<Integer> populationHistory = entityPopulationHistory.getOrDefault(entity.getNameOfEntity(), new ArrayList<>());
                    populationHistory.add(entity.getEntities().size());
                    entityPopulationHistory.put(entityName, populationHistory);
                }

                Optional<ObservableEntity> existingEntity = entityWrapper.getEntityList()
                        .stream()
                        .filter(e -> e.getName().equals(entity.getNameOfEntity()))
                        .findFirst();
                if (existingEntity.isPresent()) {
                    // Update the existing entity's population
                    existingEntity.get().setPopulation(String.valueOf(entity.getEntities().size()));
                } else {
                    // Create a new entity and add it to the wrapper
                    ObservableEntity newEntity = new ObservableEntity();
                    newEntity.setName(entity.getNameOfEntity());
                    newEntity.setPopulation(String.valueOf(entity.getEntities().size()));
                    entityWrapper.addEntity(newEntity);
                }
            }
            // if the user choses to pause
            while (simulationConditions.getPauseSimulation())
            {
                try
                {
                    Thread.sleep(100);   // Sleep for a short time while paused
                 if(!simulationConditions.getSimulationRunning())
                 {
                     break;
                 }
                }
                catch (InterruptedException e)
                {
                    // Handle interruption if needed
                }
            }

            long currentTime = System.nanoTime();
            double runningTimeInSeconds = (currentTime - startTime) / 1_000_000_000.0;

            clonedWorld.ticksCounter++;
            ticksAsTermination = (ticksAmount > 0 && clonedWorld.ticksCounter < ticksAmount) || (ticksAmount == 0);
            currTicksAmount = clonedWorld.ticksCounter;

            consumer.accept("Ticks: " + clonedWorld.ticksCounter + '\n' + "Running Time: " + runningTimeInSeconds + " seconds");
        }

        timer.cancel(); // Cancel the timer when simulation is done
        if (clonedWorld.ticksCounter == ticksAmount)
        {
            return "ticks";
        }
        return "seconds";
    }

    @Override
    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulationID) {
        SimulationResult simulationResult = simulationResults.get(simulationID);
        simulationResult.initQuantities();
        return simulationResult.getInitialQuantities(); //map of the old entities
    }

    @Override
    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName)
    {
        SimulationResult simulationResult = simulationResults.get(simulationID);
        try {
            Entity chosenEntity = findEntityAccordingName(this.originalWorld.getEntities(), chosenEntityName);
            simulationResult.initPropertyHistogramAndReturnValueCounts(chosenEntity, chosenPropertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Entity findEntityAccordingName(List<Entity> entities, String currentEntityName) throws Exception {
        for (Entity entity : entities) {
            if (entity.getNameOfEntity().equals(currentEntityName)) {
                return entity;
            }
        }
        throw new Exception("Entity not found");
    }

    public RulesDTO convertRuleToDTO(Rule rule) {
        int numberOfActions = rule.getActions().size();
        List<ActionDTO> actionDTOList = new ArrayList<>();
        for (Action action : rule.getActions()) {
            actionDTOList.add(action.convertToDTO());
        }

        return new RulesDTO(rule.getNameOfRule(), rule.getActivation().getTicks(), rule.getActivation().getProbability(), numberOfActions, actionDTOList);
    }

    public EntityDTO convertEntityToDTO(Entity entity) {
        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        List<EntityInstancesDTO> entityInstancesDTOS = new ArrayList<>();
        for (Property p : entity.getPropertiesOfTheEntity()) {
            propertyDTOs.add(convertPropertyToDTO(p));
        }

        for (EntityInstance entityInstance : entity.getEntities()) {
            List<PropertyDTO> instancePropertyDTOs = new ArrayList<>();

            for (Property property : entityInstance.getPropertiesOfTheEntity()) {
                instancePropertyDTOs.add(convertPropertyToDTO(property));
            }
            EntityInstancesDTO entityInstancesDTO = new EntityInstancesDTO(instancePropertyDTOs, entityInstance.getNameOfEntity());
            entityInstancesDTOS.add(entityInstancesDTO);
        }
        return new EntityDTO(entity.getNameOfEntity(), entity.getNumberOfInstances(), propertyDTOs, entityInstancesDTOS);
    }

    public PropertyDTO convertPropertyToDTO(Property property) {
        return new PropertyDTO(property.getNameOfProperty(), property.isRandomInitialize(), property.getTypeString(), property.getData().from, property.getData().to, property.getData().getDataString(), property.getData().isRangeExist(), property.getLastUnchangedTicks(),
        property.getSumTicksNoChange(), property.getNumOfTimesHasChanged());
    }

    public void ParseXmlAndLoadWorld(File file) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            this.originalWorld = new World();
            f = new AuxiliaryMethods(originalWorld);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            if (doc.getElementsByTagName("PRD-thread-count").item(0) != null) {
                this.numbOfThreads = Integer.parseInt(doc.getElementsByTagName("PRD-thread-count").item(0).getTextContent());

            }

            if (doc.getElementsByTagName("PRD-grid").getLength() > 0)
            {
                setGridCoordinate(doc.getElementsByTagName("PRD-grid"),originalWorld);
            }
            NodeList worldList = doc.getElementsByTagName("PRD-world");

            Node worldNode = worldList.item(0);
            NodeList Everything = worldNode.getChildNodes();

            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEnvironmentFromFile(prdEvironment,originalWorld);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities,originalWorld);

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules, originalWorld);

            initTerminationTerms(doc,originalWorld);

            this.worldBeforeChanging = convertWorldToDTO(originalWorld);
            currentXMLFilePath = file;
            simulationResults.clear();
            this.FileWorld=originalWorld.clone();

        } catch (Exception e)
        {
            throw e;
        }
    }

    public World ParseXmlAndLoadWorldFromDoc(Document doc) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            String simulationName;
            World originalWorld = new World();
            f = new AuxiliaryMethods(originalWorld);
            DocumentBuilder builder = factory.newDocumentBuilder();

            doc.getDocumentElement().normalize();

            if (doc.getElementsByTagName("PRD-thread-count").item(0) != null)
            {
                this.numbOfThreads = Integer.parseInt(doc.getElementsByTagName("PRD-thread-count").item(0).getTextContent());

            }
            simulationName=doc.getDocumentElement().getAttribute("name");

            if (doc.getElementsByTagName("PRD-grid").getLength() > 0)
            {
                setGridCoordinate(doc.getElementsByTagName("PRD-grid"),originalWorld);
            }



            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEnvironmentFromFile(prdEvironment,originalWorld);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities,originalWorld);

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules, originalWorld);

            initTerminationTerms(doc,originalWorld);

            this.worldBeforeChanging = convertWorldToDTO(originalWorld);

            simulationResults.clear();
            this.FileWorld=originalWorld.clone();
            AllSimulations.put(simulationName,new aSimulation(simulationName,originalWorld));
            return originalWorld;

        } catch (Exception e)
        {
            throw e;
        }
    }

    void initTerminationTerms(Document doc,World originalWorld)
    {
        if (doc.getElementsByTagName("PRD-by-ticks").item(0) != null) {
            String ticks = doc.getElementsByTagName("PRD-by-ticks").item(0).getAttributes().getNamedItem("count").getTextContent();
            originalWorld.setTerminationTicks(Integer.parseInt(ticks));
        }
        if (doc.getElementsByTagName("PRD-by-second").item(0) != null) {
            String seconds = doc.getElementsByTagName("PRD-by-second").item(0).getAttributes().getNamedItem("count").getTextContent();
            originalWorld.setTerminationSeconds(Integer.parseInt(seconds));

        }

        if (doc.getElementsByTagName("PRD-by-user").item(0) != null) {
            originalWorld.setTerminationByUser(true);
        }
    }
    @Override
    public void clearButtonPressed()
    {
        this.originalWorld=FileWorld.clone();
    }

    void setGridCoordinate(NodeList list,World originalWorld) throws Exception
    {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        int gridRows;
        int gridCols;
        try
        {
            gridRows = Integer.parseInt(((Node) list.item(0).getAttributes().getNamedItem("rows")).getTextContent());
            gridCols = Integer.parseInt(((Node) list.item(0).getAttributes().getNamedItem("columns")).getTextContent());
            originalWorld.getGrid().initEntityInstancesCircularGrid(gridRows, gridCols);
            exceptionHandler.checkIfInRange(String.valueOf(gridRows), "10" ,"100");
            exceptionHandler.checkIfInRange(String.valueOf(gridCols), "10" ,"100");
    }catch(Exception e)
        {
            throw new Exception("Problem while parsing grid, Reasons: " + e.getMessage());
        }
    }

    private void initRulesFromFile(NodeList list, World originalWorld) throws Exception
    {
        String nameOfRule = "";
        try {
            RuleExceptionHandler ruleExceptionHandler = new RuleExceptionHandler();
            Rule justToCallFunction = new Rule();
            justToCallFunction.setFunctions(f);
            for (int i = 0; i < list.getLength(); i++) {
                Rule newRule = new Rule();
                newRule.setFunctions(f);
                Node item = list.item(i);
                Element el = (Element) item;
                nameOfRule = ((Element) item).getAttribute("name");
                newRule.setNameOfRule(nameOfRule);
                Activation activation = new Activation();
                NodeList prdActivtionL = ((Element) item).getElementsByTagName("PRD-activation");
                if (prdActivtionL.getLength() != 0)//if not empty
                {
                    Node ticksNode = ((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("ticks");
                    if (ticksNode != null) {
                        String tickString = ticksNode.getTextContent();
                        ruleExceptionHandler.checkTicksActivation(tickString);
                        activation.setTicks(Integer.parseInt(tickString));
                    }
                    Node probNode = ((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("probability");
                    if (probNode != null) {
                        String probString = probNode.getTextContent();
                        ruleExceptionHandler.checkProbabilityActivation(probString);
                        activation.setProbability(Double.parseDouble(probString));
                    }
                }

                newRule.setActivation(activation);
                NodeList actionsListOfaRule = ((Element) item).getElementsByTagName("PRD-actions").item(0).getChildNodes();

                for (int m = 0; m < actionsListOfaRule.getLength(); m++) {
                    if (actionsListOfaRule.item(m).getNodeType() == Node.ELEMENT_NODE) {
                        Action action = justToCallFunction.createAction(actionsListOfaRule.item(m));
                        newRule.getActions().add(action);
                    }
                }

                originalWorld.getRules().add(newRule);
            }
        } catch (Exception e)
        {
            throw new Exception("Problem occurred while Parsing xml at rule name " + nameOfRule + " reason/s:" + '\n' + e.getMessage());
        }
    }

    public void initEnvironmentFromFile(NodeList list,World originalWorld) throws Exception
    {
        PropertyExceptionHandler exceptionHandler = new PropertyExceptionHandler();
        for (int i = 0; i < list.getLength(); i++)
        {
            try {
                Node item = list.item(i);
                Element el = (Element) item;
                String from = "";
                String to = "";

                String type = ((Element) item).getAttribute("type");
                String prdName = ((Element) item).getElementsByTagName("PRD-name").item(0).getTextContent();

                if ((((Element) item).getElementsByTagName("PRD-range").item(0)) != null) {
                    from = ((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                    to = ((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
                    exceptionHandler.Handle(type, prdName, true, from, to, true, "1");
                    Property eN1 = initProperty(type, prdName, true, from, to, true, "1",true);
                    EnvironmentInstance environmentInstance = new EnvironmentInstance();
                    environmentInstance.setEnvironmentProperty(eN1);
                    originalWorld.getName2Env().put(prdName, environmentInstance);
                } else {
                    exceptionHandler.Handle(type, prdName, false, from, to, true, "1");
                    Property eN = initProperty(type, prdName, false, from, to, true, "1",true);
                    EnvironmentInstance environmentInstance = new EnvironmentInstance();
                    environmentInstance.setEnvironmentProperty(eN);
                    originalWorld.getName2Env().put(prdName, environmentInstance);
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void initEntitiesFromFile(NodeList list,World originalWorld) throws Exception
    {
        String name = new String();

        PropertyExceptionHandler exceptionHandler = new PropertyExceptionHandler();
        for (int i = 0; i < list.getLength(); i++) {

            try {
                Entity newEntity = new Entity();
                Node item = list.item(i);
                Element el = (Element) item;
                name = item.getAttributes().getNamedItem("name").getTextContent();
                newEntity.setNameOfEntity(name);
                NodeList entityProperty = ((Element) item).getElementsByTagName("PRD-property");
                EntityInstance e1 = new EntityInstance();
                e1.setNameOfEntity(name);

                for (int j = 0; j < entityProperty.getLength(); j++) {
                    String from = "";
                    String to = "";
                    boolean isRange = false;
                    Node item2 = entityProperty.item(j);
                    Element el2 = (Element) item2;
                    String type = ((Element) item2).getAttribute("type");
                    String prdName = ((Element) item2).getElementsByTagName("PRD-name").item(0).getTextContent();

                    if (((Element) item2).getElementsByTagName("PRD-range").item(0) != null) {
                        from = ((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                        to = ((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
                        isRange = true;
                    }

                    String isRandom = ((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("random-initialize").getTextContent();
                    String initValue = "1"; // random value


                    if (isRandom.equals("false")) {
                        initValue = ((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                        exceptionHandler.Handle(type, prdName, isRange, from, to, false, initValue);
                        Property property = initProperty(type, prdName, isRange, from, to, false, initValue,false);
                        e1.getPropertiesOfTheEntity().add(property);
                    } else {
                        exceptionHandler.Handle(type, prdName, isRange, from, to, true, initValue);
                        Property property = initProperty(type, prdName, isRange, from, to, true, initValue,false);
                        Property propAdded;
                        propAdded = property;
                        e1.getPropertiesOfTheEntity().add(propAdded);
                    }
                }
                newEntity.setPropertiesOfTheEntity(e1.getPropertiesOfTheEntity());

               originalWorld.getEntities().add(newEntity);

            } catch (Exception e) {
                throw new Exception("Error at entity name: " + name + " " + e.getMessage());
            }
        }
    }

    public void createEntityPopulation(int popNumber, EntityDTO selectedentityDTO) throws Exception
    {
        int maxPopulationAmount = this.originalWorld.getGrid().getNumRows() * this.originalWorld.getGrid().getNumCols();

        if(popNumber <= maxPopulationAmount - originalWorld.getCurrentPopulationAmount() ) {
            originalWorld.setCurrentPopulationAmount(popNumber);
        }
        else {
            throw new Exception("Population limit exceeded. Max population allowed: " + maxPopulationAmount);
        }
        Entity entity = null;
        try {
            entity = findEntityAccordingName(this.originalWorld.getEntities(), selectedentityDTO.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<EntityInstance> entityInstances = new ArrayList<>();
        Set<Property> propOfEntity = entity.getPropertiesOfTheEntity();

        for (int m = 0; m < popNumber; m++)
        {
            EntityInstance added = entity.createNewInstance();
            entityInstances.add(added);
        }

        entity.setEntities(entityInstances);
        entity.setNumberOfInstances(popNumber);
    }

    public void createEntityPopulationGivenWorld(int popNumber, EntityDTO selectedentityDTO,World world) throws Exception
    {
        int maxPopulationAmount = world.getGrid().getNumRows() * world.getGrid().getNumCols();

        if(popNumber <= maxPopulationAmount - world.getCurrentPopulationAmount() ) {
            world.setCurrentPopulationAmount(popNumber);
        }
        else {
            throw new Exception("Population limit exceeded. Max population allowed: " + maxPopulationAmount);
        }
        Entity entity = null;
        try {
            entity = findEntityAccordingName(world.getEntities(), selectedentityDTO.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<EntityInstance> entityInstances = new ArrayList<>();
        Set<Property> propOfEntity = entity.getPropertiesOfTheEntity();

        for (int m = 0; m < popNumber; m++)
        {
            EntityInstance added = entity.createNewInstance();
            entityInstances.add(added);
        }

        entity.setEntities(entityInstances);
        entity.setNumberOfInstances(popNumber);
    }


    Property initProperty(String type, String name, boolean isRange, String from, String to, boolean bool, String init,Boolean isEnvVariable)
    {
        Property res = new Property();
        res.setNameOfProperty(name);
        res.setRandomInitialize(bool);
        Data eD = new Data();
        eD.setDataType(DataType.valueOf(type.toUpperCase()));
        eD.setRangeExist(isRange);
        if (isRange)
        {
            eD.setFrom(from);
            eD.setTo(to);
        }
        if(!isEnvVariable)
        {
            eD.calculateNewVal(init, bool);
        }
        res.setData(eD);
        return res;
    }

    @Override
    public World getOriginalWorld() {
        return this.originalWorld;
    }

    public aSimulation getSimulationFromName(String simulationName)
    {
        return AllSimulations.get(simulationName);
    }

    public void approveRequest(String userName, UUID request)
    {
        SimulationRequestDetails simulationRequestDetails =requestManager.getRequestUserTwoUUID(userName,request);
        aSimulation simulation=this.getSimulationFromName(simulationRequestDetails.getSimulationName());
        SimulationRequestExecuter simulationRequestExecuter=new SimulationRequestExecuter(simulationRequestDetails.getId(),simulation.clone());
        this.UUIdTORequestExecuter.put(simulationRequestDetails.getId(),simulationRequestExecuter);

    }

    public SimulationRequestExecuter getRequestExecutor(UUID id)
    {

        return this.UUIdTORequestExecuter.get(id);
    }

    public UUID setSimulationToBeExecuted(SimulationRequestExecuter simulationRequestExecuter)
    {
        SimulationReadyForExecution simulationReadyForExecution=new SimulationReadyForExecution();
        UUID uuid=UUID.randomUUID();
        simulationReadyForExecution.setWorld(simulationRequestExecuter.getCurrSimulation().getWorld().clone());
        simulationReadyForExecution.setExecutionId(uuid);
        simulationRequestExecuter.getUuidSimulationReadyForExecutionMap().put(uuid,simulationReadyForExecution);
        return uuid;
    }

    public void handleUserInteractionButtons(SimulationReadyForExecution simulationReadyForExecution,String userChoice)
    {
        switch (userChoice)
        {
            case "pause":
            {
                simulationReadyForExecution.getSimulationExecutionHelper().getSimulationConditions().setPauseSimulation(true);
                break;
            }
            case "resume":
            {
                simulationReadyForExecution.getSimulationExecutionHelper().getSimulationConditions().setPauseSimulation(false);
                break;
            }
            case "stop":
            {
                simulationReadyForExecution.getSimulationExecutionHelper().getSimulationConditions().setSimulationRunning(false);
                break;
            }
        }

    }
}