package com.training360.hostel.hostel;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelServiceImpl implements HostelService {

    private HostelDAOCRUDImpl hostelDAOCRUDImpl;

    public HostelServiceImpl(HostelDAOCRUDImpl hostelDAOCRUDImpl) {

        this.hostelDAOCRUDImpl = hostelDAOCRUDImpl;
    }

    @Override
    public HostelResponse createHostel(NewHostel newHostel) {

        Hostel hostel = new Hostel(newHostel);

        try {

            validateHostelInput(hostel);

            if (!hostelDAOCRUDImpl.getHostelByName(hostel.getName()).isOk()) {

                return hostelDAOCRUDImpl.createHostel(newHostel);
            }
            throw new IllegalArgumentException("Hostel with name (" + newHostel.getName() + ") is already exist.");

        } catch (IllegalArgumentException iae) {
            return new HostelResponse(false, hostel, iae.getMessage());
        }
    }

    @Override
    public HostelResponse getHostelById(Long hostelId) {
        if (hostelId == null || hostelId < 1L) {
            return new HostelResponse(false, null, "Hostel id cannot be null or less than 1.");
        }
        return hostelDAOCRUDImpl.getHostelById(hostelId);
    }

    @Override
    public HostelResponse getHostelByName(String hostelName) {
        if (hostelName == null || hostelName.trim().equals("")) {
            return new HostelResponse(false, null, "Hostel name cannot be null or empty.");
        }

        hostelName = hostelName.replaceAll(" ", "");
        return hostelDAOCRUDImpl.getHostelByName(hostelName.replaceAll("_", " "));
    }

    @Override
    public HostelResponse updateHostel(Hostel hostel) {
        if (hostel == null || hostel.getId() == null) {
            return new HostelResponse(false, hostel, "Hostel or ID cannot be null.");
        }

        try {
            HostelResponse hostelResponse = hostelDAOCRUDImpl.getHostelById(hostel.getId());

            if (!hostelResponse.isOk()) {
                throw new IllegalArgumentException("Hostel not found. id: " + hostel.getId());
            }

            if (!hostelResponse.getHostel().isActive()) {
                throw new IllegalArgumentException("Cannot update deactivated hostel. id: " + hostel.getId());
            }

            validateHostelInput(hostel);

            if (hostelDAOCRUDImpl.getHostelByName(hostel.getName()).isOk() &&
                    hostelDAOCRUDImpl.getHostelByName(hostel.getName()).getHostel().getId() != hostel.getId()) {
                throw new IllegalArgumentException("Hostel with name (" + hostel.getName() + ") is already exist.");
            }

            return hostelDAOCRUDImpl.updateHostel(hostel);

        } catch (IllegalArgumentException iae) {
            return new HostelResponse(false, hostel, iae.getMessage());
        }
    }

    @Override
    public HostelResponse deactivateHostelById(Long hostelId) {
        if (hostelId == null || hostelId < 1L) {
            return new HostelResponse(false, null, "Hostel id cannot be null or less than 1.");
        }
        try {
            HostelResponse hostelResponse = hostelDAOCRUDImpl.getHostelById(hostelId);
            if (!hostelResponse.isOk()) {
                throw new IllegalArgumentException("Hostel not found. id: " + hostelId);
            }
            if (!hostelResponse.getHostel().isActive()) {
                throw new IllegalArgumentException("Hostel has already been deactivated. id: " + hostelId);
            }
            return hostelDAOCRUDImpl.deactivateHostelById(hostelId);
        } catch (IllegalArgumentException iae) {
            return new HostelResponse(false, null, iae.getMessage());
        }
    }

    @Override
    public List<Hostel> listHostels() {
        return hostelDAOCRUDImpl.listHostels();
    }

    @Override
    public List<Hostel> listActiveHostels() {
        return hostelDAOCRUDImpl.listHostels().stream().takeWhile(Hostel::isActive).collect(Collectors.toList());
    }

    private static void validateHostelInput(Hostel hostel) {
        if (hostel.getName() == null || hostel.getName().trim().equals("")) {
            throw new IllegalArgumentException("Hostel name must be filled!");
        }
        if (hostel.getPhone() == null || hostel.getPhone().trim().equals("")) {
            throw new IllegalArgumentException("Hostel phone number must be filled!");
        }
        if (hostel.getCountry() == null || hostel.getCountry().trim().equals("")) {
            throw new IllegalArgumentException("Hostel country must be filled!");
        }
        if (hostel.getCity() == null || hostel.getCity().trim().equals("")) {
            throw new IllegalArgumentException("Hostel city must be filled!");
        }
        if (hostel.getStreet() == null || hostel.getStreet().trim().equals("")) {
            throw new IllegalArgumentException("Hostel street must be filled!");
        }
        if (hostel.getZipCode() == null || hostel.getZipCode().trim().equals("")) {
            throw new IllegalArgumentException("Hostel zip code number must be filled!");
        }
    }
}
