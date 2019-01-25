package com.training360.hostel.hostel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class HostelDAOCRUDImpl implements HostelDAOCRUD {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HostelDAOCRUDImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public HostelResponse createHostel(NewHostel newHostel) {

        Hostel hostel = new Hostel(newHostel);

        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            Long id;

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO hostel (name, phone, country, city, street, zip_code) VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,hostel.getName());
                ps.setString(2, hostel.getPhone());
                ps.setString(3, hostel.getCountry());
                ps.setString(4, hostel.getCity());
                ps.setString(5, hostel.getStreet());
                ps.setString(6, hostel.getZipCode());

                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                id = keyHolder.getKey().longValue();
            } else {
                throw new NullPointerException("Generated KeyHolder is null.");
            }

            hostel.setId(id);
            hostel.setActive(true);

            return new HostelResponse(true, hostel,"Hostel has been created.");

        } catch (DataAccessException | NullPointerException e) {
            return new HostelResponse(false, hostel, "Creating hostel failed. " + e.getMessage());
        }
    }

    @Override
    public HostelResponse getHostelById(Long hostelId) {
        try {
            return new HostelResponse(true, jdbcTemplate.queryForObject(
                    "SELECT id, name, phone, country, city, street, zip_code, active FROM hostel WHERE id = ?;",
                    new HostelRowMapper(), hostelId), "Hostel found.");
        } catch (DataAccessException dae) {
            return new HostelResponse(false, null, "Hostel not found. Hostel id: "+ hostelId + ".");
        }
    }

    @Override
    public HostelResponse getHostelByName(String hostelName) {
        try {
            return new HostelResponse(true, jdbcTemplate.queryForObject(
                    "SELECT id, name, phone, country, city, street, zip_code, active FROM hostel WHERE name = ?;",
                    new HostelRowMapper(), hostelName), "Hostel found.");
        } catch (DataAccessException dae) {
            return new HostelResponse(false, null, "Hostel not found. Hostel name: "+ hostelName + ".");
        }
    }

    @Override
    public HostelResponse updateHostel(Hostel hostel) {
        try {
            jdbcTemplate.update(
                    "update hostel set name=?, phone=?, country=?, city=?, street=?, zip_code=? " +
                            "where id=?;", hostel.getName(), hostel.getPhone(), hostel.getCountry(), hostel.getCity(),
                    hostel.getStreet(), hostel.getZipCode(), hostel.getId());

            return new HostelResponse(true, hostel, "Updating hostel successful. id: "+ hostel.getId() + ".");
        } catch (DataAccessException dae) {
            return new HostelResponse(false, hostel, "Updating hostel failed. id: "+ hostel.getId() + ".");
        }
    }

    @Override
    public HostelResponse deactivateHostelById(Long hostelId) {
        try {
            jdbcTemplate.update(
                    "update hostel set active=0 where id=?;", hostelId);

            return new HostelResponse(true, getHostelById(hostelId).getHostel(), "Deactivating hostel successful. id: "+ hostelId + ".");
        } catch (DataAccessException dae) {
            return new HostelResponse(false, null, "Deactivating hostel failed. id: "+ hostelId + ".");
        }
    }

    @Override
    public List<Hostel> listHostels() {
        return jdbcTemplate.query("SELECT id, name, phone, country, city, street, zip_code, active FROM hostel ORDER BY name;",
                new HostelRowMapper());
    }

    private static class HostelRowMapper implements RowMapper<Hostel> {
        @Override
        public Hostel mapRow(ResultSet resultSet, int i) throws SQLException {

            Hostel hostel = new Hostel();

            hostel.setId(resultSet.getLong("id"));
            hostel.setName(resultSet.getString("name"));
            hostel.setPhone(resultSet.getString("phone"));
            hostel.setCountry(resultSet.getString("country"));
            hostel.setCity(resultSet.getString("city"));
            hostel.setStreet(resultSet.getString("street"));
            hostel.setZipCode(resultSet.getString("zip_code"));
            hostel.setActive(resultSet.getBoolean("active"));

            return hostel;
        }
    }
}
