package my.study;

public class CustomerService {
	private CustomerBusinessLogic customerBusinessLogic;

	public CustomerService() {
		customerBusinessLogic = new CustomerBusinessLogic();
		customerBusinessLogic.dataAccess = new CustomerDataAccess();
	}

	public String getCustomerName(int id) {
		return customerBusinessLogic.getCustomerName(id);
	}
}
