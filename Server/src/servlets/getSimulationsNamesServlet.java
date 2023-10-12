package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
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

@WebServlet(name = "getSimulationsNamesServlet",urlPatterns = "/get_simulation_names")
public class getSimulationsNamesServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Engine engine = ServletUtils.getEngine(getServletContext());
        Map<String, aSimulation> simulationMap = engine.getAllSimulations();

        if (simulationMap == null)
            return;

        List<String> listofSimulationNames = new ArrayList<>(simulationMap.keySet());
        String jsonResponse = gson.toJson(listofSimulationNames);

        // Write the JSON response to the response's output stream
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

}
