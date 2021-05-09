package my.study;

public class DataAccessFactory {
	public static CustomerDataAccess getDataAccessObject() {
		return new CustomerDataAccess();
	}
}
