package servlets;

import Requests.RequestManager;
import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AllocationServlet", urlPatterns = "/allocations")
public class AllocationsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestManager requestManager = ServletUtils.getRequestManager(this.getServletContext());
        String type = req.getParameter("type");
        if(type != null && !type.isEmpty())
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();


            if(type.equals("admin"))
            {
               List<SimulationRequestDetails> simulationRequestDetails = requestManager.getRequests();

                String jsonResponse = gson.toJson(simulationRequestDetails);
                try (PrintWriter out = resp.getWriter())
                {
                    out.println(jsonResponse);
                }
            }
            else if(type.equals("user"))
            {
                String userName = SessionUtils.getUsername(req);
               List<SimulationRequestDetails> userSimulationReq = requestManager.getUserRequests(userName);
                if(userSimulationReq != null)
                {
                    String jsonResponse = gson.toJson(userSimulationReq);
                    try (PrintWriter out = resp.getWriter())
                    {
                        out.println(jsonResponse);
                    }
                }
            }
        }
        else
        {
            resp.getWriter().println("invalid query parameter");
            return;
        }

    }
}
