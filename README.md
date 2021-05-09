# study-ioc
study ioc using java

> 참고 자료
> - https://www.tutorialsteacher.com/ioc

## Step 0. 강하게 결합된 두 클래스
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/1/files)
### 코드 설명
- `DataAccess` 클래스 : 데이터베이스에 접근하여 데이터 제공
- `CustomerBusinessLogic` 클래스 : 고객 관련 도메인 로직을 담는 클래스
- `CustomerBusinessLogic` 클래스는 `DataAccess` 에게 의존하고 있음
- `new` 키워드를 사용하여 `DataAccess` 객체를 생성함
     
### 문제점
- `DataAccess` 의 변화가 `CustomerBusinessLogic` 에도 영향을 미침
- `CustomerBusinessLogic` 가 `new` 키워드를 통해 `DataAccess` 의 객체 생성함
    `DataAccess` 가 클래스명을 변경하면 `new` 키워드를 사용하여 `DataAccess` 객체를 참조하는 모든 클래스를 찾아 코드를 변경해주어야함
    동일 클래스의 객체를 만들고 의존성을 유지하기 위한 반복적인 코드
- `CustomerBusinessLogic` 클래스가 `DataAccess` 객체를 생성하기 때문에 독립적으로 테스트가 불가능하다 (TDD) => `DataAccess` 클래스가 mock 클래스로 대체될 수 없기 때문

## Step 1. IoC 원칙 구현을 위해 Factory Pattern 이용
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/2/files)
### IoC 원칙이란?
- IoC는 객체지향 설계에서 클래스간의 결합도를 느슨하게 하기 위해 다양한 종류의 제어를 반전시킬 것을 권장하는 디자인 원칙
- IoC를 구현하여 느슨하게 결합된 클래스를 만들어서 테스트가 용이하고, 지속가능하며 확장성있게 만들 수 있음

### 코드 설명
- 종속된 객체 생성을 제어하기 (Control Over the Dependent Object Creation)
- `CustomerBusinessLogic` 클래스가 `CustomerDataAccess(이전 명칭 : DataAccess)` 클래스에 대한 제어를 Factory 패턴을 적용한 `DataAccessFactory` 클래스에게 위임함으로써 `CustomerDataAccess` 클래스에게 종속을 회피함

### 문제점
- `CustomerBusinessLogic` 클래스는 여전히 `CustomerDataAccess` 클래스의 구상체(?)를 사용하고 있다. `But the CustomerBusinessLogic class uses the concrete CustomerDataAccess class.`
- 이것은 IoC를 통해 객체 생성 제어를 다른 클래스에 위임했음에도 클래스가 여전히 강하게 결합된 것을 의미함

## Step 2. DIP 원칙
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/3/files)
### DIP 원칙이란
- 상위 레벨의 모듈은 절대 하위 레벨 모듈에 의존하지 않는다. 둘 다 추상화에 의존해야한다. `High-level modules should not depend on low-level modules. Both should depend on the abstraction.`
- 추상화는 절대 세부 사항(details)에 의존하면 안된다. `Abstractions should not depend on details. Details should depend on abstractions.`

### 코드 설명
- DIP (Dependency Inversion Principle) 원칙을 IoC 원칙과 함께 적용해 이전 단계에서 남아있던 결합도를 낮춤
- DIP 원칙 구현을 위해 첫번째로 해야할 것은 상위 레벨 모듈(클래스)와 하위 레벨 모듈(클래스)를 결정해야 함. 이전 단계의 예제에서는 `CustomerBusinessLogic` 클래스가 `CustomerDataAccess` 클래스에 종속되어 있다. 그렇기 때문에 `CustomerBusinessLogic` 는 상위 레벨이고,  `CustomerDataAccess` 는 하위 레벨임. DIP의 첫번째 규칙을 따르자면 `CustomerBusinessLogic` 클래스는 `CustomerDataAccess` 의 구상체(?)에 의존하는 대신, 두 클래스 모두 추상화에 의존해야함
- 상위 레벨 모듈(`CustomerBusinessLogic`)과 하위 레벨 모듈(`CustomerDataAccess`)을 추상화(`ICustomerDataAccess`)에 의존함으로써 DIP를 구현함. 또한 추상화(`ICustomerDataAccess`)는 details(`CustomerDataAccess`)에 의존하지 않고, details(`CustomerDataAccess`)는 추상화의 의존적임
- 두 클래스는 추상화(`ICustomerDataAccess`)를 바라보고 있기 때문에 느슨하게 결합되었다 말할 수 있음. 앞으로 ICustomerDataAccess 를 구현한 다른 클래스들 또한 쉽고 안전하게(?) 사용할 수 있게 되었음

