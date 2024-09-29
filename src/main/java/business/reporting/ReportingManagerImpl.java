package business.reporting;

import java.sql.Connection;

public class ReportingManagerImpl implements ReportingManager {

    private final Connection connection;

    public ReportingManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void generateSalesReport() {
        // Logic to generate the sales report
        System.out.println("Generating Sales Report...");
    }

    @Override
    public void generateStockReport() {
        // Logic to generate the stock report
        System.out.println("Generating Stock Report...");
    }

    @Override
    public void generateCustomerReport() {
        // Logic to generate the customer report
        System.out.println("Generating Customer Report...");
    }
}
