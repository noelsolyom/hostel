package com.training360.hostel.guest;

import java.util.List;

public interface GuestDAOCRUD {
    GuestResponse createGuest(NewGuest newGuest);
    GuestResponse getGuestById(Long guestId);
    GuestResponse updateGuest(Guest guest);
    GuestResponse deleteGuest(Long guestId);
    List<Guest> listGuests();
}
