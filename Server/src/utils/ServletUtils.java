package utils;


import Requests.RequestManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import pSystem.Engine;
import users.UserManager;

import static constants.Constants.*;


public class ServletUtils
{

	private static final String Engine_ATTRIBUTE_NAME = "engine";
	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String REQUEST_MANAGER_ATTRIBUTE_NAME = "requestManager";

	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exist -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object engineLock = new Object();
	private static final Object userManagerLock = new Object();
	private static final Object requestManagerLock = new Object();


	public static Engine getEngine(ServletContext servletContext)
	{

		synchronized (engineLock)
		{
			if (servletContext.getAttribute(Engine_ATTRIBUTE_NAME) == null)
			{
				System.out.println("Before init engine");
				Engine engine=new Engine();
				System.out.println("After init engine");
				servletContext.setAttribute(Engine_ATTRIBUTE_NAME, engine);
			}
		}
		return (Engine) servletContext.getAttribute(Engine_ATTRIBUTE_NAME);
	}

	public static UserManager getUserManager(ServletContext servletContext)
	{
		synchronized (userManagerLock)
		{
			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null)
			{
				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, getEngine(servletContext).getUserManager());
			}
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}

	public static RequestManager getRequestManager(ServletContext servletContext)
	{
		synchronized (requestManagerLock)
		{
			if (servletContext.getAttribute(REQUEST_MANAGER_ATTRIBUTE_NAME) == null)
			{
				servletContext.setAttribute(REQUEST_MANAGER_ATTRIBUTE_NAME, getEngine(servletContext).getRequestManager());
			}
		}
		return (RequestManager) servletContext.getAttribute(REQUEST_MANAGER_ATTRIBUTE_NAME);
	}

	public static int getIntParameter(HttpServletRequest request, String name)
	{
		String value = request.getParameter(name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException numberFormatException) {
			}
		}
		return INT_PARAMETER_ERROR;
	}
}
