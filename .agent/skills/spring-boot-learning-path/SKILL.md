---
name: spring-boot-learning-path
description: Guides freshers learning Spring Boot from basics to advanced banking application development. Focuses on understanding fundamentals before implementing features.
---

# Spring Boot Learning Path for Banking Applications

Skill này hướng dẫn học Spring Boot theo lộ trình từ cơ bản đến nâng cao, đặc biệt cho banking domain.

## Learning Philosophy

### Nguyên tắc "Understand Before Implement"

1. **Hiểu CONCEPT trước**
   - Dependency Injection là gì? Tại sao cần?
   - Spring Container hoạt động ra sao?

2. **Hiểu WHY trước khi HOW**
   - Tại sao dùng @Transactional?
   - Trade-offs của mỗi annotation?

3. **Practice sau khi hiểu**
   - Implement sau khi explain được concept
   - Debug để hiểu sâu hơn

## Learning Levels

### Level 0: Java Fundamentals Check

Trước khi học Spring Boot, kiểm tra Java foundation:

**Questions:**
- "Em hiểu về Interfaces và Abstract classes chưa?"
- "Annotations trong Java hoạt động như thế nào?"
- "Exception handling: checked vs unchecked?"
- "Generics là gì?"
- "Stream API em có biết không?"

**If gaps found:**
"Em cần nắm vững Java core trước. Research:
- Java Interfaces and Polymorphism
- Java Annotations basics
- Java Exception handling best practices"

### Level 1: Spring Core Concepts

#### 1.1 Inversion of Control (IoC)

**Socratic Questions:**
```
"Em tạo object như thế nào bình thường?"
→ Student: "new MyClass()"

"Vậy nếu MyClass cần 5 dependencies thì sao?"
→ Student: "new MyClass(dep1, dep2, dep3, dep4, dep5)"

"Nếu dep1 thay đổi constructor thì sao?"
→ Student: "Phải sửa khắp nơi..."

"Vậy có cách nào tốt hơn không?"
→ Guide to IoC concept
```

**Research Assignment:**
- "Inversion of Control principle"
- "Dependency Injection vs Service Locator"
- "Spring IoC Container"

**Challenge:**
"Giải thích bằng lời của em: Spring Container làm gì?
Vẽ diagram minh họa object creation flow."

#### 1.2 Dependency Injection

**Understanding Exercise:**

Step 1: Hỏi
- "Em có hiểu DI là gì không?"
- "Constructor injection vs Setter injection vs Field injection?"

Step 2: Research
```
Keywords:
- "Dependency Injection types"
- "Constructor injection vs Field injection Spring"
- "Why constructor injection is preferred"
```

Step 3: Analysis
"So sánh 3 types:
- Pros & Cons mỗi cách
- Khi nào dùng cái nào?
- Banking system nên dùng cách nào? Tại sao?"

#### 1.3 Spring Beans & Scopes

**Learning Flow:**

1. **What is a Bean?**
   - "Em nghĩ Spring Bean khác gì với Java Object thông thường?"
   - Research: "Spring Bean lifecycle"

2. **Bean Scopes**
   Questions:
   - "Singleton nghĩa là gì?"
   - "Banking service nên là Singleton hay Prototype? Tại sao?"
   - "Request scope dùng khi nào?"

   Research: "Spring Bean scopes", "When to use each scope"

3. **Configuration Methods**
   Compare:
   - XML configuration (legacy)
   - Java configuration (@Configuration, @Bean)
   - Component scanning (@Component, @Service, @Repository)
   
   "Banking project nên dùng cách nào? Tại sao?"

### Level 2: Spring Boot Basics

#### 2.1 Spring Boot Auto-Configuration

**Understanding Check:**
- "Spring Boot khác gì với Spring Framework?"
- "Auto-configuration làm việc gì?"
- "Starter dependencies là gì?"

**Exercise:**
```
Tạo 1 empty Spring Boot project.
Quan sát:
1. pom.xml có gì?
2. @SpringBootApplication annotation làm gì?
3. Application.properties vs application.yml?

Research mỗi thứ em thấy lạ!
```

**Deep Dive Assignment:**
"Explain how @SpringBootApplication works:
- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

Mỗi annotation làm gì? Tại sao cần cả 3?"

#### 2.2 Layered Architecture

**Concept Check:**
- "Em hiểu về separation of concerns chưa?"
- "Tại sao cần chia layers?"

**Research:**
- "Layered Architecture pattern"
- "Spring MVC architecture"
- "Controller-Service-Repository pattern"

**Banking Context:**
```
"Vẽ diagram cho Account Management:
- Controller layer: làm gì?
- Service layer: làm gì?
- Repository layer: làm gì?

Mỗi layer có trách nhiệm gì?
Data flow qua các layers như thế nào?"
```

**Critical Questions:**
- "Business logic nên ở đâu? Tại sao không ở Controller?"
- "Validation nên ở layer nào?"
- "Transaction management ở đâu?"

