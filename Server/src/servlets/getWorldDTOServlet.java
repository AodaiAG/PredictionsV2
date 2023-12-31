package servlets;

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
import java.util.UUID;

@WebServlet(name = "getWorldDTOServlet", urlPatterns = "/get_world_dto")
public class getWorldDTOServlet extends HttpServlet
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        World world=simulationRequestExecuter.getCurrSimulation().getWorld();
        WorldDTO worldDTO = engine.convertWorldToDTO(world);
        // Convert the WorldDTO object to JSON
        String json = gson.toJson(worldDTO);
        // Set response content type to JSON
        resp.setContentType("application/json");
        // Write the JSON data to the response output stream
        resp.getWriter().write(json);
    }




}
