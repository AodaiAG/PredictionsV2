package servlets.simulationExecutionServlets;

import Requests.SimulationRequestExecuter.SimulationReadyForExecution;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "handleUserinteractionServlet",urlPatterns = "/user_interaction")
public class handleUserinteractionServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestId = req.getParameter("r_id");
        String executionId = req.getParameter("e_id");
        String userchoice = req.getParameter("value");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestId));
        SimulationReadyForExecution simulationReadyForExecution=simulationRequestExecuter.getUuidSimulationReadyForExecutionMap().get(UUID.fromString(executionId));
        engine.handleUserInteractionButtons(simulationReadyForExecution,userchoice);


    }
}
