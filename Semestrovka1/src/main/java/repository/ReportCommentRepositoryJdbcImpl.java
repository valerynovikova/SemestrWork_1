package repository;

import lombok.AllArgsConstructor;
import model.ReportComment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public class ReportCommentRepositoryJdbcImpl implements ReportCommentRepository {

    private final DataSource dataSource;

    private final static Function<ResultSet, ReportComment> rowMapper = (rowMapper1 -> {
        try {
            int id = rowMapper1.getInt("id");
            int userId = rowMapper1.getInt("user_id");
            int reportId = rowMapper1.getInt("report_id");
            String text = rowMapper1.getString("text");
            return new ReportComment(id, userId, reportId, text);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    });

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM comment_report WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM comment_report";

    //language=SQL
    private static final String SQL_SAVE = "INSERT INTO comment_report (user_id, report_id, text) VALUES (?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_REPORT_ID = "SELECT * FROM comment_report WHERE report_id = ?";

    @Override
    public ReportComment findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return rowMapper.apply(resultSet);
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<ReportComment> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<ReportComment> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(rowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public void save(ReportComment reportComment) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setInt(1, reportComment.getUserId());
            statement.setInt(2, reportComment.getReportId());
            statement.setString(3, reportComment.getText());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<ReportComment> findAllByReportId(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_BY_REPORT_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<ReportComment> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(rowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }
}

