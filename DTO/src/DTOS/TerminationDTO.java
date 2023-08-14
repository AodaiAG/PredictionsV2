package DTOS;

public class TerminationDTO
{
    private int terminationTicks;
    private int terminationSeconds;

    public TerminationDTO(int terminationTicks, int terminationSeconds)
    {
        this.terminationTicks = terminationTicks;
        this.terminationSeconds = terminationSeconds;
    }

    public int getTerminationTicks() {
        return terminationTicks;
    }

    public void setTerminationTicks(int terminationTicks) {
        this.terminationTicks = terminationTicks;
    }

    public int getTerminationSeconds() {
        return terminationSeconds;
    }

    public void setTerminationSeconds(int terminationSeconds) {
        this.terminationSeconds = terminationSeconds;
    }
}
