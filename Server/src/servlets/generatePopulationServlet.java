package servlets;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pSystem.engine.Engine;
import pSystem.engine.World;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "generatePopulationServlet",urlPatterns = "/add_population")
public class generatePopulationServlet extends HttpServlet
{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain"); // Set the content type to plain text
        PrintWriter out = resp.getWriter();
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        String population = req.getParameter("value");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        World world = simulationRequestExecuter.getCurrSimulation().getWorld();
        BufferedReader reader = req.getReader();
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
        {
            jsonRequest.append(line);
        }

            int popn = Integer.parseInt(population);
            EntityDTO entityDTO = gson.fromJson(jsonRequest.toString(), EntityDTO.class);
        try
        {
            engine.createEntityPopulationGivenWorld(popn,entityDTO,world);
            out.print("Population Added.");
        } catch (Exception e)
        {
           out.print("error : "+e.getMessage());
        }




    }

}
