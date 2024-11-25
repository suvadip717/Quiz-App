package com.exam.quizeapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.quizeapp.model.Question;
import com.exam.quizeapp.model.QuestionWrapper;
import com.exam.quizeapp.model.Quiz;
import com.exam.quizeapp.model.Response;
import com.exam.quizeapp.repository.QuestionRepository;
import com.exam.quizeapp.repository.QuizeRepository;

@Service
public class QuizeService {
    @Autowired
    private QuizeRepository quizeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> quistions = questionRepository.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(quistions);
        quizeRepository.save(quiz);

        return new ResponseEntity<>("Quize created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuesions(Integer id) {
        Optional<Quiz> quiz = quizeRepository.findById(id);
        List<Question> questionsOfDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUsers = new ArrayList<>();
        for (Question q : questionsOfDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
                    q.getOption3(), q.getOption4());
            questionForUsers.add(qw);
        }
        return new ResponseEntity<>(questionForUsers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses){
        Quiz quiz = quizeRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
