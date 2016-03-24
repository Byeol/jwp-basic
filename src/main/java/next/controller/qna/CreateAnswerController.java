package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateAnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = null;

        try {
            Long questionId = Long.parseLong(req.getParameter("questionId"));
            String writer = req.getParameter("writer");
            String contents = req.getParameter("contents");

            if (writer == null || writer.isEmpty()) {
                throw new IllegalArgumentException("`writer` field cannot be empty.");
            } else if (contents == null || contents.isEmpty()) {
                throw new IllegalArgumentException("`contents` field cannot be empty.");
            } else if (questionId == null) {
                throw new IllegalArgumentException("`questionId` field cannot be empty.");
            } else if (questionDao.findById(questionId) == null) {
                throw new IllegalArgumentException("Question doesn't exist.");
            }

            Answer answer = new Answer(writer, contents, questionId);
            answer = answerDao.insert(answer);
            responseBody = mapper.writeValueAsString(answer);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody = mapper.writeValueAsString(Result.fail(e.getLocalizedMessage()));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody = mapper.writeValueAsString(Result.fail(e.getLocalizedMessage()));
        } finally {
            if (responseBody != null) {
                resp.setContentType("application/json");
                resp.getWriter().print(responseBody);
            }
        }

        return null;
    }
}
