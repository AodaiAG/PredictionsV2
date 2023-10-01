package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import pSystem.Engine;
import pSystem.World;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

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
            Engine engine = ServletUtils.getEngine(getServletContext());
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
                } else
                {
                    //normalize the username value
                    usernameFromParameter = usernameFromParameter.trim();
                }

            }

        } catch (Exception e)
        {
            response.getWriter().write(e.getMessage());
        }
    }
}
