package com.quickfix.model;

import java.time.LocalDateTime;

public class Complaint {
    private int complaintId, bookingId, raisedBy, againstUserId;
    private String subject, description, status, adminResponse, raisedByName;
    private LocalDateTime createdAt;
    public int getComplaintId() { return complaintId; }
    public void setComplaintId(int complaintId) { this.complaintId = complaintId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getRaisedBy() { return raisedBy; }
    public void setRaisedBy(int raisedBy) { this.raisedBy = raisedBy; }
    public int getAgainstUserId() { return againstUserId; }
    public void setAgainstUserId(int againstUserId) { this.againstUserId = againstUserId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAdminResponse() { return adminResponse; }
    public void setAdminResponse(String adminResponse) { this.adminResponse = adminResponse; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getRaisedByName() { return raisedByName; }
    public void setRaisedByName(String raisedByName) { this.raisedByName = raisedByName; }
}
