package com.example.asadmalik.parkinglifeapp;





public class Booking {
    private String BookingId;
    private String date;
    private String time;
    private String duration;


    public Booking(String bookingId, String date, String time, String duration) {
        BookingId = bookingId;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getBookingId() {
        return BookingId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }
}
