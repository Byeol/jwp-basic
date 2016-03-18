package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void insert(Question question) {
        this.jdbcTemplate.update("INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)",
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                question.getCountOfAnswer()
        );
    }

    public Question findByQuestionId(String questionId) {
        return this.jdbcTemplate.queryForObject("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?", new QuestionMapper(), questionId);
    }

    public List<Question> findAll() {
        return this.jdbcTemplate.query("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS", new QuestionMapper());
    }

    private static final class QuestionMapper implements RowMapper<Question> {
        public Question mapRow(ResultSet rs) throws SQLException {
            return new Question(
                    rs.getLong("questionId"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate"),
                    rs.getInt("countOfAnswer")
            );
        }
    }
}
