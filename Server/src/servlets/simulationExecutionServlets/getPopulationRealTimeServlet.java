package servlets.simulationExecutionServlets;

import Requests.SimulationRequestExecuter.SimulationReadyForExecution;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.ObservableEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.SimulationExecutionHelper;
import javafx.collections.ObservableList;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
@WebServlet(name = "getPopulationRealTimeServlet",urlPatterns = "/population_progress")
public class getPopulationRealTimeServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestId = req.getParameter("r_id");
        String executionId = req.getParameter("e_id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestId));
        SimulationReadyForExecution simulationReadyForExecution=simulationRequestExecuter.getUuidSimulationReadyForExecutionMap().get(UUID.fromString(executionId));
        ObservableList<ObservableEntity> entityList = simulationReadyForExecution.getSimulationExecutionHelper().getEntityWrapper().getEntityList();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = gson.toJson(entityList);
        // Write the JSON response to the response's output stream
        try (PrintWriter out = resp.getWriter())
        {
            out.print(jsonResponse);
            out.flush();
        }

    }
}
