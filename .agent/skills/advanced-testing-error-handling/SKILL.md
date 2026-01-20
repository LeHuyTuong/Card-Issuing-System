---
name: advanced-testing-error-handling
description: Guides students through production-ready testing strategies and robust error handling for banking systems. Focuses on test-driven thinking and defensive programming.
---

# Advanced Testing & Error Handling for Banking Systems

Month 3, Week 9-10: Biến banking system từ "works on my machine" thành **production-ready**.

## Philosophy: "If It's Not Tested, It's Broken"

### Banking Code Reality:
```
Bug trong TODO app: User annoyed
Bug trong Banking app: MONEY LOST, LEGAL ISSUES, TRUST DESTROYED

→ Testing không phải optional, là MANDATORY!
→ Error handling không phải "nice to have", là CRITICAL!
```

## Week 9: Testing Strategy

### Day 1-2: Testing Pyramid Understanding

**Questions First:**
```
"Em nghĩ nên test cái gì?
Tại sao cần test?
Test bao nhiêu là đủ?
Manual test có đủ không?"
```

**Research Assignment:**
```
Keywords:
- "Testing Pyramid (Unit, Integration, E2E)"
- "Test Coverage vs Test Quality"
- "TDD (Test-Driven Development)"
- "Given-When-Then pattern"

Read:
- Martin Fowler's Testing articles
- Spring Boot Testing documentation
```

**Self-Discovery Exercise:**
```
"Vẽ Testing Pyramid cho Banking system:
- Unit tests: test cái gì? Bao nhiêu %?
- Integration tests: test cái gì? Bao nhiêu %?
- E2E tests: test cái gì? Bao nhiêu %?

Justify tỷ lệ em chọn!"
```

### Day 3-5: Unit Testing Deep Dive

#### Service Layer Testing

**Challenge:**
```
"Test AccountService.transfer() method.
List ra MỌI scenarios cần test:

Happy paths:
- ?

Edge cases:
- ?

Error cases:
- ?

Concurrency cases:
- ?

Em list được bao nhiêu test cases?"
```

**Expected Answer (guide if stuck):**
```
Happy Paths:
□ Transfer successful between valid accounts
□ Balance updated correctly
□ Transaction recorded

Edge Cases:
□ Transfer amount = 0
□ Transfer amount = exact balance
□ Transfer to same account (from = to)
□ Very large amount (BigDecimal precision)
□ Decimal places (0.01, 0.001)

Error Cases:
□ Source account not found
□ Destination account not found
□ Insufficient balance
□ Negative amount
□ Null parameters
□ Account inactive/frozen

Concurrency:
□ Multiple transfers from same account
□ Race conditions on balance update

"Em missed bao nhiêu cases?
Đây là tư duy test comprehensive!"
```

**Mocking Strategy:**
```
Question: "Em sẽ mock gì? Tại sao?

DON'T mock:
- Code under test (AccountService)
- Simple objects (Account, Transaction)
- Value objects (Money, AccountNumber)

DO mock:
- External dependencies (Repository)
- External services (EmailService, PaymentGateway)
- Time-dependent (Clock, DateProvider)

Research: "What to mock and what not to mock"
```

**Exercise:**
```
"Write test for transfer() WITHOUT code.
Just write test skeleton:

@Test
void shouldTransferSuccessfully() {
    // Given: Setup what?
    
    // When: Call what?
    
    // Then: Assert what?
}

List ra 10 test methods như vậy.
Anh sẽ review approach của em!"
```

**Common Mistakes to Catch:**
```
❌ Test implementation, not behavior
❌ Testing framework code
❌ 100% coverage but weak assertions
❌ Brittle tests (break on refactor)
❌ Test interdependence

Guide student to avoid these!
```

#### Repository Layer Testing

**Questions:**
```
"Repository test khác gì Service test?
Cần test gì?
- Query syntax?
- Database interaction?
- Custom query methods?

Mock database hay dùng real database?"
```

