package com.training360.hostel.guest;

import java.util.List;

public interface GuestService {
    GuestResponse createGuest(NewGuest newGuest);
    GuestResponse getGuestById(Long guestId);
    GuestResponse updateGuest(Guest guest);
    GuestResponse deleteGuestById(Long guestId);
    List<Guest> listGuests();
}
