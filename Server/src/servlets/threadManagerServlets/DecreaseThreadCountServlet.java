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

@WebServlet(name = "DecreaseThreadCountServlet",urlPatterns = "/de_thread_count")
public class DecreaseThreadCountServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String requestId = req.getParameter("value");
        try
        {
            int number=Integer.valueOf(requestId);
            Engine engine = ServletUtils.getEngine(getServletContext());
            ThreadPoolManager threadPoolManager=engine.getThreadPoolManager();
            threadPoolManager.decreaseThreadCount(number);
            resp.getWriter().print("Thread Count decreased.");
        }
        catch (Exception e)
        {
            resp.getWriter().print("Error,Thread count should be integer.");
        }
    }
}
