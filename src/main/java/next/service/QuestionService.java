package next.service;

import next.dao.QuestionDao;
import next.exception.NotFoundException;
import next.model.Question;

public class QuestionService {
    private QuestionDao questionDao = new QuestionDao();

    public Question insert(Question question) {
        return questionDao.insert(question);
    }

    public void delete(long questionId) throws Exception {
        Question question = questionDao.findById(questionId);

        if (question == null) {
            throw new NotFoundException();
        }

        question.delete();
    }

    public void delete(Question question) throws Exception {
        delete(question.getQuestionId());
    }
}
