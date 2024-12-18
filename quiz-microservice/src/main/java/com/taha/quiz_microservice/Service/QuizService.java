package com.taha.quiz_microservice.Service;

import com.taha.quiz_microservice.Feign.QuizInterface;
import com.taha.quiz_microservice.Model.QuestionDto;
import com.taha.quiz_microservice.Model.Quiz;
import com.taha.quiz_microservice.Model.QuizDto;
import com.taha.quiz_microservice.Model.QuizResultDto;
import com.taha.quiz_microservice.Repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuizInterface quizInterface;
    public ResponseEntity<Quiz> CreateQuiz(String quizTitle, String category) {
        List<Long> questionIds= quizInterface.getQuestionsForQuiz(category).getBody();

        Quiz quiz= new Quiz();

        quiz.setQuizTitle(quizTitle);
        quiz.setCategory(category);
        quiz.setQuestionIds(questionIds);

       return new ResponseEntity<>( quizRepo.save(quiz), HttpStatus.CREATED);



    }

    public ResponseEntity<QuizDto> findQuizById(Long id)  {
        // Fetch the quiz from the database
        Quiz ogquiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Fetch questions from the Question Service
        ResponseEntity<List<QuestionDto>> response = quizInterface.getQuestionsFromIds(ogquiz.getQuestionIds());
        List<QuestionDto> questions = response.getBody();

        // Map the data into QuizDto
        QuizDto quizDto = new QuizDto();
        quizDto.setId(id);
        quizDto.setCategory(ogquiz.getCategory());
        quizDto.setQuizTitle(ogquiz.getQuizTitle());
        quizDto.setQuestions(questions);

        // Return the constructed QuizDto
        return ResponseEntity.ok(quizDto);
    }

    public QuizResultDto quizResult(Long quizId, Map<Long, Long> userAnswers) {
        QuizResultDto result= new QuizResultDto();

        Long score=quizInterface.getScore(userAnswers).getBody();
        result.setQuizId(quizId);
        result.setScore(score);
        result.setTotalQuestions(Long.valueOf(userAnswers.size()));

        return result;

    }
}
