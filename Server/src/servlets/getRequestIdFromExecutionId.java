package servlets;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.engine.Engine;
import pSystem.engine.SimulationResult;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.UUID;
@WebServlet(name = "getRequestIdFromExecutionId",urlPatterns = "/get_req_from_exec")
public class getRequestIdFromExecutionId extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationResult simulationResult= engine.getSimulationResults().get(UUID.fromString(requestState));
        UUID executionId= simulationResult.getRequestId();
        resp.setContentType("text/plain");
        ServletOutputStream out = resp.getOutputStream();
        out.print(executionId.toString());
        out.close();
    }
}
