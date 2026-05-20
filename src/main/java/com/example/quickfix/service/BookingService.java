package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final BookingStatusDAO statusDAO = new BookingStatusDAO();
    private final BookingStatusHistoryDAO historyDAO = new BookingStatusHistoryDAO();
    private final ProviderServiceDAO serviceDAO = new ProviderServiceDAO();
    private final NotificationService notificationService = new NotificationService();
    private final ProviderAvailabilityDAO availabilityDAO = new ProviderAvailabilityDAO();

    public int book(int customerId, int serviceId, LocalDate date, LocalTime time, String address, String notes) throws Exception {
        com.quickfix.model.ProviderService service = serviceDAO.findById(serviceId);
        if (service == null) throw new IllegalArgumentException("Service not found.");
        if (date.isBefore(LocalDate.now())) throw new IllegalArgumentException("Invalid Date/Time");
        if (address == null || address.trim().isEmpty()) throw new IllegalArgumentException("Address is required");
        if (!availabilityDAO.isProviderAvailable(service.getProviderId(), date, time)) throw new IllegalArgumentException("Provider unavailable at this time.");
        Booking b = new Booking();
        b.setCustomerId(customerId); b.setProviderId(service.getProviderId()); b.setServiceId(serviceId);
        b.setStatusId(statusDAO.idByName("PENDING")); b.setBookingDate(date); b.setBookingTime(time); b.setNotes(address.trim() + "\n\n" + (notes == null ? "" : notes.trim()));
        int id = bookingDAO.create(b);
        historyDAO.create(id, b.getStatusId(), customerId, "Booking requested");
        notificationService.notifyUser(service.getProviderId(), "New booking request", "A customer requested " + service.getServiceTitle());
        notificationService.notifyUser(customerId, "Booking confirmation", "Your booking request was submitted.");
        return id;
    }

    public void changeStatus(int bookingId, String status, int changedBy) throws Exception {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found.");
        if (!isValidTransition(booking.getStatusName(), status)) throw new IllegalArgumentException("Invalid status transition.");
        int statusId = statusDAO.idByName(status);
        bookingDAO.updateStatus(bookingId, statusId);
        historyDAO.create(bookingId, statusId, changedBy, "Status changed to " + status);
        notificationService.notifyUser(booking.getCustomerId(), "Booking " + statusLabel(status), "Booking #" + bookingId + " is now " + status);
        notificationService.notifyUser(booking.getProviderId(), "Booking " + statusLabel(status), "Booking #" + bookingId + " is now " + status);
    }

    public List<Booking> customerBookings(int customerId) throws Exception { return bookingDAO.findByCustomer(customerId); }
    public List<Booking> providerBookings(int providerId) throws Exception { return bookingDAO.findByProvider(providerId); }
    public List<Booking> allBookings() throws Exception { return bookingDAO.findAll(); }

    private boolean isValidTransition(String current, String next) {
        if (current == null || next == null) return false;
        if ("PENDING".equals(current)) return Arrays.asList("ACCEPTED", "REJECTED", "CANCELLED").contains(next);
        if ("ACCEPTED".equals(current)) return Arrays.asList("IN_PROGRESS", "CANCELLED").contains(next);
        if ("IN_PROGRESS".equals(current)) return "COMPLETED".equals(next);
        return false;
    }

    private String statusLabel(String status) {
        if ("ACCEPTED".equals(status)) return "Accepted";
        if ("REJECTED".equals(status)) return "Declined";
        return "status update";
    }
}
