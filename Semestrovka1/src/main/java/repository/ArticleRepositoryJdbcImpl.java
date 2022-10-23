package repository;

import lombok.AllArgsConstructor;
import model.Article;


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
public class ArticleRepositoryJdbcImpl implements ArticleRepository{

    private final DataSource dataSource;

    //language=SQL
    private static final String SQL_SAVE_ARTICLE = "INSERT INTO articles(user_id, title, text, photo_url, data) VALUES (?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM articles";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM articles WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM articles WHERE user_id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_TITLE = "SELECT * FROM articles WHERE title ILIKE ?";

    private final Function<ResultSet, Article> articleRowMapper = (row) -> {
        try {
            int id = row.getInt("id");
            int userId = row.getInt("user_id");
            String title = row.getString("title");
            String text = row.getString("text");
            String photoUrl = row.getString("photo_url");
            String data = row.getString("data");
            return new Article(id, userId, title, text, photoUrl, data);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };


    @Override
    public void save(Article article) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_ARTICLE)) {
            statement.setInt(1, article.getUserId());
            statement.setString(2, article.getTitle());
            statement.setString(3, article.getText());
            statement.setString(4, article.getPhotoUrl());
            statement.setString(5, article.getData());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Article> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Article> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(articleRowMapper.apply(resultSet));
            }
            return list;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public Optional<Article> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet row = statement.executeQuery();
            if (row.next()){
                Article article = articleRowMapper.apply(row);
                return Optional.of(article);
            } else {
                return Optional.empty();
            }
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Article> findAllByUserId(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {
            statement.setInt(1, userId);
            List<Article> article = new ArrayList<>();
            ResultSet row = statement.executeQuery();
            while (row.next()){
                article.add(articleRowMapper.apply(row));
            }
            return article;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public List<Article> findAllByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_TITLE)) {
            statement.setString(1, "%" + title + "%");
            List<Article> articles = new ArrayList<>();
            ResultSet row = statement.executeQuery();
            while (row.next()){
                articles.add(articleRowMapper.apply(row));
            }
            return articles;
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }

    @Override
    public void delete(int id) {

    }
}

