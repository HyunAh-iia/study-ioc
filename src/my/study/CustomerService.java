package my.study;

public class CustomerService {
	private CustomerBusinessLogic customerBusinessLogic;

	public CustomerService() {
		customerBusinessLogic = new CustomerBusinessLogic();
		customerBusinessLogic.setDependency(new CustomerDataAccess());
	}

	public String getCustomerName(int id) {
		return customerBusinessLogic.getCustomerName(id);
	}
}
