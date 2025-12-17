package com.example.demo.controller;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Studententity;
import com.example.demo.services.Studentservice;
@RestController
@RequestMapping("/student")
public class Studentcontroller{
    @Autowired
    private Studentservice ser;
    @PostMapping("/add")
    public Studententity addStudent(@RequestBody Studententity st){
        return ser.saveData(st);
    }
    @GetMapping("/getall")
    public Collection<Studententity>getAllStudents(){
        return ser.getAll();
    }
    @GetMapping("/get/{id}")
    public Studententity getStudentById(@PathVariable int id){
        return ser.getById(id);
    }
    @PutMapping("/update/{id}")
    public Studententity updateStudent(@PathVariable int id,@RequestBody Studententity st){
        return ser.update(id,st);
    }
}