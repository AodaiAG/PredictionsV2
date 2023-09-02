package pDTOS;

import javafx.scene.control.TreeItem;

public class TerminationDTO
{
    private int terminationTicks;
    private int terminationSeconds;


    public TerminationDTO(int terminationTicks, int terminationSeconds) {
        this.terminationTicks = terminationTicks;
        this.terminationSeconds = terminationSeconds;
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
}