package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import java.util.List;

public class QuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        List<String> pathVariables = (List<String>) req.getAttribute("pathVariables");
        String questionId = pathVariables.get(0);

        Question question = questionDao.findByQuestionId(questionId);
        req.setAttribute("question", question);

        List<Answer> answers = answerDao.findByQuestionId(questionId);
        req.setAttribute("answers", answers);

        return "/qna/show.jsp";
    }
}
