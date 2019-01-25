package com.training360.hostel.hostel;

import java.util.List;

public interface HostelService {
    HostelResponse createHostel(NewHostel newHostel);
    HostelResponse getHostelById(Long hostelId);
    HostelResponse getHostelByName(String hostelName);
    HostelResponse updateHostel(Hostel hostel);
    HostelResponse deactivateHostelById(Long hostelId);
    List<Hostel> listHostels();
    List<Hostel> listActiveHostels();
}
