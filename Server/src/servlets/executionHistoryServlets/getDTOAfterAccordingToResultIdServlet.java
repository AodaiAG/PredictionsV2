package servlets.executionHistoryServlets;

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
import pSystem.engine.SimulationResult;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
@WebServlet(name = "getDTOAfterAccordingToResultIdServlet",urlPatterns = "/dto_b_id")
public class getDTOAfterAccordingToResultIdServlet extends HttpServlet
{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String executionId = req.getParameter("e_id");
        SimulationResult simulationResult=engine.getSimulationResults().get(UUID.fromString(executionId));
        WorldDTO worldDTO = simulationResult.getWordAfterSimulation();
        // Convert the WorldDTO object to JSON
        String json = gson.toJson(worldDTO);
        // Set response content type to JSON
        resp.setContentType("application/json");
        // Write the JSON data to the response output stream
        resp.getWriter().write(json);
    }


}
