package next.model;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.NotAllowedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionTest {

    @Mock
    private Answer answerByOwner;

    @Mock
    private Answer answerByOthers;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    private long questionId;
    private String questionWriter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        List<Answer> answers = new ArrayList<>();
        questionId = new Random().nextLong();
        questionWriter = "writer";

        when(questionDao.findById(questionId))
            .thenReturn(new Question(questionId, questionWriter, "title", "contents", new Date(), 0));

        when(answerDao.insert(any(Answer.class)))
            .then(invocation -> {
                answers.add(invocation.getArgumentAt(0, Answer.class));
                return null;
            });

        when(answerDao.findAllByQuestionId(anyLong()))
            .then(invocation -> answers.stream()
                    .filter(answer -> answer.getQuestionId() == invocation.getArgumentAt(0, Long.class))
                    .collect(Collectors.toList())
            );

        when(answerByOwner.canDelete(questionWriter)).thenReturn(true);
        when(answerByOwner.getQuestionId()).thenReturn(questionId);
        when(answerByOthers.canDelete(questionWriter)).thenReturn(false);
        when(answerByOthers.getQuestionId()).thenReturn(questionId);
    }

    @Test
    public void deleteQuestionWithoutAnswer() throws Exception {
        Question question = questionDao.findById(questionId);
        question.injectDao(questionDao, answerDao);
        question.delete();
        verify(questionDao).delete(question.getQuestionId());
    }

    @Test
    public void deleteQuestionHasOwnAnswer() throws Exception {
        Question question = questionDao.findById(questionId);
        question.injectDao(questionDao, answerDao);
        answerDao.insert(answerByOwner);
        question.delete();
        verify(questionDao).delete(question.getQuestionId());
        verify(answerByOwner).delete(questionWriter);
    }

    @Test(expected = NotAllowedException.class)
    public void deleteQuestionHasOthersAnswer() throws Exception {
        Question question = questionDao.findById(questionId);
        question.injectDao(questionDao, answerDao);
        answerDao.insert(answerByOthers);
        question.delete();
        verify(questionDao, never()).delete(question.getQuestionId());
        verify(answerByOthers, never()).delete(questionWriter);
    }

    @Test
    public void deleteQuestionHasMultipleOwnAnswer() throws Exception {
        Question question = questionDao.findById(questionId);
        question.injectDao(questionDao, answerDao);
        answerDao.insert(answerByOwner);
        answerDao.insert(answerByOwner);
        question.delete();
        verify(questionDao).delete(question.getQuestionId());
        verify(answerByOwner, times(2)).delete(questionWriter);
    }
}
