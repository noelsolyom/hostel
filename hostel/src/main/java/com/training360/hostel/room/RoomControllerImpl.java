package com.training360.hostel.room;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomControllerImpl implements RoomController{

    private RoomServiceImpl roomServiceImpl;

    public RoomControllerImpl(RoomServiceImpl roomService) {
        this.roomServiceImpl = roomService;
    }

    @Override
    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public RoomResponse createRoom(@RequestBody NewRoom newRoom) {
        return roomServiceImpl.createRoom(newRoom);
    }

    @Override
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public RoomResponse getRoomById(@PathVariable String roomId) {
        try {
            return roomServiceImpl.getRoomById(Long.parseLong(roomId));
        } catch (NumberFormatException nfe) {
            return new RoomResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/room/update", method = RequestMethod.POST)
    public RoomResponse updateRoom(@RequestBody Room room) {
        return roomServiceImpl.updateRoom(room);
    }

    @Override
    @RequestMapping(value = "/room/delete/{roomId}", method = RequestMethod.DELETE)
    public RoomResponse deleteRoomById(@PathVariable String roomId) {
        try {
            return roomServiceImpl.deleteRoomById(Long.parseLong(roomId));
        } catch (NumberFormatException nfe) {
            return new RoomResponse(false, null, "Only numbers allowed.");
        }
    }

    @Override
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public List<Room> listRoomsByHostelId(@RequestParam String hostelId) {
        try{
            return roomServiceImpl.listRoomsByHostelId(Long.parseLong(hostelId));
        }catch (NumberFormatException nfe) {
            return new ArrayList<>();
        }
    }
}
