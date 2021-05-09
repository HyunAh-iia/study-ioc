package my.study;

public class DataAccessFactory {
	public static ICustomerDataAccess getDataAccessObject() {
		return new CustomerDataAccess();
	}
}
