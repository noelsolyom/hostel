package com.training360.hostel.hostel;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HostelControllerImpl implements HostelController{

    private HostelServiceImpl hostelServiceImpl;

    public HostelControllerImpl(HostelServiceImpl hostelServiceImpl) {
        this.hostelServiceImpl = hostelServiceImpl;
    }

    @Override
    @RequestMapping(value = "/hostel", method = RequestMethod.POST)
    public HostelResponse createHostel(@RequestBody NewHostel newHostel) {
        return hostelServiceImpl.createHostel(newHostel);
    }

    @Override
    @RequestMapping(value = "/hostel/{id}", method = RequestMethod.GET)
    public HostelResponse getHostelById(@PathVariable String id) {
        try {
            return hostelServiceImpl.getHostelById(Long.parseLong(id));
        } catch (NumberFormatException nfe) {
            return new HostelResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/hostel", method = RequestMethod.GET)
    public HostelResponse getHostelByName(@RequestParam String name) {
        return hostelServiceImpl.getHostelByName(name);
    }

    @Override
    @RequestMapping(value = "/hostel/update", method = RequestMethod.POST)
    public HostelResponse updateHostel(@RequestBody Hostel hostel) {
        return hostelServiceImpl.updateHostel(hostel);
    }

    @Override
    @RequestMapping(value = "/hostel/delete/{hostelId}", method = RequestMethod.DELETE)
    public HostelResponse deactivateHostelById(@RequestParam String hostelId) {
        try {
            return hostelServiceImpl.deactivateHostelById(Long.parseLong(hostelId));
        } catch (NumberFormatException nfe) {
            return new HostelResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/hostels/all", method = RequestMethod.GET)
    public List<Hostel> listHostels() {
        return hostelServiceImpl.listHostels();
    }

    @Override
    @RequestMapping(value = "/hostels/open", method = RequestMethod.GET)
    public List<Hostel> listActiveHostels() {
        return hostelServiceImpl.listActiveHostels();
    }
}
