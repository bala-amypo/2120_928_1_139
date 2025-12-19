package com.example.demo.entity;


import jakarta.persistence.*;


@Entity
public class University {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String country;
private Boolean active = true;


public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getCountry() { return country; }
public void setCountry(String country) { this.country = country; }
public Boolean getActive() { return active; }
public void setActive(Boolean active) { this.active = active; }
}