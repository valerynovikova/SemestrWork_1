package repository;

import model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);
    List<Article> findAll();
    Optional<Article> findById(int id);
    List<Article> findAllByUserId(int userId);
    List<Article> findAllByTitle(String title);
    void delete(int id);
}

