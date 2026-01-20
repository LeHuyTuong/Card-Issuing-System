---
name: code-review-best-practices
description: Teaches code quality, best practices, and self-review skills for banking applications. Guides students to write maintainable, secure, production-ready code.
---

# Code Review & Best Practices for Banking Systems

Skill này dạy học viên tự review code và áp dụng best practices thay vì chỉ "code chạy được là OK".

## Philosophy: "Code for Humans, Not Just Machines"

### Core Principles

**Code được đọc nhiều hơn viết:**
- "6 tháng sau, em có hiểu code này không?"
- "Người khác có đọc hiểu được không?"
- "Junior dev có maintain được không?"

**Banking Code = Mission Critical:**
- "Bug = mất tiền người dùng"
- "Security flaw = data breach"
- "Performance issue = system down"

## Self-Review Checklist

### Before Committing Code

Học viên tự hỏi:

#### 1. Correctness
```
□ Code làm đúng requirement?
□ Edge cases đã handle?
□ Happy path AND error path?
□ Test cases pass?
```

**Questions to Ask:**
```
"Nếu user input invalid data thì sao?"
"Nếu database down thì sao?"
"Nếu account không tồn tại thì sao?"
"Nếu balance = 0 thì sao?"
```

#### 2. Security
```
□ Input validation đủ chưa?
□ SQL injection risk?
□ Sensitive data logged?
□ Authorization check?
```

**Banking Specific:**
```
"User A có thể transfer từ account của User B không?"
"Password có được hash không?"
"API có authenticated không?"
"Audit log có đủ thông tin?"
```

**Research:**
- "OWASP Top 10"
- "SQL injection prevention"
- "Spring Security best practices"

#### 3. Performance
```
□ N+1 query problem?
□ Large data load vào memory?
□ Connection leak?
□ Unnecessary database calls?
```

**Exercise:**
```
Review code:
List<Account> accounts = accountRepository.findAll();
for (Account acc : accounts) {
    Customer c = customerRepository.findById(acc.getCustomerId());
    // process...
}

Vấn đề gì? Làm sao optimize?
Research: "JPA N+1 problem", "fetch join"
```

#### 4. Maintainability
```
□ Code dễ đọc?
□ Naming clear?
□ Functions nhỏ, focused?
□ Comments đủ (nhưng không thừa)?
□ Magic numbers/strings?
```

**Code Smells:**
```
❌ Method 200 lines
❌ Variable name: a, b, x, temp
❌ Nested if-else 5 levels
❌ Copy-paste code
❌ God class (làm mọi thứ)
```

## Code Review Framework

### Level 1: Style & Formatting

**Teach Reading Code:**
```
"Code không chỉ chạy được, phải ĐẸP!

Check:
- Indentation consistent?
- Naming conventions (camelCase, UPPER_CASE)?
- Line length (<120 chars)?
- Whitespace usage?"
```

**Exercise:**
```
Review đoạn code:
public void TRansfer(String f,String t,double amt){
if(amt>0){Account a1=accountRepo.findById(f).get();Account a2=accountRepo.findById(t).get();
a1.setBalance(a1.getBalance()-amt);a2.setBalance(a2.getBalance()+amt);
accountRepo.save(a1);accountRepo.save(a2);}}

List ra tất cả style issues!
Rewrite theo clean code.
```

**Research:**
- "Java naming conventions"
- "Clean Code principles"
- "Google Java Style Guide"

### Level 2: Naming

**Good Names = Self-Documenting Code**

**Principles:**
```
✅ Descriptive: transferAmount (not amt)
✅ Consistent: getAccount() / setAccount()
✅ Avoid abbreviations: customerId (not custId)
✅ Boolean: isActive, hasPermission, canTransfer
```

**Exercise:**
```
Rename:
- Method: doStuff() → ?
- Variable: data → ?
- Class: Manager → ? (too generic)

Banking context:
- amt → amount
- acc → account
- txn → transaction (or spell out?)
```

**Questions:**
```
"Em đặt tên này vì sao?"
"3 tháng sau, em có nhớ biến này là gì không?"
"Junior dev có hiểu không?"
```

