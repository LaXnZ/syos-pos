package creators;

import business.reporting.ReportingManager;
import business.reporting.ReportingManagerImpl;
import entities.repositories.ReportingRepository;

public class ReportFactory {

    private ReportingRepository reportingRepository;

    public ReportFactory(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    public ReportingManager createReportingManager() {
        return new ReportingManagerImpl(reportingRepository);
    }
}
