package business.reporting;

import entities.models.Stock;
import entities.repositories.ReportingRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReportingManagerImpl implements ReportingManager {

    private final ReportingRepository reportingRepository;

    public ReportingManagerImpl(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public void generateTotalSalesReport() {
        List<Object[]> salesReport = reportingRepository.getTotalSalesReport();
        System.out.println("==== Total Sales Report ====");
        salesReport.forEach(row -> {
            System.out.println("Item Code: " + row[0] + ", Quantity Sold: " + row[1] + ", Total Price: " + row[2]);
        });
    }

    @Override
    public void generateReshelvingReport() {
        List<Stock> reshelvingReport = reportingRepository.getReshelvingReport();
        System.out.println("==== Reshelving Report ====");
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
    public void generateReorderLevelReport() {
        List<Stock> reorderReport = reportingRepository.getReorderLevelReport();
        System.out.println("==== Reorder Level Report ====");
        reorderReport.forEach(stock -> {
            System.out.println("Item Code: " + stock.getItemCode() +
                    ", Quantity in Stock: " + stock.getQuantityInStock() +
                    ", Minimum Reorder Level: " + stock.getReorderLevel() +
                    ", Batch Code: " + stock.getBatchCode());
        });
    }

    @Override
    public void generateStockReport() {
        List<Stock> stockReport = reportingRepository.getStockReport();
        System.out.println("==== Stock Report ====");
        stockReport.forEach(stock -> {
            System.out.println("Item Code: " + stock.getItemCode() +
                    ", Quantity in Stock: " + stock.getQuantityInStock() +
                    ", Date of Purchase: " + stock.getDateOfPurchase() +
                    ", Expiry Date: " + stock.getExpiryDate() +
                    ", Batch Code: " + stock.getBatchCode());
        });
    }

    @Override
    public void generateBillReport() {
        List<Object[]> billReport = reportingRepository.getBillReport();
        System.out.println("==== Bill Report ====");
        billReport.forEach(row -> {
            System.out.println("Bill ID: " + row[0] +
                    ", Customer ID: " + row[1] +
                    ", Total Price: " + row[2]);
        });
    }


}
