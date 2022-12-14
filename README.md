# Account-ManageSystem-Project
*** 
## 프로젝트 소개
### Spring Boot & JAVA 기반 계좌 관리 시스템 💸

## :pushpin: 기술 스택 ##
- Spring Boot
- Java (JDK 11)
- H2 DB
- Embedded Redis
- TestCode (Mockito & JUnit)
- Ultimate IntelliJ Edition

## :pushpin: 디렉토리 구성 ##
```bash
├── aop
│   └── AccountLock
│   └── AccountLockidInterface
│
├── config
│   └── JpaAuditingConfiguration
│   └── LocalRedisConfig
│   └── RedisRepositoryConfig
│
├── controller
│   ├── AccountController
│   └── TransactionController
│  
├── domain
│   ├── Account
│   └── AccountUser
│   └── BaseEntity
│   └── Transaction
│
├── dto
│   ├── AccountDto
│   ├── AccountInfo
│   ├── CancelBalance
│   ├── CreateAccount
│   ├── DeleteAccount
│   ├── ErrorResponse
│   ├── QueryTransactionResponse
│   ├── TransactionDto
│   └── UseBalance
│
├── exception
│   ├── GlobalExceptionHandler
│   └── AccountException
│ 
├── repository
│   ├── AccountRepository
│   ├── AccountUserRepository
│   └──  TransactionRepository
│
├── service
│   ├── AccountService
│   ├── LockAopAspect
│   ├── LockService
│   └── TransactionService
│
├── type
│   ├── AccountStatus
│   ├── ErrorCode
│   ├── TransactionResultType
│   └── TransactionType
│
├── AccountApplication
└── application.yml
```

## :pushpin: 프로젝트 기능 ##
- 사용자, 계좌, 거래 정보 관리
- 계좌 ( 생성 / 해지 / 확인 API )
- 거래 정보 ( 잔액 사용 / 잔액 사용 취소 / 거래 확인 API)