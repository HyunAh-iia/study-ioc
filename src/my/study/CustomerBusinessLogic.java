package my.study;

public class CustomerBusinessLogic implements IDataAccessDependency {
	private ICustomerDataAccess customerDataAccess;

	public CustomerBusinessLogic() {
	}

	public String getCustomerName(int id) {
		return customerDataAccess.getCustomerName(id);
	}

	@Override
	public void setDependency(ICustomerDataAccess customerDataAccess) {
		this.customerDataAccess = customerDataAccess;
	}
}
