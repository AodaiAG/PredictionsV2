package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pDTOS.WorldDTO;
import pSystem.Engine;
import pSystem.World;
import pSystem.aSimulation;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
@WebServlet(name = "SimulationsList",urlPatterns = "/simulations")
public class SimulationsServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        response.setContentType("application/json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Engine engine = ServletUtils.getEngine(getServletContext());
            Map<String, aSimulation> simulationMap=engine.getAllSimulations();
        for ( Map.Entry<String, aSimulation> entry : simulationMap.entrySet())
        {
            String key = entry.getKey();
            aSimulation aSimulation = entry.getValue();
            WorldDTO worldDTO=engine.convertWorldToDTO(aSimulation.getWorld());
            String jsonResponse = gson.toJson(worldDTO);
            System.out.println(jsonResponse);
            try (PrintWriter out = response.getWriter())
            {
                out.println(jsonResponse);
            }
        }


            //



    }
}
