package dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArticleCommentDTO {
    private final int id;
    private final UserDTO user;
    private final int articleId;
    private final String text;
}

