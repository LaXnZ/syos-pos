package entities.repositories;

import entities.models.Stock;

import java.time.LocalDate;
import java.util.List;

public interface ReportingRepository {
    List<Object[]> getTotalSalesReport(LocalDate date);
    List<Stock> getReshelvingReport(LocalDate date);
    List<Stock> getReorderLevelReport(LocalDate date);
    List<Stock> getStockReport(LocalDate date);
    List<Object[]> getBillReport(LocalDate date);
    List<Object[]> getMostSoldCategories(LocalDate date);
}
