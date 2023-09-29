package utils;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import pSystem.Engine;
import pSystem.IEngine;

import static constants.Constants.*;


public class ServletUtils
{

	private static final String Engine_ATTRIBUTE_NAME = "engine";
	private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exist -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object chatManagerLock = new Object();

	public static Engine getEngine(ServletContext servletContext)
	{

		synchronized (userManagerLock)
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
//
//	public static ChatManager getChatManager(ServletContext servletContext)
//	{
//		synchronized (chatManagerLock) {
//			if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
//				servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
//			}
//		}
//		return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
//	}

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
