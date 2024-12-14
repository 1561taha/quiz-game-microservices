package com.taha.question_service.Service;

import com.taha.question_service.Model.QuestionDb;
import com.taha.question_service.Model.QuestionDto;
import com.taha.question_service.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;
    public List<QuestionDb> findallquestions() {
        return questionRepo.findAll();
    }

    public QuestionDb findQuestion(Long id) {
        return questionRepo.findById(id).orElseThrow(()-> new RuntimeException("invalid question id"));
    }

    public QuestionDb createQuestion(QuestionDb questionDb) {
        return questionRepo.save(questionDb);
    }

    public QuestionDb updateQuestion(Long id, QuestionDb questionDb) {
        return questionRepo.findById(id).map(existingQuestion ->{
            existingQuestion.setQuestionTitle(questionDb.getQuestionTitle());
            existingQuestion.setOptions(questionDb.getOptions());
            existingQuestion.setCategory(questionDb.getCategory());
            existingQuestion.setCorrectOption(questionDb.getCorrectOption());
            existingQuestion.setDifficulty(questionDb.getDifficulty());

            return questionRepo.save(existingQuestion);


        }).orElseThrow(()-> new RuntimeException("invalid job id"));

    }

    public void deleteQuestion(Long id) {
        questionRepo.deleteById(id);
    }


    public ResponseEntity<List<Long>> getQuestionsForQuiz(String category) {

        List<Long> QuestionIds= questionRepo.findByCategory(category);
        return new ResponseEntity<>(QuestionIds, HttpStatus.OK);
    }


    public List<QuestionDto> getQuestionsByIds(List<Long> questionIds) {

        List<QuestionDb> questions=questionRepo.findAllById(questionIds);
        List<QuestionDto> questionDtos=questions
                .stream()
                .map(q->new QuestionDto(q.getId(),q.getQuestionTitle(),q.getOptions(),q.getCategory(),q.getDifficulty()))
                .collect(Collectors.toList());

        return questionDtos;


    }

    public Long getScore(Map<Long, Long> userAnswers) {
        // Fetch all questions in one go to avoid multiple database queries



        List<QuestionDb> questions=questionRepo.findAllById(userAnswers.keySet());

        System.out.println("Questions retrieved: " + questions);

        // Calculate the score
        return questions.stream()
                .filter(question -> {
                    Long userAnswer = userAnswers.get(question.getId());
                    System.out.println("Checking Question ID: " + question.getId());
                    System.out.println("User Answer: " + userAnswer);
                    System.out.println("Correct Option: " + question.getCorrectOption());
                    return userAnswer != null && userAnswer.equals(question.getCorrectOption());
                })
                .count();
    }


}