### 문제점
- 여전히 느슨한 결합을 충분히 이뤄냈다고 말하기 어려움
- `CustomerBusinessLogic` 클래스는 `ICustomerDataAccess` 를 얻기 위해 팩토리 클래스(`DataAccessFactory`)를 참조하고 있기 떄문임. 이 부분이 바로 `DI(Dependency Injection) 패턴` 의 적용이 필요한 부분임 (다음 단계에서 DI와 Strategy 패턴을 사용하여 개선해보겠음)

## Step 3. IoC 원칙 구현을 위해 DI Pattern 이용
이제 DI와 Strategy 패턴을 통해 클래스간의 결합도를 낮추어 보자!

### DI 패턴이란?
DI(Dependency Injection, 의존관계 주입)는 종속된 객체의 생성을 반전시키는 IoC 원칙을 구현한 디자인 패턴이다. 이전에 DIP 원칙에 따라 추상화하여 클래스간의 결합도를 낮추었다. 하지만 이전 포스팅 예제에서 여전히 `CustomerBusinessLogic` 클래스가 `ICustomerDataAccess` 객체를 반환하기 위해 `DataAccessFactory` 클래스를 참조하고 있다. 이제 DI와 Strategy 패턴을 구현하여 종속된 클래스의 객체 생성을 완전히 클래스 밖으로 내보낼 수 있다.
Injector 클래스로서 `CustomerService` 클래스를 만들고, Service 클래스인 `CustomerDataAccess` 클래스의 객체를 Client 클래스인 `CustomerBusinessLogic` 에게 세 가지 방법으로 주입해보겠다.

### DI 패턴 적용 3 가지 방법 
- 생성자 주입(Constructor Injection) : Injector 클래스가 Client 클래스의 생성자를 통해 Service 클래스(dependency)의 객체를 주입함
- 메서드 주입(Method Injection) : Client 클래스가 어떤 인터페이스를 구현하여 어떤 메소드가 종속성을 제공하는 지 선언하고, Injector 클래스는 이 인터페이스를 사용하여 Client 클래스에 종속성을 제공함
- Property Injection : Setter Injection처럼 Client 클래스의 public property를 통해 객체를 주입함

### 생성자 주입(Constructor Injection)
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/4/files)
- `CustomerService` 클래스가 `CustomerDataAccess` 클래스의 객체를 생성하여 `CustomerBusinessLogic` 클래스에게 의존 관계를 주입함
- 이제 `CustomerBusinessLogic` 클래스는 `new` 키워드로 `CustomerDataAccess` 클래스 객체를 생성하거나 Factory 클래스인 `DataAccessFactory` 를 사용할 필요가 없어짐
- 이로서 `CustomerDataAccess` 와 `CustomerBusinessLogic` 클래스는 더욱 더 낮은 결합도를 가지가 됨

### 메서드 주입(Method Injection)
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/5/files)
- 클래스 메서드나 인터페이스 메서드를 통해 의존관계를 주입할 수 있음 
- `CustomerBusinessLogic` 클래스가 `setDependency()` 메서드를 갖고 있는 `IDataAccessDependency` 인터페이스를 구현함 
- 그래서 Injector 클래스인 `CustomerService` 가 `setDependency()` 메서드를 통해 의존 관계를 주입할 수 있음

### Property Injection
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/6/files)
- 의존관계를 public한 필드를 통해 제공하는 것을 Property Injection이라고 함
- `CustomerBusinessLogic` 는  `ICustomerDataAccess` 타입의 public 필드 `dataAccess`에 대한 getter, setter 메서드를 구현함 
- 그래서 `CustomerService` 클래스가 public한 필드인 `dataAccess`를 통해 `CustomerDataAccess` 클래스 객체 생성을 할 수 있게 됨
