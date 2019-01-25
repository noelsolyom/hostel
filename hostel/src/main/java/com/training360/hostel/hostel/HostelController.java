package com.training360.hostel.hostel;

import java.util.List;

public interface HostelController {
    HostelResponse createHostel(NewHostel newHostel);
    HostelResponse getHostelById(String hostelId);
    HostelResponse getHostelByName(String hostelName);
    HostelResponse updateHostel(Hostel hostel);
    HostelResponse deactivateHostelById(String hostelId);
    List<Hostel> listHostels();
    List<Hostel> listActiveHostels();
}
