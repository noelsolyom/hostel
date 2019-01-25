package com.training360.hostel.room;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(NewRoom newRoom);
    RoomResponse getRoomById(Long roomId);
    RoomResponse updateRoom(Room room);
    RoomResponse deleteRoomById(Long roomId);
    List<Room> listRoomsByHostelId(Long hostelId);
}
