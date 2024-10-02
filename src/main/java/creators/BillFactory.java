package creators;

import entities.models.Bill;
import entities.models.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillFactory {
    public static Bill create(Customer customer) {
        return new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
    }
}
