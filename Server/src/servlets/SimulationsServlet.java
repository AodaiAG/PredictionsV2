package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pDTOS.SimulationDTO;
import pSystem.engine.Engine;
import pSystem.engine.aSimulation;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SimulationsList",urlPatterns = "/simulations")
public class SimulationsServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        response.setContentType("application/json");
        List<SimulationDTO> simulationDTOS=new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Engine engine = ServletUtils.getEngine(getServletContext());
        Map<String, aSimulation> simulationMap=engine.getAllSimulations();

        for ( aSimulation aSimulation:simulationMap.values())
        {
            simulationDTOS.add(engine.convertSimulationToDTO(aSimulation));
        }

        String jsonResponse = gson.toJson(simulationDTOS);
        System.out.println(jsonResponse);
        try (PrintWriter out = response.getWriter())
        {
            out.println(jsonResponse);
        }






    }
}
