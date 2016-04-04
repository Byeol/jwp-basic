package next.model;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.NotAllowedException;

import java.util.Date;
import java.util.List;

public class Question {
	private QuestionDao questionDao;
	private AnswerDao answerDao;

	public void injectDao(QuestionDao questionDao, AnswerDao answerDao) {
		this.questionDao = questionDao;
		this.answerDao = answerDao;
	}

	private long questionId;
	
	private String writer;
	
	private String title;
	
	private String contents;
	
	private Date createdDate;
	
	private int countOfComment;
	
	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}	
	
	public Question(long questionId, String writer, String title, String contents,
			Date createdDate, int countOfComment) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfComment = countOfComment;
		this.questionDao = new QuestionDao();
		this.answerDao = new AnswerDao();
	}

	public long getQuestionId() {
		return questionId;
	}
	
	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public int getCountOfComment() {
		return countOfComment;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer
				+ ", title=" + title + ", contents=" + contents
				+ ", createdDate=" + createdDate + ", countOfComment="
				+ countOfComment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}

	public boolean canDelete(String writer) {
		if (!getWriter().equals(writer))
			return false;

		List<Answer> answers = getAnswers();

		for (Answer answer : answers) {
			if (!answer.canDelete(getWriter()))
				return false;
		}

		return true;
	}

	public void delete() throws Exception {
		if (!canDelete(getWriter())) {
			throw new NotAllowedException();
		}

		List<Answer> answers = getAnswers();
		answers.forEach(answer -> {
			try {
				answer.delete(getWriter());
			} catch (Exception e) {
				throw new RuntimeException();
			}
		});

		questionDao.delete(getQuestionId());
	}

	public List<Answer> getAnswers() {
		return answerDao.findAllByQuestionId(getQuestionId());
	}
}
