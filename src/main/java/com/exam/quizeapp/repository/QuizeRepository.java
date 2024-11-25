package com.exam.quizeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.quizeapp.model.Quiz;

@Repository
public interface QuizeRepository extends JpaRepository<Quiz,Integer>{
    
}
