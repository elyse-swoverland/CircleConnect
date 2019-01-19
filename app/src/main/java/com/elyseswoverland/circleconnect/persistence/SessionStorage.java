package com.elyseswoverland.circleconnect.persistence;

import com.elyseswoverland.circleconnect.models.Session;

public class SessionStorage {
    private Session session;

    public Session getSession() { return session; }

    public void setSession(final Session session) {
        this.session = session;
    }

    public boolean hasToken() {
        return session != null;
    }

    public String getToken() {
        if (session != null) {
            return session.getToken();
        } else {
            return "";
        }
    }

    public void clearSession() {
        setSession(null);
    }

}
