package my.study;

public class CustomerBusinessLogic {
	private ICustomerDataAccess customerDataAccess;

	public CustomerBusinessLogic() {
		customerDataAccess = DataAccessFactory.getDataAccessObject();
	}

	public String getCustomerName(int id) {
		return customerDataAccess.getCustomerName(id);
	}
}
