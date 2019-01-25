package com.training360.hostel.room;

public interface RoomDAOCRUD {
    RoomResponse createRoom(NewRoom newRoom);
    RoomResponse getRoomById(Long roomId);
    RoomResponse updateRoom(Room room);
    RoomResponse deleteRoomById(Long roomId);
}
