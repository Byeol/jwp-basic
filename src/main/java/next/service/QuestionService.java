package next.service;

import next.model.Question;

public abstract class QuestionService {
    public abstract Question insert(Question question);

    public void delete(Question question) throws Exception {
        delete(question.getQuestionId());
    }

    public abstract void delete(long questionId) throws Exception;
}
