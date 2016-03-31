package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.NotAllowedException;
import next.model.Answer;
import next.model.Question;

import java.util.List;

public class QuestionServiceImpl extends QuestionService {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public Question insert(Question question) {
        return questionDao.insert(question);
    }

    @Override
    public void delete(long questionId) throws Exception {
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        answers.removeIf(answer -> answer.getWriter().equals(question.getWriter()));

        if (!answers.isEmpty()) {
            throw new NotAllowedException();
        }

        questionDao.delete(questionId);
    }
}
