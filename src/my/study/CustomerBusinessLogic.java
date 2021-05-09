package my.study;

public class CustomerBusinessLogic {
	public ICustomerDataAccess dataAccess;

	public CustomerBusinessLogic() {
	}

	public void setDataAccess(ICustomerDataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	public ICustomerDataAccess getDataAccess() {
		return this.dataAccess;
	}

	public String getCustomerName(int id) {
		return dataAccess.getCustomerName(id);
	}
}
