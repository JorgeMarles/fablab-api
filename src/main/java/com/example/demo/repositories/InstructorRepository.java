package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	List<Instructor> findBySesiones_Id(Long sesionId);
}