package com.epam.cinema.dao;

import com.epam.cinema.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Profile("database")
public class JdbcUserDao implements UserDao {

    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";

    @Value("${query.user.save}")
    private String save;

    @Value("${query.user.remove}")
    private String remove;

    @Value("${query.user.getById}")
    private String getById;

    @Value("${query.user.getAll}")
    private String getAll;

    @Value("${query.user.getByEmail}")
    private String getByEmail;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao (DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User getByEmail(String email) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(EMAIL, email);
        return jdbcTemplate.queryForObject(getByEmail, parameterSource, new UserRowMapper());
    }

    @Override
    public Long save(User object) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(FIRST_NAME, object.getFirstName())
                .addValue(LAST_NAME, object.getLastName())
                .addValue(EMAIL, object.getEmail())
                .addValue(BIRTHDAY, object.getBirthday());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(save, mapSqlParameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void remove(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(USER_ID, id);
        jdbcTemplate.update(remove, parameterSource);
    }

    @Override
    public User getById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(USER_ID, id);
        return jdbcTemplate.queryForObject(getById, parameterSource, new UserRowMapper());
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(getAll, new UserRowMapper());
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong(USER_ID));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            user.setEmail(resultSet.getString(EMAIL));
            user.setBirthday(resultSet.getDate(BIRTHDAY).toLocalDate());

            return user;
        }
    }
}