package com.quickfix.service;

import com.quickfix.dao.NotificationDAO;
import com.quickfix.model.Notification;
import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO = new NotificationDAO();

    public void notifyUser(int userId, String title, String message) throws Exception {
        Notification n = new Notification();
        n.setUserId(userId); n.setTitle(title); n.setMessage(message);
        notificationDAO.create(n);
        System.out.println("[Mock email] To user " + userId + ": " + title + " - " + message);
    }

    public List<Notification> recent(int userId) throws Exception {
        return notificationDAO.findByUser(userId);
    }
}
