package pro.sky.java.course2.examinerservice2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice2.domain.Question;
import pro.sky.java.course2.examinerservice2.exceptions.ExceededTheLimitOfQuestionsException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pro.sky.java.course2.examinerservice2.constants.QuestionsConstants.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private  JavaQuestionService serviceMock;
    @Mock
    private MathQuestionService serviceMock2;



    private ExaminerServiceImpl out;

    @BeforeEach
    public void setUp() {

        out = new ExaminerServiceImpl(serviceMock, serviceMock2);

    }

    @Test
    public void testGetQuestionsByAmount_AmountEqualsSize_Success() {
        // данный тест проверяет работу метода, когда amount == size
        int amount = 15;

        when(serviceMock.getAll())
                .thenReturn(ALL_JAVA_QUESTIONS);

        when(serviceMock2.getAll())
                .thenReturn(ALL_MATH_QUESTIONS);

        Collection<Question> actual = out.getQuestions(amount);

        assertEquals(amount, actual.size());
        assertEquals(ALL_QUESTIONS, actual);
        verify(serviceMock, times(1)).getAll();       // Вызывается 2 раза

    }

    @Test
    public void testGetQuestionsByAmount_AmountLessSize_Success() {
        // данный тест проверяет работу метода, когда amount < size
        int amount = 1;

        Set<Question> expected = new HashSet<>(List.of(javaQuest1, javaQuest2));

        when(serviceMock.getAll())
                .thenReturn(expected);
        when(serviceMock.getRandomQuestion())
                .thenReturn(javaQuest1);

        List<Question> actual = new ArrayList<>(out.getQuestions(amount));

        assertEquals(amount, actual.size());
        assertEquals(javaQuest1, actual.get(0));
        verify(serviceMock, times(2)).getAll();
        verify(serviceMock, times(1)).getRandomQuestion();
    }

    @Test
    public void testGetQuestionsByAmount_ThrowsExceededTheLimitOfQuestionsException() {

        int amount = 16;

        when(serviceMock.getAll())
                .thenReturn(ALL_QUESTIONS);

        assertThrows(ExceededTheLimitOfQuestionsException.class,
                () -> out.getQuestions(amount));
    }

    
}