### Level 3: REST API Development

#### 3.1 Spring Web MVC

**Learning Steps:**

Step 1: HTTP Fundamentals
- "Em hiểu về HTTP methods chưa? (GET, POST, PUT, DELETE, PATCH)"
- "Status codes: 200, 201, 400, 401, 404, 500 nghĩa là gì?"
- "Headers và Body khác nhau ra sao?"

Research if gaps found!

Step 2: Spring MVC Annotations
```
Research từng annotation:
- @RestController vs @Controller
- @RequestMapping vs @GetMapping, @PostMapping
- @PathVariable vs @RequestParam vs @RequestBody
- @ResponseStatus

Giải thích TỪNG CÁI và cho example use case trong banking
```

Step 3: Design API
```
Exercise: Design Account API
Em tự nghĩ ra:

1. List accounts: 
   - HTTP method? Endpoint? Response?

2. Get account detail:
   - HTTP method? Endpoint? Parameters? Response?

3. Create account:
   - HTTP method? Endpoint? Request body? Response? Status code?

4. Update account:
   - PUT hay PATCH? Tại sao?

Không code! Chỉ design API contract.
```

#### 3.2 Request Validation

**Questions:**
- "Tại sao cần validate input?"
- "Validate ở đâu? Controller hay Service?"
- "Client-side validation có đủ không?"

**Research:**
- "Bean Validation (JSR-303)"
- "@Valid vs @Validated"
- "Custom validators in Spring"

**Exercise:**
```
Account creation cần validate gì?
- Account number format?
- Initial balance: min? max? decimal places?
- Customer info: required fields?

Research @NotNull, @NotBlank, @Min, @Max, @Pattern, etc.
Liệt kê validation rules cho Account entity.
```

#### 3.3 Exception Handling

**Concept:**
- "Exception xảy ra ở service layer, ai xử lý?"
- "Client nhận được error message như thế nào?"

**Research:**
- "@ExceptionHandler"
- "@ControllerAdvice"
- "ResponseEntityExceptionHandler"
- "Custom exception classes"

**Banking Scenarios:**
```
Handle các cases:
1. Account not found
2. Insufficient balance
3. Invalid account number format
4. Database error

Mỗi case:
- Exception type?
- HTTP status code?
- Error response structure?
```

### Level 4: Data Persistence

#### 4.1 Spring Data JPA

**Foundation Check:**
- "ORM là gì? Tại sao cần?"
- "JPA vs Hibernate?"
- "Entity vs Table mapping?"

**Research Sequence:**

1. **Entities**
   ```
   Keywords:
   - "@Entity, @Table, @Id"
   - "@GeneratedValue strategies"
   - "@Column annotations"
   
   Exercise: Design Account entity
   - Fields cần thiết?
   - Data types? (Especially for money!)
   - Validation annotations?
   ```

2. **Relationships**
   ```
   Questions:
   - "Customer và Account: relationship gì?"
   - "Account và Transaction: relationship gì?"
   - "@OneToMany vs @ManyToOne"
   - "Bidirectional vs Unidirectional"
   - "Cascade types là gì?"
   - "FetchType.LAZY vs EAGER?"
   
   Research: "JPA relationships best practices"
   
   Challenge:
   "Thiết kế entities với relationships cho:
   - Customer
   - Account
   - Transaction
   
   Giải thích mỗi relationship và tại sao."
   ```

3. **Repositories**
   ```
   - "JpaRepository interface cung cấp gì?"
   - "Query methods naming convention?"
   - "Khi nào cần @Query?"
   
   Exercise:
   Với AccountRepository, em cần những methods nào?
   - findById (có sẵn)
   - findByAccountNumber → naming rule?
   - findByCustomerId → ?
   - Custom query cho complex search?
   ```

#### 4.2 Transaction Management

**CRITICAL for Banking!**

**Understanding Phase:**
```
Questions:
1. "Transaction là gì?"
2. "ACID properties nghĩa là gì?"
   - Atomicity: ?
   - Consistency: ?
   - Isolation: ?
   - Durability: ?

3. "Tại sao transfer tiền phải dùng transaction?"

Research: "Database transactions", "ACID properties explained"
```

**Spring @Transactional:**
```
Deep dive questions:
1. "Annotation này làm gì?"
2. "Đặt ở đâu? Method hay Class?"
3. "Default behavior là gì?"
4. "Rollback khi nào?"

Research:
- "@Transactional propagation types"
- "Isolation levels"
- "readOnly attribute"

Exercise:
Transfer money method cần:
- Propagation nào?
- Isolation level nào? Tại sao?
- ReadOnly = true hay false?
```

**Real Scenarios:**
```
Case 1: Transfer $100 from A to B
- Debit A: -$100
- Credit B: +$100
"Nếu credit fail thì sao? @Transactional xử lý như thế nào?"

Case 2: 2 users cùng transfer từ account A
"Race condition? Isolation level nào phù hợp?"
Research: "Pessimistic lock vs Optimistic lock"

Case 3: Read account balance while updating
"Dirty read problem? Giải quyết thế nào?"
```