### Level 3: Method Design

**Single Responsibility Principle:**

**Bad Example:**
```java
public void handleTransfer(String from, String to, double amount) {
    // Validate
    if (amount <= 0) throw exception;
    
    // Get accounts
    Account a1 = repo.findById(from);
    Account a2 = repo.findById(to);
    
    // Check balance
    if (a1.getBalance() < amount) throw exception;
    
    // Update balances
    a1.setBalance(a1.getBalance() - amount);
    a2.setBalance(a2.getBalance() + amount);
    
    // Save
    repo.save(a1);
    repo.save(a2);
    
    // Send email
    emailService.send(...);
    
    // Log
    logger.info(...);
}
```

**Questions:**
```
"Method này làm mấy việc? (Validate, get, check, update, save, email, log)
Tách thành methods nhỏ hơn như thế nào?

Research: 'Single Responsibility Principle'
Refactor thành:
- validateTransferRequest()
- checkSufficientBalance()
- executeTransfer()
- notifyTransferComplete()
```

**Method Size:**
```
"Method nên nhỏ - ideally < 20 lines.
Nếu quá dài:
- Tách logic thành methods nhỏ
- Extract helper methods
- Consider design pattern?"
```

### Level 4: Error Handling

**Don't Swallow Exceptions!**

**Bad:**
```java
try {
    transfer(...);
} catch (Exception e) {
    // Silent fail - VERY BAD for banking!
}
```

**Questions:**
```
"Exception xảy ra thì sao?
User biết không?
System recovery như thế nào?
Log đủ thông tin để debug?"
```

**Best Practices:**
```
Research:
- "Exception handling best practices Java"
- "When to use checked vs unchecked exceptions"
- "Spring @ControllerAdvice"

For banking:
- Business exceptions (InsufficientBalanceException)
- Technical exceptions (DatabaseException)
- Return meaningful error messages
- Log với context (transaction ID, account, amount)
```

**Exercise:**
```
Review exception handling:
try {
    accountService.transfer(from, to, amount);
    return "Success";
} catch (Exception e) {
    return "Error";
}

Issues:
1. Generic Exception catch
2. Lost exception details
3. No logging
4. Unhelpful error message

Rewrite properly!
```

### Level 5: Business Logic

**Domain-Driven Design Concepts:**

**Questions:**
```
"Business logic ở đâu?
- Controller? ❌
- Service? ✅
- Entity? (sometimes)

Tại sao không ở Controller?
Research: 'Anemic Domain Model vs Rich Domain Model'"
```

**Money Handling (CRITICAL):**
```
❌ double amount;  // Precision issues!
✅ BigDecimal amount;

Questions:
"Tại sao dùng BigDecimal cho money?
double có vấn đề gì?
Research: 'Floating point precision issues'"

Exercise:
double balance = 0.1 + 0.2;
System.out.println(balance); // ???
Run và explain!
```

**Validation:**
```
"Validate ở đâu?
- DTO level (@Valid)
- Service level (business rules)
- Both?

Banking validation:
- Account number format
- Amount > 0
- Amount precision (2 decimal places)
- Balance sufficient
- Account active/not frozen
- Daily transfer limit

Em sẽ validate những gì?"
```

### Level 6: Database & Persistence

**Transaction Boundaries:**
```
Questions:
"@Transactional ở đâu?
- Controller? ❌
- Service? ✅
- Repository? (already has)

Tại sao?
Research: 'Spring transaction management best practices'"
```

**N+1 Query Problem:**
```
Review code:
List<Account> accounts = accountRepository.findAll();
for (Account account : accounts) {
    Customer customer = account.getCustomer(); // Lazy load!
    System.out.println(customer.getName());
}

Problem: 1 query + N queries = N+1
Solution:
- @EntityGraph
- JOIN FETCH
- DTO projection

Research và fix!
```

