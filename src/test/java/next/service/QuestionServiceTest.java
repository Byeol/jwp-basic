package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.NotAllowedException;
import next.model.Answer;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        List<Answer> answers = new ArrayList<>();

        doReturn(new Question(1, "writer", "title", "contents", new Date(), 0))
                .when(questionDao).findById(1);

        doReturn(new Question(1, "writer", "title", "contents", new Date(), 0))
                .when(questionDao).insert(any(Question.class));

        doAnswer(invocation -> {
            answers.add(invocation.getArgumentAt(0, Answer.class));
            return null;
        }).when(answerDao).insert(any(Answer.class));

        doAnswer(invocation -> answers.stream()
                .filter(answer -> answer.getQuestionId() == invocation.getArgumentAt(0, Long.class))
                .collect(Collectors.toList())
        ).when(answerDao).findAllByQuestionId(anyLong());
    }

    @Test
    public void deleteQuestionWithoutAnswer() throws Exception {
        Question question = new Question("writer", "title", "contents");
        question = questionService.insert(question);
        questionService.delete(question);
        verify(questionDao).delete(question.getQuestionId());
    }

    @Test
    public void deleteQuestionHasOwnAnswer() throws Exception {
        Question question = new Question("writer", "title", "contents");
        question = questionService.insert(question);
        Answer answer = new Answer(question.getWriter(), "contents", question.getQuestionId());
        answerDao.insert(answer);
        questionService.delete(question);
        verify(questionDao).delete(question.getQuestionId());
    }

    @Test(expected=NotAllowedException.class)
    public void deleteQuestionHasOthersAnswer() throws Exception {
        Question question = new Question("writer", "title", "contents");
        question = questionService.insert(question);
        Answer answer = new Answer("writer 2", "contents", question.getQuestionId());
        answerDao.insert(answer);
        questionService.delete(question);
    }

    @Test
    public void deleteQuestionHasMultipleOwnAnswer() throws Exception {
        Question question = new Question("writer", "title", "contents");
        question = questionService.insert(question);
        Answer answer = new Answer(question.getWriter(), "contents", question.getQuestionId());
        answerDao.insert(answer);
        answer = new Answer(question.getWriter(), "contents", question.getQuestionId());
        answerDao.insert(answer);
        questionService.delete(question);
        verify(questionDao).delete(question.getQuestionId());
    }
}
