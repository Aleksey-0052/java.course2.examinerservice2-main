package pro.sky.java.course2.examinerservice2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice2.domain.Question;
import pro.sky.java.course2.examinerservice2.exceptions.QuestionAlreadyAddedException;
import pro.sky.java.course2.examinerservice2.exceptions.QuestionNotFoundException;
import pro.sky.java.course2.examinerservice2.repository.QuestionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static pro.sky.java.course2.examinerservice2.constants.QuestionsConstants.*;
import static pro.sky.java.course2.examinerservice2.constants.QuestionsConstants.ALL_MATH_QUESTIONS;
@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    @Mock
    private QuestionRepository repositoryMock;
    @InjectMocks
    private MathQuestionService out;


    @Test
    void testAddQuestionAnswer_Success() {

        when(repositoryMock.add(mathQuest1))
                .thenReturn(mathQuest1);

        assertEquals(mathQuest1, out.add(mathQuestion1, mathAnswer1));
        verify(repositoryMock, times(1)).add(mathQuest1);

    }

    @Test
    void testAddQuestionAnswer_QuestionAlreadyAdded_ThrowsQuestionAlreadyAddedException() {

        String mathQuestion11 = "Вопрос 11";
        String mathAnswer11 = "Ответ 11";
        // отсутствующий объект

        when(repositoryMock.add(any()))
                .thenThrow(QuestionAlreadyAddedException.class);

        assertThrows(QuestionAlreadyAddedException.class, () -> out.add(mathQuestion11, mathAnswer11));
        // Срабатывает при добавлении отсутствующего элемента

    }

    @Test
    void testAddQuestion_Success() {

        when(repositoryMock.add(mathQuest1))
                .thenReturn(mathQuest1);

        assertEquals(mathQuest1, out.add(mathQuest1));
        verify(repositoryMock, times(1)).add(mathQuest1);

    }

    @Test
    void testAddQuestion_QuestionAlreadyAdded_ThrowsQuestionAlreadyAddedException() {

        Question mathQuest11 = new Question("Вопрос 11", "Ответ 11");
        // отсутствующий объект

        when(repositoryMock.add(any()))
                .thenThrow(QuestionAlreadyAddedException.class);

        assertThrows(QuestionAlreadyAddedException.class, () -> out.add(mathQuest11));
        // При добавлении отсутствующих объектов тоже срабатывает

    }

    @Test
    void testRemoveQuestion_Success() {

        when(repositoryMock.remove(mathQuest5))
                .thenReturn(mathQuest5);

        assertEquals(mathQuest5, out.remove(mathQuest5));
        verify(repositoryMock, times(1)).remove(mathQuest5);

    }

    @Test
    void  testRemoveQuestion_QuestionNotFound_ThrowsQuestionNotFoundException() {

        Question mathQuest11 = new Question("Вопрос 11", "Ответ 11");
        // отсутствующий объект

        when(repositoryMock.remove(any()))
                .thenThrow(QuestionNotFoundException.class);

        assertThrows(QuestionNotFoundException.class, () -> out.remove(mathQuest1));
        // Срабатывает при удалении имеющегося элемента

    }

    @Test
    void testGetAll_Success() {

        when(repositoryMock.getAll())
                .thenReturn(ALL_MATH_QUESTIONS);

        assertIterableEquals(ALL_MATH_QUESTIONS, out.getAll());
        assertEquals(ALL_MATH_QUESTIONS.size(), out.getAll().size());
        verify(repositoryMock, times(2)).getAll();    // Вызывается 2 раза

    }
    
}