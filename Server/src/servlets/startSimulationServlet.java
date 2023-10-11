package servlets;

import Requests.SimulationRequestExecuter.SimulationReadyForExecution;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.ThreadPoolManager.SimulationTask;
import pSystem.ThreadPoolManager.ThreadPoolManager;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "startSimulationServlet",urlPatterns = "/start_execution")

public class startSimulationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            Engine engine = ServletUtils.getEngine(getServletContext());
            String requestId = req.getParameter("r_id");
            String executionId = req.getParameter("e_id");
            SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestId));
            ThreadPoolManager threadPoolManager = engine.getThreadPoolManager();
            SimulationTask simulationTask=new SimulationTask(engine,simulationRequestExecuter,UUID.fromString(executionId));
             threadPoolManager.submitThreadTask(simulationTask);
            //engine.executeSimulation(simulationRequestExecuter,UUID.fromString(executionId));

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