### Level 5: Security

#### 5.1 Spring Security Basics

**Concepts:**
- "Authentication vs Authorization?"
- "Principal, Credentials, Authorities?"

**Research Path:**
```
1. "Spring Security architecture"
2. "Filter chain concept"
3. "Authentication providers"
4. "UserDetailsService"
```

**Exercise:**
"Banking app cần:
- Public endpoints nào? (login, register)
- Protected endpoints nào?
- Role-based access: USER, ADMIN, TELLER?

Design security rules (chưa cần code)."

#### 5.2 JWT Authentication

**Understanding:**
- "JWT structure: Header.Payload.Signature"
- "Stateless authentication nghĩa là gì?"
- "JWT vs Session-based?"

**Flow Challenge:**
```
"Vẽ diagram cho flow:
1. User login với username/password
2. Server generate JWT
3. Client gửi JWT trong requests
4. Server validate JWT

Mỗi bước xảy ra gì? Validate cái gì?"
```

### Level 6: Advanced Topics

#### 6.1 Caching

**When to introduce:** Sau khi có working app

**Questions:**
- "Tại sao cần cache?"
- "Cache ở đâu? Client, Server, Database?"
- "TTL là gì?"

**Banking Context:**
```
"Account balance có nên cache không? Tại sao?"
"Transaction history thì sao?"
"Reference data (currency codes, bank branches)?"

Research: 
- "Spring Cache abstraction"
- "Redis vs Ehcache"
- "Cache eviction strategies"
```

#### 6.2 Asynchronous Processing

**Scenario:**
"Gửi email confirmation sau khi transfer.
Block request hay async? Tại sao?"

**Research:**
- "@Async annotation"
- "CompletableFuture"
- "Thread pool configuration"

#### 6.3 Testing

**Test Pyramid:**
```
1. Unit Tests (70%)
   - "Test business logic riêng lẻ"
   - "Mockito để mock dependencies"
   - Research: "JUnit 5", "Mockito framework"

2. Integration Tests (20%)
   - "Test với real database"
   - "@SpringBootTest"
   - Research: "Testcontainers"

3. E2E Tests (10%)
   - "Test complete flows"
   - Research: "REST Assured"
```

**Exercise:**
"Test AccountService:
- Test successful transfer
- Test insufficient balance
- Test invalid account
- Test concurrent transfers

Outline test cases (không cần code chi tiết)."

## Learning Projects Progression

### Project 1: Basic CRUD
- Account management (Create, Read, Update)
- Simple REST API
- In-memory database (H2)

### Project 2: Add Complexity
- Customer management
- Account-Customer relationships
- MySQL database
- Basic validation

### Project 3: Transactions
- Transfer money
- Transaction history
- @Transactional usage
- Exception handling

### Project 4: Security
- JWT authentication
- Role-based authorization
- Secure endpoints

### Project 5: Production-Ready
- Logging (SLF4J)
- Monitoring (Actuator)
- API documentation (OpenAPI/Swagger)
- Docker containerization

## Common Pitfalls

### Pitfall 1: Copy-Paste without Understanding
❌ "Em copy code từ StackOverflow"

**Mentor:**
"Code đó làm gì? Explain từng dòng.
Tại sao nó work? Trade-offs?"

### Pitfall 2: Skipping Fundamentals
❌ "Em muốn học Microservices, Kafka luôn"

**Mentor:**
"Em đã master được:
- Spring Core concepts?
- Data JPA?
- Transaction management?

Fundamentals trước, advanced sau!"

### Pitfall 3: Not Reading Documentation
❌ "Annotation này làm gì nhỉ?"

**Mentor:**
"Em đã đọc Spring docs chưa?
Official documentation > random blogs.
Search: 'Spring Framework Reference Documentation'."

## Weekly Learning Rhythm

**Monday:** New concept introduction
- Socratic discussion
- Research assignment

**Wednesday:** Implementation
- Student codes
- Mentor reviews approach (not code!)

**Friday:** Reflection
- "Em học được gì?"
- "Vấn đề nào khó nhất?"
- "Sẽ apply vào banking project như thế nào?"

## Success Metrics

✅ Explain concepts without looking at notes
✅ Debug issues independently (Google errors, read stack traces)
✅ Ask "why" before asking "how"
✅ Read official docs instead of random blogs
✅ Write code with understanding, not copy-paste

## Final Advice for Freshers

"Spring Boot rộng lắm. Đừng cố học hết một lúc.
Master fundamentals → Apply → Learn advanced → Repeat.

Mỗi concept, hãy:
1. Hiểu WHY it exists
2. Hiểu HOW it works
3. Practice với toy example
4. Apply vào banking project
5. Teach lại cho người khác (best way to learn!)

Rome không xây trong 1 ngày. Banking system cũng vậy."