package com.quickfix.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProviderAvailability {
    private int availabilityId, providerId;
    private LocalDate availableDate;
    private LocalTime startTime, endTime;
    private boolean available;
    public int getAvailabilityId() { return availabilityId; }
    public void setAvailabilityId(int availabilityId) { this.availabilityId = availabilityId; }
    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }
    public LocalDate getAvailableDate() { return availableDate; }
    public void setAvailableDate(LocalDate availableDate) { this.availableDate = availableDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
