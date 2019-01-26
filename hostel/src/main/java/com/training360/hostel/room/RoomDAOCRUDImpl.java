package com.training360.hostel.room;

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
public class RoomDAOCRUDImpl implements RoomDAOCRUD{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RoomDAOCRUDImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RoomResponse createRoom(NewRoom newRoom) {

        Room room = new Room(newRoom);

        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            Long id;

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO room (floor, number, capacity, net_base_price, description, hostel_id) VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1,room.getFloor());
                ps.setLong(2, room.getNumber());
                ps.setLong(3, room.getCapacity());
                ps.setBigDecimal(4, room.getNetBasePrice());
                ps.setString(5, room.getDescription());
                ps.setLong(6, room.getHostelId());

                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                id = keyHolder.getKey().longValue();
            } else {
                throw new NullPointerException("Generated KeyHolder is null.");
            }

            room.setId(id);

            return new RoomResponse(true, room,"Room has been created.");

        } catch (DataAccessException | NullPointerException e) {
            return new RoomResponse(false, room, "Creating room failed. " + e.getMessage());
        }
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        try {
            return new RoomResponse(true, jdbcTemplate.queryForObject(
                    "SELECT id, floor, number, capacity, net_base_price, description, hostel_id FROM room WHERE id = ?;",
                    new RoomRowMapper(), roomId), "Room found.");
        } catch (DataAccessException dae) {
            return new RoomResponse(false, null, "Room not found. Room id: "+ roomId + ".");
        }
    }

    @Override
    public RoomResponse updateRoom(Room room) {
        try {
            jdbcTemplate.update(
                    "UPDATE room SET number =?, capacity=?, net_base_price=?, description=? WHERE id=?;",
                    room.getNumber(), room.getCapacity(), room.getNetBasePrice(), room.getDescription(), room.getId());
            return new RoomResponse(true, room, "Updating room successful. id: "+ room.getId() + ".");
        } catch (DataAccessException dae) {
            return new RoomResponse(false, room, "Updating room failed. id: "+ room.getId() + ".");
        }
    }

    @Override
    public RoomResponse deleteRoomById(Long roomId) {
        try {
            jdbcTemplate.update(
                    "DELETE from room where id = ?", roomId);
            return new RoomResponse(true, null, "Room has been deleted");
        } catch (DataAccessException dae) {
            return new RoomResponse(false, null, "Deleting room failed");
        }
    }

    public RoomResponse checkRoomOnFloorInHostel(Room room) {
        try {
            return new RoomResponse(true, jdbcTemplate.queryForObject(
                    "SELECT id, floor, number, capacity, net_base_price, description, hostel_id FROM room " +
                    "WHERE floor =? AND number =? AND hostel_id =?",
                    new RoomRowMapper(), room.getFloor(), room.getNumber(), room.getHostelId()), "Room found in hotel.");
        } catch (DataAccessException dae) {
            return new RoomResponse(false, null, "Room not found in hotel");
        }
    }

    public List<Room> listRoomsByHostelId(Long hostelId) {
        return jdbcTemplate.query("SELECT id, floor, number, capacity, net_base_price, description, hostel_id FROM room " +
                "WHERE hostel_id =?;", new RoomRowMapper(), hostelId);
    }

    private static class RoomRowMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet resultSet, int i) throws SQLException {

            Room room = new Room();

            room.setId(resultSet.getLong("id"));
            room.setFloor(resultSet.getInt("floor"));
            room.setNumber(resultSet.getInt("number"));
            room.setCapacity(resultSet.getInt("capacity"));
            room.setNetBasePrice(resultSet.getBigDecimal("net_base_price"));
            room.setDescription(resultSet.getString("description"));
            room.setHostelId(resultSet.getLong("hostel_id"));

            return room;
        }
    }
}