**Research Direction:**
```
Keywords:
- "@DataJpaTest annotation"
- "H2 in-memory database for tests"
- "Testcontainers for real database"
- "DBUnit for data setup"

Compare approaches:
1. Mock repository
2. H2 in-memory
3. Testcontainers (Docker)

When to use each?"
```

**Exercise:**
```
"Test AccountRepository.findByAccountNumber()

Scenarios:
- Account exists → return Optional<Account>
- Account doesn't exist → return Optional.empty()
- Multiple accounts (shouldn't happen) → ?
- Database down → ?

Setup test data như thế nào?
@BeforeEach hay trong mỗi test?"
```

### Day 6-7: Integration Testing

**Concept Check:**
```
"Integration test khác Unit test thế nào?
Test layers nào together?
Chậm hơn bao nhiêu?
Nên có bao nhiêu integration tests?"
```

**Real Banking Scenario:**
```
"Test complete transfer flow:
Controller → Service → Repository → Database

Em sẽ test như thế nào?
- @SpringBootTest?
- MockMvc?
- TestRestTemplate?
- Real HTTP calls?

Trade-offs của mỗi approach?"
```

**Challenge:**
```
Write integration test cho:
POST /api/transfers
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100.00
}

Test:
1. Success case: 201 Created, balances updated
2. Insufficient balance: 400 Bad Request
3. Account not found: 404 Not Found
4. Invalid amount: 400 Bad Request
5. Concurrent requests: Both succeed or proper locking

Em sẽ setup test data thế nào?
Em sẽ verify kết quả thế nao?
```

**Transaction Testing:**
```
Critical: "Test @Transactional behavior!

Scenario: Transfer fails at step 3
- Step 1: Debit from account A ✅
- Step 2: Save debit transaction ✅
- Step 3: Credit to account B ❌ FAILS

Expected: All rollback!

Em sẽ test điều này như thế nào?
- Throw exception at step 3
- Verify account A balance unchanged
- Verify no transaction saved

Research: Testing transaction rollback"
```

### Day 8-10: Advanced Testing Topics

#### Performance Testing

**Questions:**
```
"Banking system handle 1000 transfers/second?
Em test performance như thế nào?

Metrics to measure:
- Throughput (requests/second)?
- Latency (p50, p95, p99)?
- Resource usage (CPU, memory)?
- Database connections?

Tools: JMeter? Gatling? Custom?"
```

**Exercise:**
```
"Simple performance test:

@Test
void shouldHandleHighLoad() {
    // Create 1000 transfer requests
    // Execute concurrently
    // Measure time
    // Assert: complete within X seconds
    // Assert: no errors
    // Assert: balances correct
}

Implement approach cho test này!"
```

#### Security Testing

**Scenarios to Test:**
```
"Security vulnerabilities:

1. SQL Injection
   Test: Transfer with accountId = "1 OR 1=1"
   Expected: Rejected, not executed

2. Authentication Bypass
   Test: Call API without token
   Expected: 401 Unauthorized

3. Authorization Violation
   Test: User A transfer from User B's account
   Expected: 403 Forbidden

4. XSS in account name
   Test: Account name = "<script>alert('xss')</script>"
   Expected: Sanitized or rejected

Em sẽ test những scenarios này thế nào?"
```

#### Contract Testing

**For Microservices:**
```
"Nếu có Payment Gateway service:

AccountService depends on PaymentGateway API

Contract test ensures:
- API schema matches expectation
- Breaking changes detected
- Backward compatibility

Research: Pact, Spring Cloud Contract"
```

## Week 10: Error Handling & Resilience

### Day 11-12: Exception Strategy

**Design Questions:**
```
"Banking system exception strategy:

1. Exception hierarchy:
   - BusinessException?
   - TechnicalException?
   - Domain-specific exceptions?

2. Checked vs Unchecked?

3. When to create custom exceptions?

4. Exception messages: technical vs user-friendly?

Vẽ exception hierarchy cho Banking system!"
```

