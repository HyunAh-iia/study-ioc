package my.study;

public class CustomerBusinessLogic {
	private ICustomerDataAccess customerDataAccess;

	public CustomerBusinessLogic(ICustomerDataAccess customerDataAccess) {
		this.customerDataAccess = customerDataAccess;
	}

	public CustomerBusinessLogic() {
		customerDataAccess = new CustomerDataAccess();
	}

	public String getCustomerName(int id) {
		return customerDataAccess.getCustomerName(id);
	}
}
