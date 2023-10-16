package servlets.threadManagerServlets;

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
import pSystem.engine.World;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "getSimulationSummaryServlet",urlPatterns = "/get_simulation_summary")

public class getSimulationSummaryServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        World world=simulationRequestExecuter.getCurrSimulation().getWorld();
        WorldDTO worldDTO =engine.convertWorldToDTO(world);
        String jsonResponse = gson.toJson(worldDTO);
        try (PrintWriter out = resp.getWriter())
        {
            out.println(jsonResponse);
        }


    }
}
