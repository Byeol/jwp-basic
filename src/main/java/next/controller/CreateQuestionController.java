package next.controller;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return "redirect:/users/loginForm";
		}

		if (req.getParameter("writer").isEmpty()
				|| req.getParameter("title").isEmpty()
				|| req.getParameter("contents").isEmpty()) {
			return "redirect:/";
		}

		Question question = new Question(
				req.getParameter("writer"),
				req.getParameter("title"),
				req.getParameter("contents")
		);
		log.debug("Question : {}", question);

		QuestionDao questionDao = new QuestionDao();
		questionDao.insert(question);
		return "redirect:/";
	}
}
