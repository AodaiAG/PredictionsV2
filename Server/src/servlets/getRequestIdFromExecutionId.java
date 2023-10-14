package servlets;

import Requests.RequestInfoHelper;
import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.omg.PortableInterceptor.RequestInfo;
import pSystem.engine.Engine;
import pSystem.engine.SimulationResult;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.UUID;
@WebServlet(name = "getRequestIdFromExecutionId",urlPatterns = "/get_req_from_exec")
public class getRequestIdFromExecutionId extends HttpServlet
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Engine engine = ServletUtils.getEngine(getServletContext());
        String requestState = req.getParameter("id");
        SimulationResult simulationResult= engine.getSimulationResults().get(UUID.fromString(requestState));
        RequestInfoHelper requestInfoHelper=new RequestInfoHelper();
        requestInfoHelper.setRequestExecutorId(simulationResult.getRequestExecutorId());
        requestInfoHelper.setRequestId(simulationResult.getRequestId());
        String json = gson.toJson(requestInfoHelper);
        // Set response content type to JSON
        resp.setContentType("application/json");
        // Write the JSON data to the response output stream
        resp.getWriter().write(json);
    }
}
