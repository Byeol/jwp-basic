package next.model;

import java.sql.Timestamp;
import java.util.Date;

public class Answer {
    private Integer answerId;
    private String writer;
    private String contents;
    private Timestamp createdDate;
    private Integer questionId;

    public Answer(String writer, String contents, Integer questionId) {
        this.writer = writer;
        this.contents = contents;
        this.createdDate = new Timestamp(new Date().getTime());
        this.questionId = questionId;
    }

    public Answer(Integer answerId, String writer, String contents, Timestamp createdDate, Integer questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    @Override
    public String toString() {
        return "Answer [" +
                "answerId=" + answerId +
                ", writer='" + writer +
                ", contents='" + contents +
                ", createdDate=" + createdDate +
                ", questionId=" + questionId +
                ']';
    }
}
