package System;

import DTOS.*;
import Environment.EnvironmentInstance;
import Expression.AuxiliaryMethods;
import Rules.ActionTypes.*;
import Rules.Rule;
import Rules.Activation;
import org.w3c.dom.*;
import Entity.EntityInstance;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import Entity.Property;
import Entity.Data;
import Entity.DataType;
import Entity.Entity;
import ExceptionHandler.PropertyExceptionHandler;
import ExceptionHandler.RuleExceptionHandler;

public class Engine implements IEngine
{
    File currentXMLfilePath;
    private static boolean programRunning = true;
    private final Map<UUID, Simulation> simulations = new HashMap<>();
    public Random r = new Random();
    public World world;
    private WorldDTO worldBeforeChanging = null;

    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");

        return simulations.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), dateFormat.format(entry.getValue().getRunningDate())))
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
    }


    public WorldDTO convertWorldToDTO()
    {
        World world = this.world;
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
        for (EnvironmentInstance env : world.getName2Env().values()) {
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
            Engine iEngine=new Engine();
            iEngine.ParseXmlAndLoadWorld(currentXMLfilePath);
            this.world=iEngine.getWorld();
            WorldDTO oldWorldDTO = convertWorldToDTO();
            UUID simulationId = UUID.randomUUID();
            String reasonForTermination = runSimulation();
            WorldDTO worldAfter = convertWorldToDTO();
            Simulation simulation = new Simulation(oldWorldDTO, worldAfter);
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
    public Boolean isWordNull() {
        return world == null;
    }

    @Override
    public Map<UUID, Simulation> getSimulations() {
        return this.simulations;
    }

    public String runSimulation()
    {

        double generatedProbability;
        generatedProbability = r.nextDouble();
        int ticksCounter = 0;
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                programRunning = false;
                System.out.println("Time's up");
                timer.cancel();
            }
        };

        int ticksAmount = this.world.getTerminationTicks();
        long delay = (long) this.world.getTerminationSeconds() * 1000; // Delay in milliseconds (5 seconds)
        timer.schedule(task, delay);

        while (ticksCounter < ticksAmount && programRunning)
        {
            for (Rule rule : this.world.getRules()) {
                rule.isActivated(world.getEntities(), ticksCounter, generatedProbability);
                generatedProbability = r.nextDouble();
            }
            System.out.println("Ticks : " + ticksCounter + " population: " + world.getEntities().get(0).getNumberOfInstances());
            ticksCounter++;
        }
        timer.cancel(); // Cancel the timer when simulation is done
        if(ticksCounter == ticksAmount) {
         return "ticks";
        }
        return "seconds";
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
        try {
            Entity chosenEntity = findEntityAccordingName(this.world.getEntities(), chosenEntityName);
            simulation.initPropertyHistogramAndReturnValueCounts(chosenEntity, chosenPropertyName);
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
        List<String> actionNames = new ArrayList<>();
        int numberOfActions = rule.getActions().size();
        for (Action action : rule.getActions()) {
            actionNames.add(action.getNameOfAction());
        }
        return new RulesDTO(rule.getNameOfRule(), rule.getActivation().getTicks(), rule.getActivation().getProbability(), numberOfActions, actionNames);
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
            NodeList worldList = doc.getElementsByTagName("PRD-world");

            Node worldNode = worldList.item(0);
            NodeList Everything = worldNode.getChildNodes();

            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEvironmentFromFile(prdEvironment);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities);

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules, this.world);

            String ticks = doc.getElementsByTagName("PRD-by-ticks").item(0).getAttributes().getNamedItem("count").getTextContent();
            String seconds = doc.getElementsByTagName("PRD-by-second").item(0).getAttributes().getNamedItem("count").getTextContent();
            this.world.setTerminationTicks(Integer.parseInt(ticks));
            this.world.setTerminationSeconds(Integer.parseInt(seconds));
            this.worldBeforeChanging = convertWorldToDTO();
            currentXMLfilePath=file;
            simulations.clear();
        } catch (Exception e)
        {
            throw e;
        }

    }

    private void initRulesFromFile(NodeList list, World world) throws Exception {
        String nameOfRule = "";
        try {
            RuleExceptionHandler ruleExceptionHandler = new RuleExceptionHandler();
            AuxiliaryMethods f = new AuxiliaryMethods(world);
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
                        Action action = justToCallFunction.CreateAction(actionsListOfaRule.item(m));

                        newRule.getActions().add(action);
                    }
                }

                this.world.getRules().add(newRule);
            }
        } catch (Exception e) {
            throw new Exception("Problem occurred while Parsing xml at rule name " + nameOfRule + " reason/s:" + '\n' + e.getMessage());
        }
    }

    public void initEvironmentFromFile(NodeList list) throws Exception {
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

    public void initEntitiesFromFile(NodeList list) throws Exception {
        String name = new String();

        PropertyExceptionHandler exceptionHandler = new PropertyExceptionHandler();
        for (int i = 0; i < list.getLength(); i++) {

            try {
                Entity newEntity = new Entity();
                Node item = list.item(i);
                Element el = (Element) item;
                name = item.getAttributes().getNamedItem("name").getTextContent();
                newEntity.setNameOfEntity(name);
                String population = ((Element) item).getElementsByTagName("PRD-population").item(0).getTextContent();
                try {
                    exceptionHandler.checkIfValueMatchesType(population, "decimal");
                } catch (Exception e1) {
                    throw new RuntimeException("Population number is not numeric!");
                }
                int popNumber = Integer.parseInt(population);
                newEntity.setNumberOfInstances(popNumber);

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

                // create collection of entities
                List<EntityInstance> first = new ArrayList<>();
                Set<Property> propOfEntity = e1.getPropertiesOfTheEntity();
                newEntity.setPropertiesOfTheEntity(propOfEntity);


                for (int m = 0; m < popNumber; m++) {

                    EntityInstance added = e1.clone();

                    for (Property p : added.getPropertiesOfTheEntity()) {
                        boolean isInitRandom = p.isRandomInitialize();
                        String initVal = p.getData().getDataString();
                        if (isInitRandom) {
                            p.getData().calculateNewVal(initVal, true);
                        }
                    }

                    first.add(added);
                }

                newEntity.setEntities(first);
                this.world.getEntities().add(newEntity);
            } catch (Exception e) {
                throw new Exception("Error at entity name: " + name + " " + e.getMessage());
            }

        }


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

    private World getWorld() {
        return this.world;
    }

}