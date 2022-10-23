package model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArticleComment {
    private int id;
    private int userId;
    private int articleId;
    private String text;

    public ArticleComment(int userId, int articleId, String text) {
        this.userId = userId;
        this.articleId = articleId;
        this.text = text;
    }
}

