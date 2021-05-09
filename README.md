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
- `CustomerBusinessLogic` 클래스는 여전히 `CustomerDataAccess` 클래스의 구상체(?)를 사용하고 있다. But the CustomerBusinessLogic class uses the concrete CustomerDataAccess class.
- 이것은 IoC를 통해 객체 생성 제어를 다른 클래스에 위임했음에도 클래스가 여전히 강하게 결합된 것을 의미함