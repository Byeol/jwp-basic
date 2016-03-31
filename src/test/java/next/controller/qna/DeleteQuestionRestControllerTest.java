package next.controller.qna;

import core.mvc.ModelAndView;
import next.exception.NotAllowedException;
import next.model.Result;
import next.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeleteQuestionRestControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private DeleteQuestionRestController controller;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteQuestion() throws Exception {
        long questionId = 1;
        when(request.getParameter("questionId")).thenReturn(Long.toString(questionId));
        ModelAndView mav = controller.execute(request, response);
        verify(questionService).delete(questionId);
        Result result = (Result) mav.getModel().get("result");
        assertTrue(result.isStatus());
    }

    @Test
    public void deleteQuestionNotAllowed() throws Exception {
        long questionId = 1;
        when(request.getParameter("questionId")).thenReturn(Long.toString(questionId));
        doThrow(new NotAllowedException()).when(questionService).delete(questionId);
        ModelAndView mav = controller.execute(request, response);
        verify(questionService).delete(questionId);
        Result result = (Result) mav.getModel().get("result");
        assertFalse(result.isStatus());
    }
}
