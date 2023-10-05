package servlets;

import Requests.RequestManager;
import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "HandleRequestStatus", urlPatterns = "/handle_request")
public class HandleRequestStatus extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestManager requestManager = ServletUtils.getRequestManager(this.getServletContext());
        Engine engine=ServletUtils.getEngine(getServletContext());
        String requestState= req.getParameter("status");
        String usernameFromSession= req.getParameter("username");
        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
        {
            jsonRequest.append(line);
        }
        UUID uuidRequest=gson.fromJson(jsonRequest.toString(),UUID.class);
        SimulationRequestDetails userDetailsSimulationRequests =requestManager.getRequestUserTwoUUID(usernameFromSession,uuidRequest);
        userDetailsSimulationRequests.setRequestStatus(requestState);
        if(requestState.equals("approved"))
        {
            engine.approveRequest(usernameFromSession,uuidRequest);
        }

    }
}
