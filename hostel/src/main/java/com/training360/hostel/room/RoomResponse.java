package com.training360.hostel.room;

public class RoomResponse {

    private boolean ok;
    private Room room;
    private String message;

    public RoomResponse(boolean ok, Room room, String message) {
        this.ok = ok;
        this.room = room;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public Room getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
