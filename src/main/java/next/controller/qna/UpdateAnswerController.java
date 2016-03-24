package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateAnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = null;

        try {
            Long answerId = Long.parseLong(req.getParameter("answerId"));
            String writer = req.getParameter("writer");
            String contents = req.getParameter("contents");

            Answer answer;

            if (writer == null || writer.isEmpty()) {
                throw new IllegalArgumentException("`writer` field cannot be empty.");
            } else if (contents == null || contents.isEmpty()) {
                throw new IllegalArgumentException("`contents` field cannot be empty.");
            } else if (answerId == null) {
                throw new IllegalArgumentException("`answerId` field cannot be empty.");
            } else if ((answer = answerDao.findById(answerId)) == null) {
                throw new IllegalArgumentException("Answer doesn't exist.");
            }

            answer.setWriter(writer);
            answer.setContents(contents);

            answer = answerDao.update(answer);
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
