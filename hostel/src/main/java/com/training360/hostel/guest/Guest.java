package com.training360.hostel.guest;

public class Guest {

    private Long id;
    private String surname;
    private String forename;
    private String phone;

    public Guest() {
    }

    public Guest(NewGuest newGuest) {
        this.surname = newGuest.getSurname();
        this.forename = newGuest.getForename();
        this.phone = newGuest.getPhone();
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
