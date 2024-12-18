package com.taha.quiz_microservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private Long quizId;
    private Long totalQuestions;
    private Long score;
}