**Repository Best Practices:**
```
✅ Use derived query methods when simple
✅ @Query for complex queries
✅ Avoid findAll() in production
✅ Use Pagination (Page<T>)
✅ Projections for read-only data

Exercise:
"Design AccountRepository methods:
- Find by account number
- Find active accounts by customer
- Find accounts with balance > X
- Complex search (multiple criteria)

Implement như thế nào?"
```

## Banking-Specific Best Practices

### 1. Idempotency

**Concept:**
```
"Gọi operation nhiều lần = gọi 1 lần?

Transfer $100:
- Call 1 time: OK
- Call 2 times by accident: Transfer $200? ❌

How to prevent?
Research: 'Idempotency in REST APIs', 'Idempotency key'"
```

**Exercise:**
```
"Design idempotent transfer API:
- Request với unique transaction ID
- Check if already processed
- Return same result if duplicate

Implement strategy?"
```

### 2. Audit Trail

**Questions:**
```
"Banking cần audit trail vì:
- Compliance (regulations)
- Fraud detection
- Dispute resolution

Log cái gì?
- WHO: user ID
- WHAT: action (transfer, withdraw)
- WHEN: timestamp
- WHERE: IP address, device
- HOW MUCH: amount
- RESULT: success/fail

Research: 'Audit logging best practices'"
```

**Implementation:**
```
"Audit log ở đâu?
- Database table
- Separate audit service
- ELK stack

Data retention bao lâu?
Security: encrypt sensitive data?"
```

### 3. Immutability for Transaction History

**Concept:**
```
"Transaction history không được sửa/xóa!

Why?
- Audit requirements
- Integrity
- Dispute evidence

Design:
- No UPDATE/DELETE on transactions table
- Only INSERT
- Soft delete if needed (status = 'CANCELLED')"
```

### 4. Pessimistic Locking for Balance Updates

**Scenario:**
```
"2 users transfer từ cùng 1 account đồng thời:
User A: Transfer $100 (balance $500 → $400)
User B: Transfer $200 (balance $500 → $300)

Without locking: both see $500, both succeed!
Result: balance = $400 hoặc $300 (lost update!)

Solution:
@Lock(LockModeType.PESSIMISTIC_WRITE)
Account findByIdForUpdate(Long id);

Research: 'JPA pessimistic locking'
```

## Code Review Dialogue Patterns

### Pattern 1: Ask Understanding

**Student shows code:**
```java
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    Account from = accountRepo.findById(fromId).get();
    Account to = accountRepo.findById(toId).get();
    from.setBalance(from.getBalance().subtract(amount));
    to.setBalance(to.getBalance().add(amount));
    accountRepo.save(from);
    accountRepo.save(to);
}
```

**Mentor:**
```
"Em explain code này làm gì?
Good points em thấy?
Issues em thấy?

(Let student analyze first!)

Sau khi student trả lời:
'Còn mấy issues nữa em chưa thấy:
1. No @Transactional - what if save(to) fails?
2. No validation - amount < 0? balance sufficient?
3. No null check - findById() return Optional
4. No error handling

Em sẽ fix những cái này như thế nào?'"
```

### Pattern 2: Suggest Research Direction

**Instead of:** "Thêm @Transactional"

**Say:**
```
"Em nghĩ vấn đề gì nếu save(to) fail?
Money mất ở giữa chừng?

Research về:
- Database transactions
- ACID properties
- Spring @Transactional

Sau khi hiểu, em sẽ biết cần làm gì!"
```

### Pattern 3: Challenge with Scenarios

**Code:**
```java
public AccountDTO getAccount(Long id) {
    Account account = repo.findById(id).get();
    return mapper.toDTO(account);
}
```

**Mentor:**
```
"Code này OK với happy path.
Nhưng scenarios này thì sao:

1. Account ID không tồn tại?
2. User không có permission xem account này?
3. Account đã bị xóa (soft delete)?
4. Database connection timeout?

Em sẽ handle các cases này như thế nào?"
```

## Common Code Smells in Banking Apps

### Smell 1: Money as Double/Float
```
❌ double balance;
✅ BigDecimal balance;

Review: Tìm trong code mọi chỗ dùng double/float cho money!
```

