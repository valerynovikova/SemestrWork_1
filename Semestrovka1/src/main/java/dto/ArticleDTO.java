package dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArticleDTO {
    private final int id;
    private final String userNick;
    private final String title;
    private final String text;
    private final String photoUrl;
    private final String data;
}

