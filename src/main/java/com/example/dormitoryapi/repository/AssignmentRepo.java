package com.example.dormitoryapi.repository;

import com.example.dormitoryapi.model.Assignment;
import com.example.dormitoryapi.model.Room;
import com.example.dormitoryapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {

    List<Assignment> findByStudent(Student student);

    Optional<Assignment> findByStudentAndEndDateIsNull(Student student);

    List<Assignment> findByRoomAndEndDateIsNull(Room room);
}
