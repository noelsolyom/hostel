package com.training360.hostel.room;

import com.training360.hostel.hostel.HostelDAOCRUDImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomDAOCRUDImpl roomDAOCRUDImpl;
    private HostelDAOCRUDImpl hostelDAOCRUD;

    public RoomServiceImpl(RoomDAOCRUDImpl roomDAOCRUDImpl, HostelDAOCRUDImpl hostelDAOCRUD) {
        this.roomDAOCRUDImpl = roomDAOCRUDImpl;
        this.hostelDAOCRUD = hostelDAOCRUD;
    }

    @Override
    public RoomResponse createRoom(NewRoom newRoom) {

        Room room = new Room(newRoom);

        try {
            validateRoomInput(room);
            if (!roomDAOCRUDImpl.checkRoomOnFloorInHostel(room).isOk()) {
                return roomDAOCRUDImpl.createRoom(newRoom);
            }
            throw new IllegalArgumentException("Room already exist on floor in hostel.");

        } catch (IllegalArgumentException iae) {
            return new RoomResponse(false, room, iae.getMessage());
        }
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        if (roomId == null || roomId < 1L) {
            return new RoomResponse(false, null, "Room id cannot be null or less than 1.");
        }
        return roomDAOCRUDImpl.getRoomById(roomId);
    }

    @Override
    public RoomResponse updateRoom(Room room) {
        if (room == null || room.getId() == null) {
            return new RoomResponse(false, room, "Room or ID cannot be null.");
        }

        if (!hostelDAOCRUD.getHostelById(room.getHostelId()).isOk()) {
            return new RoomResponse(false, room, "Hostel id for room is incorrect.");
        }

        try {
            RoomResponse roomResponse = roomDAOCRUDImpl.getRoomById(room.getId());

            if (!roomResponse.isOk()) {
                throw new IllegalArgumentException("Room not found. id: " + room.getId());
            }

            validateRoomInput(room);

            if (roomDAOCRUDImpl.checkRoomOnFloorInHostel(room).isOk() &&
                    roomDAOCRUDImpl.checkRoomOnFloorInHostel(room).getRoom().getId() != room.getId()) {
                throw new IllegalArgumentException("Room on floor is already exist.");
            }

            return roomDAOCRUDImpl.updateRoom(room);

        } catch (IllegalArgumentException iae) {
            return new RoomResponse(false, room, iae.getMessage());
        }
    }

    @Override
    public RoomResponse deleteRoomById(Long roomId) {
        if (roomId == null || roomId < 1L) {
            return new RoomResponse(false, null, "Room id cannot be null or less than 1.");
        }
        try {
            RoomResponse roomResponse = roomDAOCRUDImpl.getRoomById(roomId);
            if (!roomResponse.isOk()) {
                throw new IllegalArgumentException("Room not found. id: " + roomId);
            }

            return roomDAOCRUDImpl.deleteRoomById(roomId);
        } catch (IllegalArgumentException iae) {
            return new RoomResponse(false, null, iae.getMessage());
        }
    }

    @Override
    public List<Room> listRoomsByHostelId(Long hostelId) {
        return roomDAOCRUDImpl.listRoomsByHosteId(hostelId);
    }

    private static void validateRoomInput(Room room) {
        if (room.getFloor() == null || room.getFloor() < 0) {
            throw new IllegalArgumentException("Room floor must be filled and cannot be less than 0.");
        }
        if (room.getNumber() == null || room.getNumber() < 1) {
            throw new IllegalArgumentException("Room number must be must be filled and positive.");
        }
        if (room.getCapacity() == null || room.getCapacity() < 1) {
            throw new IllegalArgumentException("Room capacity must be must be filled and must be positive");
        }
        if (room.getNetBasePrice() == null || room.getNetBasePrice().longValue() < 0L) {
            throw new IllegalArgumentException("Room net base price must be filled and cannot be less than 0.");
        }
        if (room.getDescription() == null || room.getDescription().trim().equals("")) {
            throw new IllegalArgumentException("Room description must be filled.");
        }
        if (room.getHostelId() == null || room.getHostelId() < 0) {
            throw new IllegalArgumentException("Room's hostel id must be filled and positive.");
        }
    }
}
