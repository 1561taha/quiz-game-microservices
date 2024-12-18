package com.taha.quiz_microservice.Controller;

import com.taha.quiz_microservice.Model.Quiz;
import com.taha.quiz_microservice.Model.QuizDto;
import com.taha.quiz_microservice.Model.QuizResultDto;
import com.taha.quiz_microservice.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam String quizTitle,
                                           @RequestParam String category){
        return quizService.CreateQuiz(quizTitle,category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> GetQuizById(@PathVariable Long id)  {
       return quizService.findQuizById(id);
    }

    @PostMapping("/{quizId}/play")
    public ResponseEntity<QuizResultDto> playquiz
            (@PathVariable Long quizId , @RequestBody Map<Long, Long> userAnswers){
        return new ResponseEntity<>(quizService.quizResult(quizId,userAnswers), HttpStatus.OK);
    }




}
