package business.reporting;

import entities.models.Stock;
import java.util.List;

public interface ReportingManager {
    void generateTotalSalesReport();
    void generateReshelvingReport();
    void generateReorderLevelReport();
    void generateStockReport();
    void generateBillReport();

}
