package com.$2012.web.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/*
 * session监听器
 *     监听session的创建
 */
public class SiteSessionListener implements HttpSessionListener {
	private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	public void sessionCreated(HttpSessionEvent event) {
		//System.out.println("*************" + event.getSession().getId());
		sessions.put(event.getSession().getId(), event.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		sessions.remove(event.getSession().getId());
	}
	
	public static HttpSession getSession(String sessionId) {
		return sessions.get(sessionId);
	}
	
	public static void removeSession(String sessionId) {
		if (sessions.containsKey(sessionId)) sessions.remove(sessionId);
	}

}
