package payment;

import java.io.IOException;

public interface Payable {

	String processPayment(String custName, double amount) throws IOException;
}
