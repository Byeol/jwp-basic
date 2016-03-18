package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void insert(User user) {
        this.jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail()
        );
    }

    public User findByUserId(String userId) {
        return this.jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", new UserMapper(), userId);
    }

    public List<User> findAll() {
        return this.jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", new UserMapper());
    }

    public void update(User user) {
        this.jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUserId()
        );
    }

    private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs) throws SQLException {
            return new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
        }
    }
}
