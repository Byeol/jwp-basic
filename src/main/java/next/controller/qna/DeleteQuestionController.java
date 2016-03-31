package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.exception.NotAllowedException;
import next.service.QuestionService;
import next.service.QuestionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController extends AbstractController {
    private QuestionService questionService = new QuestionServiceImpl();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        ModelAndView mav = jspView("redirect:/");
        try {
            questionService.delete(questionId);
        } catch (NotAllowedException e) {
        }
        return mav;
    }
}
