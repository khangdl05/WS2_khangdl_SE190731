/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class SessionManager {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String account){
        return sessions.get(account);
    }

    public static void addSession(String account, HttpSession session){
        sessions.put(account, session);
    }

    public static void removeSession(String account){
        sessions.remove(account);
    }
}
