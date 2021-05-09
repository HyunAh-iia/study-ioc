# study-ioc
study ioc using java

> 참고 자료
> - https://www.tutorialsteacher.com/ioc

## Step 0. 강하게 결합된 두 클래스
[코드보기](https://github.com/HyunAh-iia/study-ioc/pull/1/files)
### 코드 설명
- `DataAccess` 클래스 : 데이터베이스에 접근하여 데이터 제공
- `CustomerBusinessLogic` 클래스 : 고객 관련 도메인 로직을 담는 클래스
- `CustomerBusinessLogic` 클래스는 `DataAccess` 에게 의존하고 있다.
- `new` 키워드를 사용하여 `DataAccess` 객체를 생성함
     
### 문제점
- `DataAccess` 의 변화가 `CustomerBusinessLogic` 에도 영향을 미침
- `CustomerBusinessLogic` 가 `new` 키워드를 통해 `DataAccess` 의 객체 생성함. 
    `DataAccess` 가 클래스명을 변경하면 `new` 키워드를 사용하여 `DataAccess` 객체를 참조하는 모든 클래스를 찾아 코드를 변경해주어야함. 
    동일 클래스의 객체를 만들고 의존성을 유지하기 위한 반복적인 코드
- `CustomerBusinessLogic` 클래스가 `DataAccess` 객체를 생성하기 때문에 독립적으로 테스트가 불가능하다 (TDD) => `DataAccess` 클래스가 mock 클래스로 대체될 수 없기 때문