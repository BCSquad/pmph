package com.bc.pmpheep.back.sessioncontext;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

@SuppressWarnings("all")
public class SessionContext {
    private static HashMap hashMap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            hashMap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            hashMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) hashMap.get(session_id);
    }
}
