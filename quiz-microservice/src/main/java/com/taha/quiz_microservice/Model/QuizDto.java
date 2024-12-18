package com.taha.quiz_microservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long  id;
    private  String quizTitle;
    private String category;
    private List<QuestionDto> questions;
}
