package my.study;

public class CustomerBusinessLogic {

	public CustomerBusinessLogic() {
	}

	public String getCustomerName(int id) {
		CustomerDataAccess customerDataAccess = DataAccessFactory.getDataAccessObject();
		return customerDataAccess.getCustomerName(id);
	}
}
