package servlets.threadManagerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pSystem.ThreadPoolManager.ThreadPoolManager;
import pSystem.engine.Engine;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "getWaitingTasksServlet",urlPatterns = "/get_waiting_tasks")
public class getWaitingTasksServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ServletUtils.getEngine(getServletContext());
        ThreadPoolManager threadPoolManager=engine.getThreadPoolManager();
        resp.getWriter().print(threadPoolManager.getWaitingSimulations());
    }
}
