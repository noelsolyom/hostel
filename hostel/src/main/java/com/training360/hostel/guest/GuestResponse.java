package com.training360.hostel.guest;

public class GuestResponse {
    private boolean ok;
    private Guest guest;
    private String message;

    public GuestResponse(boolean ok, Guest guest, String message) {
        this.ok = ok;
        this.guest = guest;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public Guest getGuest() {
        return guest;
    }

    public String getMessage() {
        return message;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
