package pDTOS;

import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class WorldDTO
{
    private List<EntityDTO> entityDTOSet;

    private List<RulesDTO> rulesDTOSet;

    private TerminationDTO terminationDTO;

    private List<EnvironmentDTO> environmentDTOS;

    public WorldDTO(List<EntityDTO> entityDTOSet, List<EnvironmentDTO> env, List<RulesDTO> rulesDTOSet, TerminationDTO terminationDTO) {
        this.entityDTOSet = entityDTOSet;
        this.rulesDTOSet = rulesDTOSet;
        this.terminationDTO = terminationDTO;
        this.environmentDTOS = env;
    }

    public TreeView<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>("World");

        // Add EntityDTOs as child nodes
        TreeItem<String> entityNode = new TreeItem<>("Entities");
        for (EntityDTO entityDTO : entityDTOSet)
        {
            entityNode.getChildren().add(entityDTO.generateTreeView());
        }
        rootNode.getChildren().add(entityNode);

        // Add RulesDTOs as child nodes
        TreeItem<String> rulesNode = new TreeItem<>("Rules");
        for (RulesDTO rulesDTO : rulesDTOSet)
        {
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

        TreeView<String> treeView = new TreeView<>(rootNode);
        return treeView;
    }

    public void setEnvironmentDTOS(List<EnvironmentDTO> environmentDTOS) {
        this.environmentDTOS = environmentDTOS;
    }

    public List<EntityDTO> getEntityDTOSet() {
        return entityDTOSet;
    }

    public List<EnvironmentDTO> getEnvironmentDTOS() {
        return environmentDTOS;
    }

    public void setEntityDTOSet(List<EntityDTO> entityDTOSet) {
        this.entityDTOSet = entityDTOSet;
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
}