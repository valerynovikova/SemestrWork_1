package repository;

import model.ReportComment;

import java.util.List;

public interface ReportCommentRepository {
    ReportComment findById(int id);
    List<ReportComment> findAll();
    void save(ReportComment guideComment);
    List<ReportComment> findAllByReportId(int id);
}
