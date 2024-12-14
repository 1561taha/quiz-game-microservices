package com.taha.question_service.Controller;

import com.taha.question_service.Model.QuestionDb;
import com.taha.question_service.Model.QuestionDto;
import com.taha.question_service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public ResponseEntity<List<QuestionDb>> allQuestions(){
        return ResponseEntity.ok(questionService.findallquestions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDb> findQuestionById(@PathVariable Long id){
        return ResponseEntity.ok(questionService.findQuestion(id));
    }

    @PostMapping("/add")
    public ResponseEntity<QuestionDb> addQuestion(@RequestBody QuestionDb questionDb){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionDb));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDb> updateQuestion(@PathVariable Long id , @RequestBody QuestionDb questionDb){
        return ResponseEntity.ok(questionService.updateQuestion(id,questionDb));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body("question deleted");
        }
        catch ( IllegalArgumentException e){
            throw new RuntimeException("invalid job id");
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz
            (@RequestParam String category){
        return questionService.getQuestionsForQuiz(category);

    }

    @PostMapping("/get")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(@RequestBody List<Long> questionIds)
    {
        return new ResponseEntity<>(questionService.getQuestionsByIds(questionIds),HttpStatus.OK);

    }

    @PostMapping("/score")
    public ResponseEntity<Long> getScore(@RequestBody Map<Long,Long> userAnswers)
    {
        return new ResponseEntity<>(questionService.getScore(userAnswers),HttpStatus.OK);
    }

    // generte getquestions score
}
