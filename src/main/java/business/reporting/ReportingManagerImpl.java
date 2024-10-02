package business.reporting;

import entities.models.Stock;
import entities.repositories.ReportingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportingManagerImpl implements ReportingManager {

    private final ReportingRepository reportingRepository;

    public ReportingManagerImpl(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public void generateTotalSalesReport(LocalDate date) {
        // Generate report for today and compare with yesterday
        List<Object[]> salesReport = reportingRepository.getTotalSalesReport(date);
        BigDecimal totalSalesToday = salesReport.stream()
                .map(row -> (BigDecimal) row[2])
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Yesterday's report
        LocalDate yesterday = date.minusDays(1);
        List<Object[]> salesReportYesterday = reportingRepository.getTotalSalesReport(yesterday);
        BigDecimal totalSalesYesterday = salesReportYesterday.stream()
                .map(row -> (BigDecimal) row[2])
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("==== Total Sales Report for " + date + " ====");
        salesReport.forEach(row -> {
            System.out.println("Item Code: " + row[0] + ", Quantity Sold: " + row[1] + ", Total Price: " + row[2]);
        });

        System.out.println("\nTotal Sales Today: " + totalSalesToday);
        System.out.println("Total Sales Yesterday: " + totalSalesYesterday);
        System.out.println("Sales Difference: " + totalSalesToday.subtract(totalSalesYesterday));

        // Most sold categories
        List<Object[]> mostSoldCategories = reportingRepository.getMostSoldCategories(date);
        System.out.println("\n==== Most Sold Categories ====");
        mostSoldCategories.forEach(row -> {
            System.out.println("Category: " + row[0] + ", Total Sold: " + row[1]);
        });
    }

    @Override
    public void generateReshelvingReport(LocalDate date) {
        // Use the passed date for the report
        List<Stock> reshelvingReport = reportingRepository.getReshelvingReport(date);
        System.out.println("==== Reshelving Report for " + date + " ====");
        reshelvingReport.forEach(stock -> {
            System.out.println("Item Code: " + stock.getItemCode() +
                    ", Quantity in Stock: " + stock.getQuantityInStock() +
                    ", Reshelf Quantity: " + stock.getReshelfQuantity() +
                    ", Shelf Capacity: " + stock.getShelfCapacity() +
                    ", Batch Code: " + stock.getBatchCode() +
                    ", Expiry Date: " + stock.getExpiryDate());
        });
    }

    @Override
    public void generateReorderLevelReport(LocalDate date) {
        // Use the passed date for the report
        List<Stock> reorderReport = reportingRepository.getReorderLevelReport(date);
        System.out.println("==== Reorder Level Report for " + date + " ====");
        reorderReport.forEach(stock -> {
            System.out.println("Item Code: " + stock.getItemCode() +
                    ", Quantity in Stock: " + stock.getQuantityInStock() +
                    ", Minimum Reorder Level: " + stock.getReorderLevel() +
                    ", Batch Code: " + stock.getBatchCode());
        });
    }

    @Override
    public void generateStockReport(LocalDate date) {
        // Use the passed date for the report
        List<Stock> stockReport = reportingRepository.getStockReport(date);
        System.out.println("==== Stock Report for " + date + " ====");
        stockReport.forEach(stock -> {
            System.out.println("Item Code: " + stock.getItemCode() +
                    ", Quantity in Stock: " + stock.getQuantityInStock() +
                    ", Date of Purchase: " + stock.getDateOfPurchase() +
                    ", Expiry Date: " + stock.getExpiryDate() +
                    ", Batch Code: " + stock.getBatchCode());
        });
    }

    @Override
    public void generateBillReport(LocalDate date) {
        List<Object[]> billReport = reportingRepository.getBillReport(date);
        BigDecimal totalRevenue = BigDecimal.ZERO;
        int totalBills = billReport.size();

        System.out.println("==== Bill Report for " + date + " ====");
        for (Object[] row : billReport) {
            System.out.println("Bill ID: " + row[0] + ", Customer ID: " + row[1] + ", Total Price: " + row[2]);
            totalRevenue = totalRevenue.add((BigDecimal) row[2]);
        }

        System.out.println("\nTotal Bills: " + totalBills);
        System.out.println("Total Revenue: " + totalRevenue);

        // Average transaction value
        BigDecimal avgTransactionValue = totalRevenue.divide(BigDecimal.valueOf(totalBills), BigDecimal.ROUND_HALF_UP);
        System.out.println("Average Transaction Value: " + avgTransactionValue);
    }


}
