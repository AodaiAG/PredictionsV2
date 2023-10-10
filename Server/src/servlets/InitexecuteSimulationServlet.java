package servlets;

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

@WebServlet(name = "InitexecuteSimulationServlet",urlPatterns = "/init_execute_simulation")
public class InitexecuteSimulationServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        UUID executionId= engine.setSimulationToBeExecuted(simulationRequestExecuter);
        resp.getWriter().print(executionId);

    }
}
