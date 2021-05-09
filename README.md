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
