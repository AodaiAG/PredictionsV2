package servlets.simulationResultsServlets;

import Requests.SimulationRequestExecuter.SimulationReadyForExecution;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pDTOS.WorldDTO;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "getWorldDTOAfterExecution",urlPatterns = "/get_dto_before")
public class getWorldDTOBeforeExecution extends HttpServlet
{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestId = req.getParameter("r_id");
        String executionId = req.getParameter("e_id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestId));
        SimulationReadyForExecution simulationReadyForExecution=simulationRequestExecuter.getUuidSimulationReadyForExecutionMap().get(UUID.fromString(executionId));
        WorldDTO worldDTO = simulationReadyForExecution.getSimulationResult().getWordAfterSimulation();
        // Convert the WorldDTO object to JSON
        String json = gson.toJson(worldDTO);
        // Set response content type to JSON
        resp.setContentType("application/json");
        // Write the JSON data to the response output stream
        resp.getWriter().write(json);
    }

}
