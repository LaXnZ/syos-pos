package utils;

import entities.models.Stock;
import java.util.List;

public class ReportFormatter {

    public String formatBillReport(List<Object[]> billReport) {
        StringBuilder formattedReport = new StringBuilder("=== Bill Report ===\n");
        for (Object[] row : billReport) {
            formattedReport.append("Bill ID: ").append(row[0])
                    .append(", Customer ID: ").append(row[1])
                    .append(", Total Price: ").append(row[2])
                    .append("\n");
        }
        return formattedReport.toString();
    }

    public String formatSalesReport(List<Object[]> salesReport) {
        StringBuilder formattedReport = new StringBuilder("=== Total Sales Report ===\n");
        for (Object[] row : salesReport) {
            formattedReport.append("Item Name: ").append(row[0])
                    .append(", Quantity Sold: ").append(row[1])
                    .append(", Total Revenue: ").append(row[2])
                    .append("\n");
        }
        return formattedReport.toString();
    }

    public String formatInventoryReport(List<Stock> inventoryReport) {
        StringBuilder formattedReport = new StringBuilder("=== Inventory Report ===\n");
        for (Stock stock : inventoryReport) {
            formattedReport.append("Item Code: ").append(stock.getItemCode())
                    .append(", Batch Code: ").append(stock.getBatchCode())
                    .append(", Quantity in Stock: ").append(stock.getQuantityInStock())
                    .append(", Date of Purchase: ").append(stock.getDateOfPurchase())
                    .append(", Expiry Date: ").append(stock.getExpiryDate())
                    .append("\n");
        }
        return formattedReport.toString();
    }
}