### Smell 2: Missing Transaction Boundaries
```
❌ Controller gọi nhiều repository methods
✅ Service với @Transactional orchestrates

Review: Check transaction boundaries trong mọi business operations
```

### Smell 3: Hardcoded Values
```
❌ if (amount > 10000) // Daily limit
✅ if (amount.compareTo(dailyTransferLimit) > 0)

dailyTransferLimit load từ config/database
```

### Smell 4: Exposing Internal IDs
```
❌ API return: accountId (database PK)
✅ API return: accountNumber (business identifier)

Security: không expose internal structure
```

### Smell 5: No Logging
```
❌ Silent operations
✅ Log important events:
    logger.info("Transfer initiated: from={} to={} amount={}", 
                from, to, amount);

For debugging and audit!
```

## Testing & Code Quality

### Test Coverage Guidelines

```
Banking code cần high test coverage:
- Critical paths: 100% (transfers, authentication)
- Business logic: 90%+
- Controllers: 80%+
- Utilities: 70%+

Not just coverage %, but MEANINGFUL tests!
```

**Questions:**
```
"Em có tests chưa?
- Unit tests cho service layer?
- Integration tests cho repositories?
- API tests?

Test cases cover:
- Happy path
- Edge cases (boundary values)
- Error cases
- Concurrent scenarios?"
```

### Code Metrics

**Teach awareness of:**
```
- Cyclomatic complexity (< 10 preferred)
- Method length (< 20 lines)
- Class length (< 300 lines)
- Code duplication (DRY principle)

Tools: SonarQube, PMD, Checkstyle
```

## Progressive Code Review

### Week 1-2: Focus on Style
- Formatting
- Naming
- Basic structure

### Week 3-4: Focus on Logic
- Correctness
- Error handling
- Validation

### Week 5-6: Focus on Design
- SOLID principles
- Design patterns
- Architecture

### Week 7-8: Focus on Non-Functionals
- Performance
- Security
- Scalability

### Week 9+: Production Readiness
- Monitoring
- Logging
- Error recovery
- Documentation

## Mentor's Review Approach

### Don't Just Point Out Issues

**Bad:**
```
"Line 15: Missing null check
Line 23: Use BigDecimal
Line 30: Add @Transactional"
```

**Good:**
```
"Em thử review lại code mình:
1. Tìm những chỗ có thể null
2. Check money handling
3. Verify transaction boundaries

Em tìm thấy gì?"
```

### Ask "Why"
```
"Tại sao em design như vậy?"
"Có consider alternatives không?"
"Trade-offs là gì?"

Even if code correct, understand reasoning!
```

### Celebrate Good Practices
```
"Good! Em đã:
✅ Use BigDecimal cho money
✅ Validate input
✅ Add meaningful logs

Next level: Think about concurrent access!"
```

## Final Checklist for Production Code

```
□ Functionality
  □ Requirements met
  □ Edge cases handled
  □ Error cases handled

□ Security
  □ Input validated
  □ Authentication checked
  □ Authorization verified
  □ No sensitive data in logs

□ Performance
  □ No N+1 queries
  □ Appropriate caching
  □ Pagination for large datasets

□ Maintainability
  □ Clean, readable code
  □ Meaningful names
  □ Small, focused methods
  □ No duplication

□ Testing
  □ Unit tests
  □ Integration tests
  □ Test coverage adequate

□ Observability
  □ Logging (right level)
  □ Metrics (if applicable)
  □ Error tracking

□ Documentation
  □ API documented
  □ Complex logic explained
  □ README updated
```

## Success Metrics

Học viên thành công khi:
✅ Tự review code trước khi commit
✅ Tìm được issues trong code của mình
✅ Explain được design decisions
✅ Write tests without being told
✅ Care about code quality, not just "works"
✅ Review code của peers constructively

## Remember

"Code review không phải để chỉ ra lỗi,
mà để cùng nhau học hỏi và improve.

Em viết code tốt = team tốt = product tốt.
Banking system = trust = quality code.

Take pride in your craftsmanship!"