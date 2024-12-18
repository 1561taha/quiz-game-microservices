package com.taha.quiz_microservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.taha.quiz_microservice.Model.QuestionDto;

import java.util.List;
import java.util.Map;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("/questions/generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz
            (@RequestParam String category);

    @PostMapping("/questions/get")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(@RequestBody List<Long> questionIds);
    @PostMapping("/questions/score")
    public ResponseEntity<Long> getScore(@RequestBody Map<Long,Long> userAnswers);

}
