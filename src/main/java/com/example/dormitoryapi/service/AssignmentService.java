package com.example.dormitoryapi.service;

import com.example.dormitoryapi.model.Assignment;
import com.example.dormitoryapi.model.Room;
import com.example.dormitoryapi.model.Student;
import com.example.dormitoryapi.repository.AssignmentRepo;
import com.example.dormitoryapi.repository.RoomRepo;
import com.example.dormitoryapi.repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;
    private final RoomRepo roomRepo;
    private final StudentRepo studentRepo;

    public AssignmentService(AssignmentRepo assignmentRepo, RoomRepo roomRepo, StudentRepo studentRepo) {
        this.assignmentRepo = assignmentRepo;
        this.roomRepo = roomRepo;
        this.studentRepo = studentRepo;
    }

    public Assignment assignStudentToRoom(Long roomId, Long studentId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found"));
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            throw new IllegalStateException("Room is full, cannot assign student");
        }

        boolean hasActive = assignmentRepo.findByStudentAndEndDateIsNull(student).isPresent();
        if (hasActive) {
            throw new IllegalStateException("Student already has an active assignment");
        }

        Assignment assignment = new Assignment(LocalDate.now(), null, student, room);
        assignment = assignmentRepo.save(assignment);

        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        roomRepo.save(room);

        return assignment;
    }

    public Assignment checkoutAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));

        if (assignment.getEndDate() != null) {
            throw new IllegalStateException("Assignment already checked out");
        }

        assignment.setEndDate(LocalDate.now());
        assignment = assignmentRepo.save(assignment);

        Room room = assignment.getRoom();
        room.setCurrentOccupancy(Math.max(room.getCurrentOccupancy() -1, 0));
        roomRepo.save(room);

        return assignment;
    }

    public List<Student> listCurrentRoomOccupants(Long roomId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found"));
        List<Assignment> activeAssignments = assignmentRepo.findByRoomAndEndDateIsNull(room);
        return activeAssignments.stream()
                .map(Assignment::getStudent)
                .toList();
    }

    public List<Assignment> getStudentAssignments(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return assignmentRepo.findByStudent(student);
    }
}
