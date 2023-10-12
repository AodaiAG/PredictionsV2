package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdministratorActivationServlet", urlPatterns = "/admin-activation")
public class AdministratorActivationServlet extends HttpServlet {
    private boolean isAdminActive = false;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("mode");
        System.out.println(param);
        if ("check".equals(param)) {
            boolean isAdministratorActive = checkAdministratorStatus();
            response.setContentType("text/plain");
            response.getWriter().write(String.valueOf(isAdministratorActive));
            System.out.println(isAdministratorActive);
        } else if ("activated".equals(param)) {
            setAdministratorActive(true);
            response.setContentType("text/plain");
            response.getWriter().write("Administrator activated.");
            System.out.println(isAdminActive);
        } else if ("deactivated".equals(param)) {
            setAdministratorActive(false);
            response.setContentType("text/plain");
            response.getWriter().write("Administrator deactivated.");
            System.out.println(isAdminActive);
        } else {
            // Invalid endpoint, return an error response
            response.setContentType("text/plain");
            response.getWriter().write("Invalid endpoint.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // Function to check administrator status
    private synchronized boolean checkAdministratorStatus() {
        return isAdminActive;
    }

    // Function to activate the administrator
    private synchronized void setAdministratorActive(boolean adminMode) {
        isAdminActive = adminMode;
    }
}