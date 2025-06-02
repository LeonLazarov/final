package com.example.dormitoryapi;

import com.example.dormitoryapi.model.Assignment;
import com.example.dormitoryapi.model.Room;
import com.example.dormitoryapi.model.Student;
import com.example.dormitoryapi.repository.AssignmentRepo;
import com.example.dormitoryapi.repository.RoomRepo;
import com.example.dormitoryapi.repository.StudentRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepo studentRepo;
    private final RoomRepo roomRepo;
    private final AssignmentRepo assignmentRepo;

    public DataLoader(StudentRepo studentRepo, RoomRepo roomRepo, AssignmentRepo assignmentRepo) {
        this.studentRepo = studentRepo;
        this.roomRepo = roomRepo;
        this.assignmentRepo = assignmentRepo;
    }

    @Override
    public void run(String... args) {
        Student s1 = studentRepo.save(new Student("Leon", "Lazarov", "leon.lazarov2@gmail.com", "FRESHMAN"));
        Student s2 = studentRepo.save(new Student("Teresa", "Frederick", "TeresaCFrederick@gmail.com", "SENIOR"));
        Student s3 = studentRepo.save(new Student("Michael", "Siefert", "MichaelSSiefert@gmail.com", "FRESHMAN"));
        Student s4 = studentRepo.save(new Student("Cathy", "Aronson", "CathyDAronson@gmail.com", "SENIOR"));
        Student s5 = studentRepo.save(new Student("Ollie", "Martinez", "OllieTMartinez@gmail.com", "FRESHMAN"));
        Student s6 = studentRepo.save(new Student("Steven", "Taylor", "StevenBTaylor@gmail.com", "SENIOR"));

        Room r1 = roomRepo.save(new Room("Building A", 1, 101, 2, 0));
        Room r2 = roomRepo.save(new Room("Building A", 1, 102, 4, 0));
        Room r3 = roomRepo.save(new Room("Building B", 2, 201, 3, 0));
        Room r4 = roomRepo.save(new Room("Building B", 2, 202, 5, 0));


        assignmentRepo.save(new Assignment(LocalDate.now().minusMonths(6), LocalDate.now().minusMonths(3), s1, r1));
        assignmentRepo.save(new Assignment(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(2), s2, r2));

        Assignment active1 = new Assignment(LocalDate.now().minusDays(30), null, s3, r1);
        Assignment active2 = new Assignment(LocalDate.now().minusDays(10), null, s4, r2);
        Assignment active3 = new Assignment(LocalDate.now().minusDays(20), null, s5, r2);

        assignmentRepo.save(active1);
        assignmentRepo.save(active2);
        assignmentRepo.save(active3);

        r1.setCurrentOccupancy(1);
        r2.setCurrentOccupancy(2);
        roomRepo.save(r1);
        roomRepo.save(r2);
    }
}
