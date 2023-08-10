import java.util.List;
import java.util.Set;

public class WorldDTO
{
    private List<EntityDTO> entityDTOSet;
    private List<RulesDTO> rulesDTOSet;
     private TerminationDTO terminationDTO;

    public WorldDTO(List<EntityDTO> entityDTOSet, List<RulesDTO> rulesDTOSet, TerminationDTO terminationDTO)
    {
        this.entityDTOSet = entityDTOSet;
        this.rulesDTOSet = rulesDTOSet;
        this.terminationDTO = terminationDTO;
    }
    public List<EntityDTO> getEntityDTOSet() {
        return entityDTOSet;
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
