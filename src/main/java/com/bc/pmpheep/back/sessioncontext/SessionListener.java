package com.bc.pmpheep.back.sessioncontext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SessionContext.AddSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        SessionContext.DelSession(session);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        this.sessionDestroyed(event);
        this.sessionCreated(event);
    }

}
