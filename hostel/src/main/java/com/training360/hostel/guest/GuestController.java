package com.training360.hostel.guest;

import java.util.List;

public interface GuestController {
    GuestResponse createGuest(NewGuest newGuest);
    GuestResponse getGuestById(String guestId);
    GuestResponse updateGuest(Guest guest);
    GuestResponse deleteGuestById(String guestId);
    List<Guest> listHostels();
}
