package com.example.dormitoryapi.service;

import com.example.dormitoryapi.model.Room;
import com.example.dormitoryapi.repository.RoomRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepo roomRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepo.findById(id).orElse(null);
    }

    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);
    }
}
