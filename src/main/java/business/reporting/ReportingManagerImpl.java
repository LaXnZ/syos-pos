package business.reporting;

import entities.models.Stock;
import entities.repositories.ReportingRepository;

import java.time.LocalDate;
import java.util.List;

public class ReportingManagerImpl implements ReportingManager {

    private final ReportingRepository reportingRepository;

    public ReportingManagerImpl(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public void generateTotalSalesReport(LocalDate date) {
        // Use the passed date for the report
        List<Object[]> salesReport = reportingRepository.getTotalSalesReport(date);
        System.out.println("==== Total Sales Report for " + date + " ====");
        salesReport.forEach(row -> {
            System.out.println("Item Code: " + row[0] + ", Quantity Sold: " + row[1] + ", Total Price: " + row[2]);
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
        // Use the passed date for the report
        List<Object[]> billReport = reportingRepository.getBillReport(date);
        System.out.println("==== Bill Report for " + date + " ====");
        billReport.forEach(row -> {
            System.out.println("Bill ID: " + row[0] +
                    ", Customer ID: " + row[1] +
                    ", Total Price: " + row[2]);
        });
    }
}
