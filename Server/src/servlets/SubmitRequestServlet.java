package servlets;

import Requests.SimulationRequestDetails;
import Requests.RequestManager;
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

import static constants.Constants.*;

@WebServlet(name = "SubmitRequestServlet",urlPatterns = "/new_request")

public class SubmitRequestServlet extends HttpServlet
{

    private final String SIGN_UP_URL = "../signup/signup.html";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {

            String usernameFromSession = SessionUtils.getUsername(request);
            if (usernameFromSession == null)
            {
                //user is not logged in yet
                String usernameFromParameter = request.getParameter(USERNAME);
                if (usernameFromParameter == null || usernameFromParameter.isEmpty())
                {
                    //no username in session and no username in parameter -
                    //redirect back to the index page
                    //this return an HTTP code back to the browser telling it to load
                    response.sendRedirect(SIGN_UP_URL);
                }
                else
                {
                    usernameFromParameter = usernameFromParameter.trim();
                }

            }
            else
            {

                RequestManager requestManager=ServletUtils.getRequestManager(getServletContext());
                // Get the input stream from the request
                BufferedReader reader = request.getReader();
                StringBuilder jsonRequest = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                {
                    jsonRequest.append(line);
                }

                // Parse the JSON data using Gson into your Request object
                Gson gson = new Gson();
                SimulationRequestDetails simulationRequestDetailsObject = gson.fromJson(jsonRequest.toString(), SimulationRequestDetails.class);
                requestManager.addRequest(usernameFromSession, simulationRequestDetailsObject);
                response.getWriter().print("Request Submitted. ");

            }

        } catch (Exception e)
        {
            response.getWriter().write(e.getMessage());
        }
    }
}
