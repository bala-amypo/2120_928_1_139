package com.example.demo.services;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Studentity;
@Service 
public class Studentservice{
private Map<Integer,Studententity>
details=new HashMap<>();
public Studententity saveData(Studententity st){
    details.put(st.getId(),st);
    return st;
}
public Collection<Studententity> getAll(){
    return details.values();
}
public StudentEntity getById(int id){
    
}
}