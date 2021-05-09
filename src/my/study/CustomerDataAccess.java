package my.study;

public class CustomerDataAccess implements ICustomerDataAccess {
	public CustomerDataAccess() {
	}

	public String getCustomerName(int id) {
		return "홍길동"; // from DB
	}
}
