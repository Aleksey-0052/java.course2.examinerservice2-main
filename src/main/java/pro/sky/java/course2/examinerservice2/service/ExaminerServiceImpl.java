package pro.sky.java.course2.examinerservice2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice2.domain.Question;
import pro.sky.java.course2.examinerservice2.exceptions.ExceededTheLimitOfQuestionsException;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService javaQuestionService;

    private final QuestionService mathQuestionService;

    @Autowired
    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaQuestionService, @Qualifier("mathQuestionService") QuestionService mathQuestionService) {

        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        Collection<Question> javaAllQuestions = javaQuestionService.getAll();
        Collection<Question> mathAllQuestions = mathQuestionService.getAll();
        int x = javaAllQuestions.size() + mathAllQuestions.size();

        if (amount > x) {
            throw new ExceededTheLimitOfQuestionsException("Превышен лимит вопросов, всего вопросов " + x);
        }

        Set<Question> setResult = new HashSet<>();
        if (amount == x) {
            setResult.addAll(javaAllQuestions);
            setResult.addAll(mathAllQuestions);
            return setResult;
        }
        Random random = new Random();
        while (setResult.size() < amount) {
            if (random.nextBoolean()) {
                setResult.add(javaQuestionService.getRandomQuestion());
            } else {
                setResult.add(mathQuestionService.getRandomQuestion());
            }
        }
        return setResult;
    }

}
