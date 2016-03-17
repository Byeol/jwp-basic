package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;

import java.util.List;

public class FormQuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        return "/qna/form.jsp";
    }
}
