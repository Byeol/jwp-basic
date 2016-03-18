package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnswerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Answer> findByQuestionId(String questionId) {
        return this.jdbcTemplate.query("SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=?", new AnswerMapper(), questionId);
    }

    private static final class AnswerMapper implements RowMapper<Answer> {
        public Answer mapRow(ResultSet rs) throws SQLException {
            return new Answer(
                    rs.getLong("answerId"),
                    rs.getString("writer"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate"),
                    rs.getLong("questionId")
            );
        }
    }
}
