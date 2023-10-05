//package components.Results.ResultsScreenController;
//
////import application.manager.UserInterfaceManager;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.ListView;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.layout.AnchorPane;
//import pSystem.engine.SimulationResult;
//
//import java.util.UUID;
//
//public class ResultsScreenController
//{
//
//    int tabCounter=0;
//
//    @FXML
//    private ListView<UUID> simulationListView;
//    @FXML
//    private TabPane tabPane;
//
//    private SimulationResult selectedSimulationResult;
//
//    private UserInterfaceManager uiManager;
//    @FXML
//    private AnchorPane mainAnchor;
//    public void initialize()
//    {
//        TabPane tabPane = uiManager.getTabPane();
//
//        AnchorPane.setTopAnchor(tabPane, 0.0);
//        AnchorPane.setBottomAnchor(tabPane, 0.0);
//        AnchorPane.setLeftAnchor(tabPane, 0.0);
//        AnchorPane.setRightAnchor(tabPane, 0.0);
//        // Add the TabPane to the mainAnchor
//        mainAnchor.getChildren().add(tabPane);
//
//
//        // Get the list of tabs
//        ObservableList<Tab> tabs = tabPane.getTabs();
//
//        // Select the last added tab
//        if (!tabs.isEmpty()) {
//            tabPane.getSelectionModel().select(tabs.size() - 1);
//        }
//    }
//
//
//      public void refreshScreen()
//      {
//          this.initialize();
//      }
//    public Tab createAndAddNewTab(TabPane tabPane)
//    {
//        Tab tab = new Tab("Simulation " + tabCounter);
//        tabPane.getTabs().add(tab);
//        tabCounter++;
//        return tab;
//
//    }
//
//
//    public static ResultsScreenController getInstance()
//    {
//        return Holder.INSTANCE;
//    }
//
//    // Rest of your code...
//
//    // Private static inner class to hold the instance
//    private static class Holder
//    {
//        private static final ResultsScreenController INSTANCE = new ResultsScreenController();
//    }
//
//
//
//
//public ResultsScreenController()
//    {
//        uiManager = UserInterfaceManager.INSTANCE;
//
//    }
//
//}
