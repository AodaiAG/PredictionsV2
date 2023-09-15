package pSystem;

import pDTOS.*;
import pDTOS.ActionsDTO.ActionDTO;
import pEntity.*;
import pEntity.Entity;
import pEnvironment.EnvironmentInstance;
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
import java.util.stream.Collectors;

import pExceptionHandler.PropertyExceptionHandler;
import pExceptionHandler.RuleExceptionHandler;

public class Engine implements IEngine
{
    private File currentXMLFilePath;
    private static boolean programRunning = true;
    private final Map<UUID, Simulation> simulations = new HashMap<>();
    public Random r = new Random();
    public World world;
    private WorldDTO worldBeforeChanging = null;
    private int numbOfThreads=2;
    Map<String, List<Integer>> entityPopulationHistory = new HashMap<>();
    private volatile boolean simulationRunning = true; // Add this flag
    private volatile boolean pauseSimulation = false;
    private volatile Integer currTicksAmount=0;


    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");

        return simulations.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), dateFormat.format(entry.getValue().getRunningDate())))
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
    }

    public Integer getCurrTicksAmount()
    {
        return currTicksAmount;
    }

    public void setCurrTicksAmount(Integer currTicksAmount) {
        this.currTicksAmount = currTicksAmount;
    }

    public int getNumThreads()
    {
        return numbOfThreads;
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
        for (Rule r : world.getRules()) {
            rulesDTOSet.add(convertRuleToDTO(r));
        }
        for (EnvironmentInstance env : world.getName2Env().values())
        {
            PropertyDTO pDto = convertPropertyToDTO(env.getEnvironmentProperty());
            EnvironmentDTO dto = new EnvironmentDTO(pDto);
            envSet.add(dto);
        }
        return new WorldDTO(entityDTOSet, envSet, rulesDTOSet, terminationDTO);
    }

    public WorldDTO getWorldBeforeChanging() {
        return worldBeforeChanging;
    }

    @Override
    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception {
        EnvironmentInstance environmentInstance = this.world.getName2Env().get(environmentDTO.getEnProperty().getNameOfProperty());
        try {
            environmentInstance.getEnvironmentProperty().getData().setNewValue(userValue);
            environmentInstance.getEnvironmentProperty().setRandomInitialize(false);
        } catch (Exception e) {
            throw e;
        }
    }

    //command #3
    @Override
    public UUID startSimulation()
    {
        try
        {
            World clonedWorld = world.clone();
            WorldDTO oldWorldDTO = convertWorldToDTO(clonedWorld);
            UUID simulationId = UUID.randomUUID();
            String reasonForTermination = runSimulation(clonedWorld);
            WorldDTO worldAfter = convertWorldToDTO(clonedWorld);
            Simulation simulation = new Simulation(oldWorldDTO, worldAfter,simulationId);
            simulation.setEntityPopulationHistory(this.entityPopulationHistory);
            Date currentDate = new Date(); // Replace this with the actual date you want to use
            simulation.setRunningDate(currentDate);
            simulation.setReasonForTermination(reasonForTermination);
            simulations.put(simulationId, simulation);
            return simulationId;
        }

        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public Boolean isWordNull()
    {
        //change!!
        return world == null;
    }

    @Override
    public Map<UUID, Simulation> getSimulations() {
        return this.simulations;
    }
        @Override
        public World cloneWorld()
        {
            return this.world.clone();
        }
    public String runSimulation(World clonedWorld)
    {
        double generatedProbability;
        generatedProbability = r.nextDouble();
        int ticksCounter = 0;
        Timer timer = new Timer();
        System.out.println(Thread.currentThread());
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                programRunning = false;

                timer.cancel();
            }
        };

        int ticksAmount = clonedWorld.getTerminationTicks();
        long delay = (long) clonedWorld.getTerminationSeconds() * 1000; // Delay in milliseconds (5 seconds)
        timer.schedule(task, delay);
        // Graph //

        entityPopulationHistory.clear();

        while ((ticksCounter < ticksAmount) &&simulationRunning)
        {
            for (Rule rule : clonedWorld.getRules())
            {
                rule.isActivated(clonedWorld.getEntities(), ticksCounter, generatedProbability);
                generatedProbability = r.nextDouble();
            }

            List<Entity> entityList = world.getEntities();

            // Iterate over each entity and save the population in its history list
            for (Entity entity : entityList)
            {
                String entityName = entity.getNameOfEntity(); // Get the name of the entity
                List<Integer> populationHistory = entityPopulationHistory.getOrDefault(entity.getNameOfEntity(), new ArrayList<>());
                populationHistory.add(entity.getEntities().size());
                entityPopulationHistory.put(entityName, populationHistory);
            }
            // if the user choses to pause
            while (pauseSimulation)
            {
                try
                {
                    Thread.sleep(100); // Sleep for a short time while paused
                } catch (InterruptedException e)
                {
                    // Handle interruption if needed
                }
            }

            ticksCounter++;
            currTicksAmount=ticksCounter;

        }


        timer.cancel(); // Cancel the timer when simulation is done
        if(ticksCounter == ticksAmount)
        {
         return "ticks";
        }
        return "seconds";
    }

    public void stopSimulation()
    {
        simulationRunning = false;
    }
    public void pauseSimulation()
    {
        pauseSimulation = true;
    }

    public void resumeSimulation()
    {
        pauseSimulation = false;
    }

    @Override
    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulationID) {
        Simulation simulation = simulations.get(simulationID);
        simulation.initQuantities();
        return simulation.getInitialQuantities(); //map of the old entities
    }

    @Override
    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName) {
        Simulation simulation = simulations.get(simulationID);
        try
        {
            Entity chosenEntity = findEntityAccordingName(this.world.getEntities(), chosenEntityName);
            simulation.initPropertyHistogramAndReturnValueCounts(chosenEntity, chosenPropertyName);
        }
        catch (Exception e)
        {
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

    public RulesDTO convertRuleToDTO(Rule rule)
    {

        int numberOfActions = rule.getActions().size();
        List<ActionDTO> actionDTOList=new ArrayList<>();
        for (Action action : rule.getActions())
        {
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
        return new PropertyDTO(property.getNameOfProperty(), property.isRandomInitialize(), property.getTypeString(), property.getData().from, property.getData().to, property.getData().getDataString(), property.getData().isRangeExist());
    }

    public void ParseXmlAndLoadWorld(File file) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            this.world = new World();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            if(doc.getElementsByTagName("PRD-thread-count").item(0)!=null)
            {
                this.numbOfThreads=Integer.parseInt(doc.getElementsByTagName("PRD-thread-count").item(0).getTextContent());

            }

            if(doc.getElementsByTagName("PRD-grid").getLength()>0)
            {
                setGridCoordinate(doc.getElementsByTagName("PRD-grid"));
            }


            NodeList worldList = doc.getElementsByTagName("PRD-world");

            Node worldNode = worldList.item(0);
            NodeList Everything = worldNode.getChildNodes();

            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEnvironmentFromFile(prdEvironment);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities);

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules, this.world);

            initTerminationTerms(doc);

            this.worldBeforeChanging = convertWorldToDTO(world);
            currentXMLFilePath =file;
            simulations.clear();

        } catch (Exception e)
        {
            throw e;
        }

    }

    void initTerminationTerms(Document doc)
    {
        if (doc.getElementsByTagName("PRD-by-ticks").item(0) != null)
        {
            String ticks = doc.getElementsByTagName("PRD-by-ticks").item(0).getAttributes().getNamedItem("count").getTextContent();
            this.world.setTerminationTicks(Integer.parseInt(ticks));

        }
        if (doc.getElementsByTagName("PRD-by-second").item(0) != null)
        {
            String seconds = doc.getElementsByTagName("PRD-by-second").item(0).getAttributes().getNamedItem("count").getTextContent();
            this.world.setTerminationSeconds(Integer.parseInt(seconds));

        }

        if(doc.getElementsByTagName("PRD-by-user").item(0) != null)
        {
            this.world.setTerminationByUser(true);
        }
    }

    void setGridCoordinate(NodeList list)
    {
        int gridRows;
        int gridCols;
        gridRows = Integer.parseInt(((Node) list.item(0).getAttributes().getNamedItem("rows")).getTextContent());
        gridCols = Integer.parseInt(((Node) list.item(0).getAttributes().getNamedItem("columns")).getTextContent());
        this.world.getGrid().initEntityInstancesCircularGrid(gridRows,gridCols);
    }

    private void initRulesFromFile(NodeList list, World world) throws Exception
    {
        String nameOfRule = "";
        try {
            RuleExceptionHandler ruleExceptionHandler = new RuleExceptionHandler();
            AuxiliaryMethods f = new AuxiliaryMethods(world);
            Rule justToCallFunction = new Rule();
            justToCallFunction.setFunctions(f);
            for (int i = 0; i < list.getLength(); i++)
            {
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
                    if (actionsListOfaRule.item(m).getNodeType() == Node.ELEMENT_NODE)
                    {
                        Action action = justToCallFunction.createAction(actionsListOfaRule.item(m));
                        newRule.getActions().add(action);
                    }
                }

                this.world.getRules().add(newRule);
            }
        } catch (Exception e) {
            throw new Exception("Problem occurred while Parsing xml at rule name " + nameOfRule + " reason/s:" + '\n' + e.getMessage());
        }
    }

    public void initEnvironmentFromFile(NodeList list) throws Exception
    {
        PropertyExceptionHandler exceptionHandler = new PropertyExceptionHandler();
        for (int i = 0; i < list.getLength(); i++) {
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
                    Property eN1 = initProperty(type, prdName, true, from, to, true, "1");
                    EnvironmentInstance environmentInstance = new EnvironmentInstance();
                    environmentInstance.setEnvironmentProperty(eN1);
                    world.getName2Env().put(prdName, environmentInstance);
                } else {
                    exceptionHandler.Handle(type, prdName, false, from, to, true, "1");
                    Property eN = initProperty(type, prdName, false, from, to, true, "1");
                    EnvironmentInstance environmentInstance = new EnvironmentInstance();
                    environmentInstance.setEnvironmentProperty(eN);
                    world.getName2Env().put(prdName, environmentInstance);
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void initEntitiesFromFile(NodeList list) throws Exception
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

                for (int j = 0; j < entityProperty.getLength(); j++)
                {
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
                        Property property = initProperty(type, prdName, isRange, from, to, false, initValue);
                        e1.getPropertiesOfTheEntity().add(property);
                    } else {
                        exceptionHandler.Handle(type, prdName, isRange, from, to, true, initValue);
                        Property property = initProperty(type, prdName, isRange, from, to, true, initValue);
                        Property propAdded;
                        propAdded = property;
                        e1.getPropertiesOfTheEntity().add(propAdded);
                    }
                }
                newEntity.setPropertiesOfTheEntity(e1.getPropertiesOfTheEntity());

                this.world.getEntities().add(newEntity);

            } catch (Exception e) {
                throw new Exception("Error at entity name: " + name + " " + e.getMessage());
            }
        }
    }

    public void createEntityPopulation(int popNumber, EntityDTO selectedentityDTO)
    {
        Entity entity = null;
        for(Entity en : this.world.getEntities())
        {
            if(en.getNameOfEntity().equals(selectedentityDTO.getName()))
            {
                entity = en;
                break;
            }
        }

        List<EntityInstance> entityInstances = new ArrayList<>();
        Set<Property> propOfEntity = entity.getPropertiesOfTheEntity();
        EntityInstance e1 = new EntityInstance();
        e1.setPropertiesOfTheEntity(propOfEntity);

        for (int m = 0; m < popNumber; m++)
        {
            EntityInstance added = e1.clone();

            for (Property p : added.getPropertiesOfTheEntity()) {
                boolean isInitRandom = p.isRandomInitialize();
                String initVal = p.getData().getDataString();
                if (isInitRandom) {
                    p.getData().calculateNewVal(initVal, true);
                }
            }

            entityInstances.add(added);
        }

        entity.setEntities(entityInstances);
        entity.setNumberOfInstances(popNumber);
    }

    Property initProperty(String type, String name, boolean isRange, String from, String to, boolean bool, String init) {

        Property res = new Property();
        res.setNameOfProperty(name);
        res.setRandomInitialize(bool);
        Data eD = new Data();
        eD.setDataType(DataType.valueOf(type.toUpperCase()));
        eD.setRangeExist(isRange);
        if (isRange) {
            eD.setFrom(from);
            eD.setTo(to);
        }
        eD.calculateNewVal(init, bool);
        res.setData(eD);
        return res;
    }

    @Override
    public World getWorld() {
        return this.world;
    }
}