**Exercise:**
```
"Design exceptions cho transfer operation:

Custom exceptions cần:
- AccountNotFoundException
- InsufficientBalanceException
- InvalidTransferAmountException
- AccountInactiveException
- DailyLimitExceededException
- ?

Each exception có:
- Error code?
- User message?
- Technical details?
- Suggested action?

Implement base exception class!"
```

**Global Exception Handling:**
```
Research: @ControllerAdvice, @ExceptionHandler

Challenge:
"Map exceptions to HTTP responses:

AccountNotFoundException → 404
InsufficientBalanceException → 400 + specific error code
DailyLimitExceededException → 429 (Too Many Requests)
DatabaseException → 500

Response format:
{
  "timestamp": "...",
  "errorCode": "INSUFFICIENT_BALANCE",
  "message": "User-friendly message",
  "details": [...],
  "path": "/api/transfers"
}

Implement global handler!"
```

### Day 13-14: Defensive Programming

**Input Validation:**
```
"Validate EVERYTHING at boundaries!

Controller level:
- @Valid for DTO validation
- @NotNull, @NotBlank, @Positive
- Custom validators

Service level:
- Business rule validation
- State validation
- Consistency checks

Example: Transfer validation
@Valid TransferRequest:
  - fromAccountId: @NotNull
  - toAccountId: @NotNull, @Different(from = "fromAccountId")
  - amount: @NotNull, @Positive, @Digits(integer=10, fraction=2)

Em sẽ implement custom validators thế nào?"
```

**Null Safety:**
```
"Tránh NullPointerException trong Banking!

Strategies:
1. Use Optional<T> for uncertain values
2. @NonNull annotations
3. Objects.requireNonNull()
4. Validate early, fail fast

Challenge:
Review code của em, tìm MỌI chỗ có thể NPE!
Fix bằng defensive checks."
```

**Immutability:**
```
"Money object MUST be immutable!

Why?
- Thread safety
- Prevent accidental changes
- Clear intent

Example:
public final class Money {
    private final BigDecimal amount;
    private final Currency currency;
    
    // No setters!
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }
}

Em sẽ identify entities nào nên immutable?"
```

### Day 15-16: Resilience Patterns

**Circuit Breaker:**
```
"Scenario: External payment gateway down

Without circuit breaker:
- Every request waits for timeout
- Resources exhausted
- System down

With circuit breaker:
- Detect failures
- Stop calling failing service
- Fallback behavior
- Retry after cooldown

Research: Resilience4j, Spring Retry

Implement:
@CircuitBreaker(name = "paymentGateway")
public PaymentResponse processPayment(Transfer t) {
    // Call external service
}

Fallback strategy?"
```

**Retry Logic:**
```
"Transient failures cần retry:
- Network timeout
- Database deadlock
- Temporary service unavailable

NOT retry:
- Business validation failures
- Insufficient balance
- Account not found

Challenge:
Implement retry với:
- Max attempts: 3
- Backoff strategy: exponential
- Retry only for specific exceptions

Research: @Retryable annotation"
```

**Timeout Management:**
```
"Every external call MUST have timeout!

Database queries: 5 seconds
External API: 10 seconds
Batch processing: 1 minute

Why?
- Prevent resource exhaustion
- Fail fast
- Better user experience

Implement timeout for:
- RestTemplate
- Database queries (@QueryHints)
- Transaction timeout (@Transactional(timeout = 5))
"
```

### Day 17-18: Logging & Monitoring

**Structured Logging:**
```
"Banking logs cần:
1. Correlation ID (trace requests)
2. User context (who)
3. Action (what)
4. Timestamp (when)
5. Result (success/failure)
6. Duration (performance)

Good log:
{
  "correlationId": "abc-123",
  "userId": "user-456",
  "action": "TRANSFER",
  "from": "ACC001",
  "to": "ACC002",
  "amount": 100.00,
  "status": "SUCCESS",
  "duration": 45,
  "timestamp": "2024-01-15T10:30:00Z"
}

Research: Structured logging with Logback/SLF4J
MDC (Mapped Diagnostic Context)"
```

