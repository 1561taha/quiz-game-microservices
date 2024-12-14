package com.taha.question_service.Repository;

import com.taha.question_service.Model.QuestionDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionDb,Long> {
@Query(value = "Select q.id from question_db q where q.category= :category" , nativeQuery = true)
    List<Long> findByCategory(String category);
}