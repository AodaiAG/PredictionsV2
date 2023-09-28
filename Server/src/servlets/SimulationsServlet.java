package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.Engine;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
@WebServlet(name = "SimulationsList",urlPatterns = "/simulations")
public class SimulationsServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            Engine engine = ServletUtils.getEngine(getServletContext());
            String json = gson.toJson(engine.getSimulationList());
            out.println(json);
            out.flush();
        }
    }
}
