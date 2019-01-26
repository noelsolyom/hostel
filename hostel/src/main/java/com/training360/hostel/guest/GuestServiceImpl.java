package com.training360.hostel.guest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private GuestDAOCRUDImpl guestDAOCRUDImpl;

    public GuestServiceImpl(GuestDAOCRUDImpl guestDAOCRUDImpl) {
        this.guestDAOCRUDImpl = guestDAOCRUDImpl;
    }

    @Override
    public GuestResponse createGuest(NewGuest newGuest) {

        Guest guest = new Guest(newGuest);

        try {
            validateGuestInput(guest);
            return guestDAOCRUDImpl.createGuest(newGuest);

        } catch (IllegalArgumentException iae) {
            return new GuestResponse(false, guest, iae.getMessage());
        }
    }

    @Override
    public GuestResponse getGuestById(Long guestId) {
        if (guestId == null || guestId < 1L) {
            return new GuestResponse(false, null, "Guest id cannot be null or less than 1.");
        }
        return guestDAOCRUDImpl.getGuestById(guestId);
    }

    @Override
    public GuestResponse updateGuest(Guest guest) {
        if (guest == null || guest.getId() == null || guest.getId() < 1) {
            return new GuestResponse(false, guest, "Guest or ID cannot be null or less than 1.");
        }

        try {
            GuestResponse guestResponse = guestDAOCRUDImpl.getGuestById(guest.getId());

            if (!guestResponse.isOk()) {
                throw new IllegalArgumentException("Guest not found. id: " + guest.getId());
            }

            validateGuestInput(guest);

            return guestDAOCRUDImpl.updateGuest(guest);

        } catch (IllegalArgumentException iae) {
            return new GuestResponse(false, guest, iae.getMessage());
        }
    }

    @Override
    public GuestResponse deleteGuestById(Long guestId) {
        if (guestId == null || guestId < 1L) {
            return new GuestResponse(false, null, "Guest id cannot be null or less than 1.");
        }
        try {
            GuestResponse guestResponse = guestDAOCRUDImpl.getGuestById(guestId);
            if (!guestResponse.isOk()) {
                throw new IllegalArgumentException("Guest not found. id: " + guestId);
            }
            return guestDAOCRUDImpl.deleteGuest(guestId);
        } catch (IllegalArgumentException iae) {
            return new GuestResponse(false, null, iae.getMessage());
        }
    }

    @Override
    public List<Guest> listGuests() {
        return guestDAOCRUDImpl.listGuests();
    }

    private static void validateGuestInput(Guest guest) {
        if (guest.getSurname() == null || guest.getSurname().trim().equals("")) {
            throw new IllegalArgumentException("Guest surname must be filled!");
        }
        if (guest.getForename() == null || guest.getSurname().trim().equals("")) {
            throw new IllegalArgumentException("Guest forename number must be filled!");
        }
        if (guest.getPhone() == null || guest.getSurname().trim().equals("")) {
            throw new IllegalArgumentException("Guest phone must be filled!");
        }
    }
}
