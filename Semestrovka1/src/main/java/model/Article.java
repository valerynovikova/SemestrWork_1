package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Article {
    private int id;
    private int userId;
    private String title;
    private String text;
    private String photoUrl;
    private String data;

    public Article(int userId, String title, String text, String photoUrl, String data) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.photoUrl = photoUrl;
        this.data = data;
    }
}
