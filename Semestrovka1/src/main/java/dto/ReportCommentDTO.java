package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportCommentDTO {
    private final int id;
    private final UserDTO user;
    private final int reportId;
    private final String text;
}
