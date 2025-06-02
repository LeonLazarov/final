package com.example.dormitoryapi.controller;

import com.example.dormitoryapi.model.Assignment;
import com.example.dormitoryapi.model.Student;
import com.example.dormitoryapi.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }



    @PostMapping("/rooms/{roomId}/students/{studentId}/assignments")
    public ResponseEntity<?> assignStudentToRoom(@PathVariable Long roomId, @PathVariable Long studentId) {
        try {
            Assignment assignment = assignmentService.assignStudentToRoom(roomId, studentId);
            return ResponseEntity.ok(assignment);
        } catch (IllegalStateException | NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/assignments/{id}/checkout")
    public ResponseEntity<?> checkoutAssignment(@PathVariable Long id) {
        try {
            Assignment assignment = assignmentService.checkoutAssignment(id);
            return ResponseEntity.ok(assignment);
        } catch (IllegalStateException | NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @GetMapping("/rooms/{roomId}/students")
    public ResponseEntity<?> listRoomOccupants(@PathVariable Long roomId) {
        try {
            List<Student> occupants = assignmentService.listCurrentRoomOccupants(roomId);
            return ResponseEntity.ok(occupants);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/students/{studentId}/assignments")
    public ResponseEntity<?> getStudentAssignments(@PathVariable Long studentId) {
        try {
            List<Assignment> assignments = assignmentService.getStudentAssignments(studentId);
            return ResponseEntity.ok(assignments);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
