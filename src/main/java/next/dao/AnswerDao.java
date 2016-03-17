package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

import java.util.List;

public class AnswerDao {
    public List<Answer> findByQuestionId(String questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId=?";

        RowMapper<Answer> rm = rs -> new Answer(
                rs.getInt("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                Integer.parseInt(questionId)
        );

        return jdbcTemplate.query(sql, rm, questionId);
    }
}
