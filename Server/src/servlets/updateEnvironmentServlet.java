package servlets;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pDTOS.EnvironmentDTO;
import pSystem.engine.Engine;
import pSystem.engine.World;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "updateEnvironmentServlet",urlPatterns = "/update_env")
public class updateEnvironmentServlet extends HttpServlet
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        String userValue=req.getParameter("user_value");
        SimulationRequestExecuter simulationRequestExecuter = engine.getRequestExecutor(UUID.fromString(requestState));
        World world = simulationRequestExecuter.getCurrSimulation().getWorld();
        try (BufferedReader reader = req.getReader())
        {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                jsonBuilder.append(line);
            }
            EnvironmentDTO environmentDTO = gson.fromJson(jsonBuilder.toString(), EnvironmentDTO.class);
            engine.setDataToEnvironmentVarGivenWorld(environmentDTO,userValue,world);

        }
        catch (Exception e)
        {
            resp.getWriter().print(e.getMessage());
        }



    }

}