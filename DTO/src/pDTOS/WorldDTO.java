package pDTOS;

import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class WorldDTO {
    int gridRows;
    int gridCols;
    public int tickCounter;
    private List<EntityDTO> entityDTOSet;
    private List<RulesDTO> rulesDTOSet;
    private TerminationDTO terminationDTO;
    private List<EnvironmentDTO> environmentDTOS;
    private String sleepAmount="0";

    public WorldDTO(List<EntityDTO> entityDTOSet, List<EnvironmentDTO> env, List<RulesDTO> rulesDTOSet, TerminationDTO terminationDTO, int gridRows, int gridCols, int tickCounter,String sleepAmount)
    {
        this.entityDTOSet = entityDTOSet;
        this.rulesDTOSet = rulesDTOSet;
        this.terminationDTO = terminationDTO;
        this.environmentDTOS = env;
        this.gridRows = gridRows;
        this.gridCols = gridCols;
        this.tickCounter = tickCounter;
        this.sleepAmount=sleepAmount;

    }
    public TreeView<String> generateTreeViewForEnviVariables()
    {

        // Add EntityDTOs as child nodes

        // Add EnvironmentDTOs as child nodes
        TreeItem<String> environmentNode = new TreeItem<>("Environment Variables");
        for (EnvironmentDTO environmentDTO : environmentDTOS) {
            environmentNode.getChildren().add(environmentDTO.generateTreeViewForSummary());
        }
        environmentNode.setExpanded(true);
        return new TreeView<>(environmentNode);
    }
    public TreeView<String> generateTreeViewForSummary()
    {
        TreeItem<String> rootNode = new TreeItem<>("World");

        // Add EntityDTOs as child nodes
        TreeItem<String> entityNode = new TreeItem<>("Entities");
        for (EntityDTO entityDTO : entityDTOSet) {
            entityNode.getChildren().add(entityDTO.generateTreeViewForSummary());
        }
        rootNode.getChildren().add(entityNode);

        // Add TerminationDTO as a child node
        rootNode.getChildren().add(terminationDTO.generateTreeView());

        // Add EnvironmentDTOs as child nodes
        TreeItem<String> environmentNode = new TreeItem<>("Environment");
        for (EnvironmentDTO environmentDTO : environmentDTOS) {
            environmentNode.getChildren().add(environmentDTO.generateTreeViewForSummary());
        }
        rootNode.getChildren().add(environmentNode);
        return new TreeView<>(rootNode);
    }


    public TreeView<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>("World");

        // Add EntityDTOs as child nodes
        TreeItem<String> entityNode = new TreeItem<>("Entities");
        for (EntityDTO entityDTO : entityDTOSet) {
            entityNode.getChildren().add(entityDTO.generateTreeView());
        }
        rootNode.getChildren().add(entityNode);

        // Add RulesDTOs as child nodes
        TreeItem<String> rulesNode = new TreeItem<>("Rules");
        for (RulesDTO rulesDTO : rulesDTOSet) {
            rulesNode.getChildren().add(rulesDTO.generateTreeView());
        }

        rootNode.getChildren().add(rulesNode);

        // Add TerminationDTO as a child node
        rootNode.getChildren().add(terminationDTO.generateTreeView());

        // Add EnvironmentDTOs as child nodes
        TreeItem<String> environmentNode = new TreeItem<>("Environment");
        for (EnvironmentDTO environmentDTO : environmentDTOS) {
            environmentNode.getChildren().add(environmentDTO.generateTreeView());
        }
        rootNode.getChildren().add(environmentNode);
        TreeItem<String> grid = new TreeItem<>("Grid");
        grid.getChildren().add(new TreeItem<>(gridRows + "X" + gridCols));
        TreeItem<String> sleepAmount = new TreeItem<>("Sleep Amount");
        sleepAmount.getChildren().add(new TreeItem<>(this.sleepAmount));
        rootNode.getChildren().add(sleepAmount);
        TreeView<String> treeView = new TreeView<>(rootNode);
        return treeView;
    }

    public List<EntityDTO> getEntityDTOSet() {
        return entityDTOSet;
    }

    public void setEntityDTOSet(List<EntityDTO> entityDTOSet) {
        this.entityDTOSet = entityDTOSet;
    }

    public List<EnvironmentDTO> getEnvironmentDTOS() {
        return environmentDTOS;
    }

    //    public VBox generateVBox()
//    {
//
//    }
    public void setEnvironmentDTOS(List<EnvironmentDTO> environmentDTOS) {
        this.environmentDTOS = environmentDTOS;
    }

    public List<RulesDTO> getRulesDTOSet() {
        return rulesDTOSet;
    }

    public void setRulesDTOSet(List<RulesDTO> rulesDTOSet) {
        this.rulesDTOSet = rulesDTOSet;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }

    public void setTerminationDTO(TerminationDTO terminationDTO) {
        this.terminationDTO = terminationDTO;
    }
    public int getNumberOfEntityInstancesBefore(EntityDTO entityDTO)
    {
        for(EntityDTO entityDTO1:entityDTOSet)
        {
            if(entityDTO1.getName().equals(entityDTO.getName()))
            {
                return entityDTO1.getInstancesDTOS().size();
            }
        }
        return 0;
    }

    public int getNumberOfEntityInstancesAfter(EntityDTO entityDTO)
    {

        for(EntityDTO entityDTO1:getEntityDTOSet())
        {
            if(entityDTO1.getName().equals(entityDTO.getName()))
            {
                return entityDTO1.getInstancesDTOS().size();
            }
        }
        return 0;
    }
}