**What to Log:**
```
"DO log:
- All business operations (audit trail)
- Errors with full context
- Performance metrics
- Security events (login, failed auth)

DON'T log:
- Passwords, PINs, sensitive data
- Too much in production (debug logs)
- PII without masking

Challenge:
Review code, ensure:
- Proper log levels (DEBUG, INFO, WARN, ERROR)
- Sensitive data masked
- Contextual information included"
```

**Observability:**
```
Research topics:
- Metrics (Micrometer, Prometheus)
- Distributed tracing (Spring Cloud Sleuth)
- APM (Application Performance Monitoring)
- ELK Stack (Elasticsearch, Logstash, Kibana)

For MVP: Focus on
- Structured logging
- Basic metrics (request count, latency)
- Error rates
```

### Day 19-20: Testing Error Handling

**Test Failure Scenarios:**
```
"Comprehensive error testing:

Database failures:
@Test
void shouldHandleDatabaseDown() {
    // Mock repository to throw DataAccessException
    // Verify proper error response
    // Verify transaction rollback
    // Verify no partial updates
}

Network failures:
@Test
void shouldHandleTimeouts() {
    // Mock external service with timeout
    // Verify retry logic
    // Verify circuit breaker opens
    // Verify fallback behavior
}

Em thiết kế test cho MỌI failure scenarios!"
```

## Comprehensive Testing Checklist

### For Each Feature:
```
□ Unit Tests
  □ Happy paths
  □ Edge cases
  □ Error cases
  □ Boundary values
  □ Null handling

□ Integration Tests
  □ End-to-end flows
  □ Transaction behavior
  □ Database interactions
  □ API contracts

□ Security Tests
  □ Authentication
  □ Authorization
  □ Input validation
  □ SQL injection prevention

□ Performance Tests
  □ Load testing
  □ Concurrent access
  □ Resource usage

□ Resilience Tests
  □ Failure scenarios
  □ Retry logic
  □ Circuit breakers
  □ Timeout handling
```

## Quality Gates

**Before considering feature "done":**
```
□ Test coverage > 80% (critical paths 100%)
□ All tests pass
□ No critical/high security vulnerabilities
□ Performance within SLA
□ Error handling comprehensive
□ Logging adequate
□ Code reviewed
□ Documentation updated
```

## Daily Practice Routine

**Morning (2h):**
```
- Write tests for yesterday's code
- Identify gaps in test coverage
- Research testing techniques
```

**Afternoon (4h):**
```
- Implement new features (TDD approach)
- Fix bugs found by tests
- Refactor with test safety net
```

**Evening (1h):**
```
- Review error handling
- Check logs for issues
- Document learnings
```

## Success Metrics

After Week 9-10, em phải:
```
✅ Write tests BEFORE asking mentor
✅ Think about error cases naturally
✅ Test coverage > 80%
✅ Confident code won't break in production
✅ Handle errors gracefully
✅ Log properly for debugging
✅ Understand testing trade-offs
```

## Red Flags - Mentor Will Challenge:

```
❌ "Tests sau, code trước"
→ "Em thử TDD approach xem"

❌ "Test này không cần thiết"
→ "Nếu bug xảy ra ở production thì sao?"

❌ "Mock everything"
→ "Em có test real database integration không?"

❌ "Coverage 95%!"
→ "Nhưng test quality thế nào? Show anh tests!"

❌ "Exception này ít xảy ra"
→ "Nhưng nếu xảy ra, impact thế nào?"
```

## Final Challenge

**Week 10 deliverable:**
```
"Chaos testing: Break your own system!

1. Kill database randomly
2. Slow down external services
3. Send invalid data
4. Concurrent access 100 threads
5. Fill up memory/disk

System should:
- Handle gracefully
- Recover automatically
- Log properly
- Alert if needed

Em's system survive được không?"
```

## Remember

```
"Testing không chỉ tìm bugs,
mà còn là:
- Specification của behavior
- Safety net cho refactoring
- Documentation cho developers
- Confidence cho production deployment

Banking code = People's money
→ Testing không negotiable!

Good tests = Sleep well at night! 😴"
```