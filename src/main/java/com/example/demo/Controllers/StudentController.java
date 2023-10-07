package com.example.demo.Controllers;

import com.example.demo.Exceptions.ResourceNotFoundExceptions;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentRepository.save(student);

    }

    //get all
    @GetMapping
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    //Get by id
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundExceptions("student not found with id"+id));
        return ResponseEntity.ok(student);
    }

    //update

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id,@RequestBody Student studentDetails){
        Student updateStud = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundExceptions("student not found  id: "+id));

        updateStud.setFirstName(studentDetails.getFirstName());
        updateStud.setLastName(studentDetails.getLastName());
        updateStud.setEmail(studentDetails.getEmail());

        studentRepository.save(updateStud);
        return ResponseEntity.ok(updateStud);

    }

    //delete
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundExceptions("student does not exit with this id :"+id));

        studentRepository.delete(student);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<String>("Student with '"+id+"' has been deleted" , HttpStatus.OK);
    }
}
