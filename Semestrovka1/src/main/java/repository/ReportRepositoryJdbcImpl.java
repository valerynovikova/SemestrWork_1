package repository;

import lombok.AllArgsConstructor;
import model.Report;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class ReportRepositoryJdbcImpl implements ReportRepository {

    private final DataSource dataSource;

    //language=SQL
    private static final String SQL_SAVE_REPORT = "INSERT INTO reports(user_id, title, text, photo_url, data) VALUES (?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM reports";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM reports WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM reports WHERE user_id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_TITLE = "SELECT * FROM reports WHERE title ILIKE ?";

    private final Function<ResultSet, Report> reportRowMapper = (row) -> {
        try {
            int id = row.getInt("id");
            int userId = row.getInt("user_id");
            String title = row.getString("title");
            String text = row.getString("text");
            String photoUrl = row.getString("photo_url");
            String data = row.getString("data");
            return new Report(id, userId, title, text, photoUrl, data);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(Report report) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_REPORT)) {
            statement.setInt(1, report.getUserId());
            statement.setString(2, report.getTitle());
            statement.setString(3, report.getText());
            statement.setString(4, report.getPhotoUrl());
            statement.setString(5, report.getData());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Report> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Report> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(reportRowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public Optional<Report> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet row = statement.executeQuery();
            if (row.next()){
                Report report = reportRowMapper.apply(row);
                return Optional.of(report);
            } else {
                return Optional.empty();
            }
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Report> findAllByUserId(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {
            statement.setInt(1, userId);
            List<Report> reports = new ArrayList<>();
            ResultSet row = statement.executeQuery();
            while (row.next()){
                reports.add(reportRowMapper.apply(row));
            }
            return reports;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Report> findAllByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_TITLE)) {
            statement.setString(1, "%" + title + "%");
            List<Report> reports = new ArrayList<>();
            ResultSet row = statement.executeQuery();
            while (row.next()){
                reports.add(reportRowMapper.apply(row));
            }
            return reports;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public void delete(int id) {

    }
}

