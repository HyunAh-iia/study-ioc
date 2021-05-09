package my.study;

public class CustomerBusinessLogic {
	DataAccess DataAccess;

	public CustomerBusinessLogic() {
		// 강하게 결합된 클래스 (CustomerBusinessLogic와 DataAccess)
		// 1. DataAccess 클래스의 구현체의 주소를 포함하고 있음
		// 2. DataAccess 클래스의 객체를 생성하고 생명주기를 관리함
		DataAccess = new DataAccess();
	}

	public String getCustomerName(int id) {
		return DataAccess.getCustomerName(id);
	}
}
