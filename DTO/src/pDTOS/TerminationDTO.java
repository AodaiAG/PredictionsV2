package pDTOS;

import javafx.scene.control.TreeItem;

import java.util.Objects;

public class TerminationDTO
{
    private int terminationTicks;
    private int terminationSeconds;
    private boolean isByUser;


    public TerminationDTO(int terminationTicks, int terminationSeconds)
    {
        this.terminationTicks = terminationTicks;
        this.terminationSeconds = terminationSeconds;
    }

    public TerminationDTO()
    {

    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminationDTO that = (TerminationDTO) o;
        return terminationTicks == that.terminationTicks &&
                terminationSeconds == that.terminationSeconds &&
                isByUser == that.isByUser;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(terminationTicks, terminationSeconds, isByUser);
    }

    @Override
    public String toString()
    {
        return ("ticks: "+terminationTicks+" Seconds: "+terminationSeconds+" UserTermination: "+ this.isByUser);
    }

    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>("Termination");

        // Add terminationTicks as a child node
        TreeItem<String> ticksNode = new TreeItem<>("Termination Ticks: " + terminationTicks);
        rootNode.getChildren().add(ticksNode);

        // Add terminationSeconds as a child node
        TreeItem<String> secondsNode = new TreeItem<>("Termination Seconds: " + terminationSeconds);
        rootNode.getChildren().add(secondsNode);

        return rootNode;
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

    public boolean isByUser() {
        return isByUser;
    }

    public void setByUser(boolean byUser) {
        isByUser = byUser;
    }
}