package com.training360.hostel.guest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class GuestDAOCRUDImpl implements GuestDAOCRUD {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GuestDAOCRUDImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GuestResponse createGuest(NewGuest newGuest) {

        Guest guest = new Guest(newGuest);

        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            Long id;

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO guest (surname, forename, phone) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, guest.getSurname());
                ps.setString(2, guest.getForename());
                ps.setString(3, guest.getPhone());

                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                id = keyHolder.getKey().longValue();
            } else {
                throw new NullPointerException("Generated KeyHolder is null.");
            }

            guest.setId(id);

            return new GuestResponse(true, guest,"Guest has been created.");

        } catch (DataAccessException | NullPointerException e) {
            return new GuestResponse(false, guest, "Creating guest failed. " + e.getMessage());
        }
    }

    @Override
    public GuestResponse getGuestById(Long guestId) {
        try {
            return new GuestResponse(true, jdbcTemplate.queryForObject(
                    "SELECT id, surname, forename, phone FROM guest WHERE id = ?;",
                    new GuestRowMapper(), guestId), "Hostel found.");
        } catch (DataAccessException dae) {
            return new GuestResponse(false, null, "Guest not found. Guest id: "+ guestId + ".");
        }
    }

    @Override
    public GuestResponse updateGuest(Guest guest) {
        try {
            jdbcTemplate.update(
                    "UPDATE guest SET surname=?, forename=?, phone=? WHERE id=?;",
                    guest.getSurname(), guest.getForename(), guest.getPhone(), guest.getId());

            return new GuestResponse(true, guest, "Updating guest successful. id: "+ guest.getId() + ".");
        } catch (DataAccessException dae) {
            return new GuestResponse(false, guest, "Updating guest failed. id: "+ guest.getId() + ".");
        }
    }

    @Override
    public GuestResponse deleteGuest(Long guestId) {
        try {
            jdbcTemplate.update(
                    "UPDATE guest SET surname=NULL, forename=NULL, phone=NULL WHERE id=?;", guestId);

            return new GuestResponse(true, null, "Deleting user successful. id: "+ guestId + ".");
        } catch (DataAccessException dae) {
            return new GuestResponse(false, null, "Deleting user failed. id: "+ guestId + ".");
        }
    }

    @Override
    public List<Guest> listGuests() {
        return jdbcTemplate.query("SELECT id, surname, forename, phone FROM guest ORDER BY surname;",
                new GuestRowMapper());
    }

    private static class GuestRowMapper implements RowMapper<Guest> {
        @Override
        public Guest mapRow(ResultSet resultSet, int i) throws SQLException {

            Guest guest = new Guest();

            guest.setId(resultSet.getLong("id"));
            guest.setSurname(resultSet.getString("surname"));
            guest.setForename(resultSet.getString("forename"));
            guest.setPhone(resultSet.getString("phone"));

            return guest;
        }
    }
}
