package com.training360.hostel.room;

import java.math.BigDecimal;

public class Room {

    private Long id;
    private Integer floor;
    private Integer number;
    private Integer capacity;
    private BigDecimal netBasePrice;
    private String Description;
    private Long hostelId;

    public Room() {
    }

    public Room(NewRoom newRoom) {
        this.floor = newRoom.getFloor();
        this.number = newRoom.getNumber();
        this.capacity = newRoom.getCapacity();
        this.netBasePrice = newRoom.getNetBasePrice();
        this.Description = newRoom.getDescription();
        this.hostelId = newRoom.getHostelId();
    }

    public Long getId() {
        return id;
    }

    public Integer getFloor() {
        return floor;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public BigDecimal getNetBasePrice() {
        return netBasePrice;
    }

    public String getDescription() {
        return Description;
    }

    public Long getHostelId() {
        return hostelId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setNetBasePrice(BigDecimal netBasePrice) {
        this.netBasePrice = netBasePrice;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setHostelId(Long hostelId) {
        this.hostelId = hostelId;
    }
}
