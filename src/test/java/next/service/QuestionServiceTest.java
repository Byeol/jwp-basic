package next.service;

import next.dao.QuestionDao;
import next.exception.NotFoundException;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    @Mock
    private Question question;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionService questionService;

    private long questionId;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        questionId = new Random().nextLong();
        when(questionDao.findById(questionId))
                .thenReturn(question);
    }

    @Test
    public void delete() throws Exception {
        questionService.delete(questionId);
        verify(question).delete();
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExist() throws Exception {
        questionService.delete(questionId+1);
        verify(question, never()).delete();
    }
}
