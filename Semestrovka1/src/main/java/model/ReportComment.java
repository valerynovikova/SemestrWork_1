package model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportComment {
    private int id;
    private int userId;
    private int reportId;
    private String text;

    public ReportComment(int userId, int reportId, String text) {
        this.userId = userId;
        this.reportId = reportId;
        this.text = text;
    }
}


