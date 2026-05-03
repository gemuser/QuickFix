package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final BookingStatusDAO statusDAO = new BookingStatusDAO();
    private final BookingStatusHistoryDAO historyDAO = new BookingStatusHistoryDAO();
    private final ProviderServiceDAO serviceDAO = new ProviderServiceDAO();
    private final NotificationService notificationService = new NotificationService();

    public int book(int customerId, int serviceId, LocalDate date, LocalTime time, String notes) throws Exception {
        com.quickfix.model.ProviderService service = serviceDAO.findById(serviceId);
        if (service == null) throw new IllegalArgumentException("Service not found.");
        Booking b = new Booking();
        b.setCustomerId(customerId); b.setProviderId(service.getProviderId()); b.setServiceId(serviceId);
        b.setStatusId(statusDAO.idByName("PENDING")); b.setBookingDate(date); b.setBookingTime(time); b.setNotes(notes);
        int id = bookingDAO.create(b);
        historyDAO.create(id, b.getStatusId(), customerId, "Booking requested");
        notificationService.notifyUser(service.getProviderId(), "New booking request", "A customer requested " + service.getServiceTitle());
        notificationService.notifyUser(customerId, "Booking confirmation", "Your booking request was submitted.");
        return id;
    }

    public void changeStatus(int bookingId, String status, int changedBy) throws Exception {
        Booking booking = bookingDAO.findById(bookingId);
        int statusId = statusDAO.idByName(status);
        bookingDAO.updateStatus(bookingId, statusId);
        historyDAO.create(bookingId, statusId, changedBy, "Status changed to " + status);
        if (booking != null) {
            notificationService.notifyUser(booking.getCustomerId(), "Booking status update", "Booking #" + bookingId + " is now " + status);
            notificationService.notifyUser(booking.getProviderId(), "Booking status update", "Booking #" + bookingId + " is now " + status);
        }
    }

    public List<Booking> customerBookings(int customerId) throws Exception { return bookingDAO.findByCustomer(customerId); }
    public List<Booking> providerBookings(int providerId) throws Exception { return bookingDAO.findByProvider(providerId); }
    public List<Booking> allBookings() throws Exception { return bookingDAO.findAll(); }
}
