package com.aniket.myofficeapp;

public class BookAppointmentModel {

    public String name;
    public String cancelled;
    public String confirmed;
    public String date;
    public String officialid;
    public String reason;
    public String usermail;

    public BookAppointmentModel(String name, String cancelled, String confirmed, String date, String officialid, String reason, String usermail) {
        this.name = name;
        this.cancelled = cancelled;
        this.confirmed = confirmed;
        this.date = date;
        this.officialid = officialid;
        this.reason = reason;
        this.usermail = usermail;
    }
}
