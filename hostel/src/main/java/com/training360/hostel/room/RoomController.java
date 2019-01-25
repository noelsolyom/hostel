package com.training360.hostel.room;

import java.util.List;

public interface RoomController {
    RoomResponse createRoom(NewRoom newRoom);
    RoomResponse getRoomById(String roomId);
    RoomResponse updateRoom(Room room);
    RoomResponse deleteRoomById(String roomId);
    List<Room> listRoomsByHostelId(String hostelId);
}
