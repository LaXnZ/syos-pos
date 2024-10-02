package business.reporting;

import java.time.LocalDate;

public interface ReportingManager {
    void generateTotalSalesReport(LocalDate date);
    void generateReshelvingReport(LocalDate date);
    void generateReorderLevelReport(LocalDate date);
    void generateStockReport(LocalDate date);
    void generateBillReport(LocalDate date);
}
