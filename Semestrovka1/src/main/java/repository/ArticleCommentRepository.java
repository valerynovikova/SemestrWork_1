package repository;


import model.ArticleComment;

import java.util.List;

public interface ArticleCommentRepository {
    ArticleComment findById(int id);
    List<ArticleComment> findAll();
    void save(ArticleComment articleComment);
    List<ArticleComment> findAllByReportId(int id);//????

}

