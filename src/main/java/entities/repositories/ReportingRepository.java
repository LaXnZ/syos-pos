package entities.repositories;

import entities.models.Stock;
import java.util.List;

public interface ReportingRepository {
    List<Object[]> getTotalSalesReport();
    List<Stock> getReshelvingReport();
    List<Stock> getReorderLevelReport();
    List<Stock> getStockReport();
    List<Object[]> getBillReport();


}
