package my.study;

public class CustomerService {
	private CustomerBusinessLogic customerBusinessLogic;

	public CustomerService() {
		customerBusinessLogic = new CustomerBusinessLogic(new CustomerDataAccess());
	}

	public String getCustomerName(int id) {
		return customerBusinessLogic.getCustomerName(id);
	}
}
