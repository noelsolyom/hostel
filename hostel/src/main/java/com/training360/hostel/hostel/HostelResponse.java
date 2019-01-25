package com.training360.hostel.hostel;

public class HostelResponse {

    private boolean ok;
    private Hostel hostel;
    private String message;

    public HostelResponse(boolean ok, Hostel hostel, String message) {
        this.ok = ok;
        this.hostel = hostel;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public String getMessage() {
        return message;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
