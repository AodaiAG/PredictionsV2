package servlets;

import Requests.RequestManager;
import Requests.SimulationRequest;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "HandleRequestStatus", urlPatterns = "/handle_request")
public class HandleRequestStatus extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestManager requestManager = ServletUtils.getRequestManager(this.getServletContext());
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
        SimulationRequest userSimulationRequests=requestManager.getRequestUserTwoUUID(usernameFromSession,uuidRequest);
        userSimulationRequests.setRequestStatus(requestState);

    }
}
