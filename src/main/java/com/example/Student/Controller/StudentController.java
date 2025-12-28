package com.example.Student.Controller;

import com.example.Student.Model.Student;
import com.example.Student.Service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentServices studentServices;
    @GetMapping
    public List<Student> AllStudents(){
        return studentServices.getAll();
    }
    @GetMapping("id/{myID}")
    public Student getStudent(@PathVariable Long myID){
        Optional<Student> studentoptional= studentServices.getByID(myID);
        if(studentoptional.isPresent()){
            return studentoptional.get();
        }
        else throw new RuntimeException("Student is not found with find "+ myID);
    }
    @PostMapping
    public Student saveStudent(@RequestBody Student student){
        studentServices.saveEntry(student);
        return student;
    }
    @PutMapping("id/{myID}")
    public Student updateStudent(@PathVariable Long myID, @RequestBody Student newStudent) {
        Optional<Student> optional = studentServices.getByID(myID);
        if (optional.isPresent()) {
            Student old = optional.get();
            old.setCourse(newStudent.getCourse() != null && !newStudent.getCourse().equals(" ") ? newStudent.getCourse() : old.getCourse());
            old.setName(newStudent.getName() != null && !newStudent.getName().equals(" ") ? newStudent.getName() : old.getName());
            old.setUniversity_Name(newStudent.getUniversity_Name() != null && !newStudent.getUniversity_Name().equals(" ") ? newStudent.getUniversity_Name() : old.getUniversity_Name());
            studentServices.saveEntry(old);
            return old;
        } else throw new RuntimeException("Student not found with ID " + myID);
    }
    @DeleteMapping("id/{myID}")
    public String deleteStudent(@PathVariable Long myID){
        if (studentServices.getByID(myID).isPresent()) {
            studentServices.deleteID(myID);
            return "Student with id "+myID+" has been deleted";
        }
        else return "Student with id is not found";
    }

}
