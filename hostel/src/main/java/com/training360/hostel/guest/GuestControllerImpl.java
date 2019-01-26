package com.training360.hostel.guest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GuestControllerImpl implements GuestController {

    private GuestServiceImpl guestServiceImpl;

    public GuestControllerImpl(GuestServiceImpl guestServiceImpl) {
        this.guestServiceImpl = guestServiceImpl;
    }

    @Override
    @RequestMapping(value = "/guest", method = RequestMethod.POST)
    public GuestResponse createGuest(@RequestBody NewGuest newGuest) {
        return guestServiceImpl.createGuest(newGuest);
    }

    @Override
    @RequestMapping(value = "/guest/{id}", method = RequestMethod.GET)
    public GuestResponse getGuestById(@PathVariable String id) {
        try {
            return guestServiceImpl.getGuestById(Long.parseLong(id));
        } catch (NumberFormatException nfe) {
            return new GuestResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/guest/update", method = RequestMethod.POST)
    public GuestResponse updateGuest(@RequestBody Guest guest) {
        return guestServiceImpl.updateGuest(guest);
    }

    @Override
    @RequestMapping(value = "/guest/delete/{guestId}", method = RequestMethod.DELETE)
    public GuestResponse deleteGuestById(@RequestParam String guestId) {
        try {
            return guestServiceImpl.deleteGuestById(Long.parseLong(guestId));
        } catch (NumberFormatException nfe) {
            return new GuestResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/guests", method = RequestMethod.GET)
    public List<Guest> listHostels() {
        return guestServiceImpl.listGuests();
    }
}
