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
@WebServlet(name = "clearExecutionBtnServlet", urlPatterns = "/clear_btn")

public class clearExecutionBtnServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        simulationRequestExecuter.clearBtnPressed();

    }
}
