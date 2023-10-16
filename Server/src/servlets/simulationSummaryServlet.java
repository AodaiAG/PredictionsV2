package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.scene.control.TreeView;
import pDTOS.SimulationDTO;
import pSystem.engine.Engine;
import pSystem.engine.aSimulation;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "simulationSummaryServlet",urlPatterns = "/get_simulations_summary")
public class simulationSummaryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            Engine engine = ServletUtils.getEngine(getServletContext());
            TreeView treeView= engine.getAllSimulations().get();

            // Convert the TreeViewData to JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(treeViewData);

            // Set the response content type and write the JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            // Handle exceptions and return an error response if necessary
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
