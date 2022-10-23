package dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
    private int id;
    private String userNick;
    private String title;
    private String text;
    private String photoUrl;
    private String data;
}
