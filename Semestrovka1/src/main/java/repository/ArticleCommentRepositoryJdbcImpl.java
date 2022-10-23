package repository;

import lombok.AllArgsConstructor;
import model.ArticleComment;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public class ArticleCommentRepositoryJdbcImpl implements ArticleCommentRepository{

    private final DataSource dataSource;

    private final static Function<ResultSet, ArticleComment> rowMapper = (rowMapper1 -> {
        try {
            int id = rowMapper1.getInt("id");
            int userId = rowMapper1.getInt("user_id");
            int reportId = rowMapper1.getInt("article_id");
            String text = rowMapper1.getString("text");
            return new ArticleComment(id, userId, reportId, text);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    });

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM comment_article WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM comment_article";

    //language=SQL
    private static final String SQL_SAVE = "INSERT INTO comment_article (user_id, article_id, text) VALUES (?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_REPORT_ID = "SELECT * FROM comment_article WHERE article_id = ?";

    @Override
    public ArticleComment findById(int id) {
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
    public List<ArticleComment> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<ArticleComment> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(rowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public void save(ArticleComment articleComment) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setInt(1, articleComment.getUserId());
            statement.setInt(2, articleComment.getArticleId());
            statement.setString(3, articleComment.getText());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }



    @Override
    public List<ArticleComment> findAllByReportId(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_BY_REPORT_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<ArticleComment> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(rowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }
}

