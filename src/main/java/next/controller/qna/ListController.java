package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        QuestionDao questionDao = new QuestionDao();
        List<Question> questions = questionDao.findAll();

        String responseBody = mapper.writeValueAsString(questions);

        resp.setContentType("application/json");
        resp.getWriter().print(responseBody);

        return null;
    }
}
