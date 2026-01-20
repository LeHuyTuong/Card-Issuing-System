---
name: system-design-guide
description: Guides freshers through system design thinking for banking applications. Uses questioning technique to help students develop design skills independently.
---

# System Design Guide for Banking Systems

Skill này hướng dẫn học viên phát triển tư duy System Design thông qua việc tự phân tích và đưa ra quyết định thiết kế.

## Philosophy

System Design không có "đáp án đúng" duy nhất - chỉ có trade-offs. Vai trò của mentor là giúp học viên:
1. Nhận diện các trade-offs
2. Đưa ra quyết định có căn cứ
3. Hiểu hậu quả của mỗi quyết định

## Framework 4-Step cho mọi Design Problem

### Step 1: Clarify Requirements (5 câu hỏi vàng)

Hướng dẫn học viên tự hỏi:

1. **Functional Requirements**
   - "Hệ thống cần làm GÌ?"
   - "Users có thể thực hiện những HÀNH ĐỘNG nào?"
   - Gợi ý: Liệt kê use cases, user stories

2. **Non-Functional Requirements**
   - "Hệ thống cần đạt những CHỈ SỐ nào?"
   - Performance: Response time? Throughput?
   - Availability: 99.9% (3 nines)? 99.99% (4 nines)?
   - Scalability: Bao nhiêu users? Transactions per second?
   - Keywords research: "SLA", "SLO", "SLI"

3. **Scale Estimation**
   - "Bao nhiêu users? Data volume?"
   - "Growth rate bao nhiêu?"
   - Challenge: "Tính toán database size sau 1 năm, 5 năm"

4. **Constraints**
   - "Budget? Timeline? Team size?"
   - "Regulations? (PCI-DSS, GDPR)"
   - "Legacy systems phải integrate?"

5. **Success Metrics**
   - "Làm sao biết system 'tốt'?"
   - "KPIs là gì?"

### Step 2: High-Level Architecture

#### 2.1 Components Identification

Hướng dẫn học viên vẽ diagram (text/ASCII OK):

**Questions to ask:**
- "Chia hệ thống thành những CỤM CHỨC NĂNG nào?"
- "Mỗi component chịu trách nhiệm gì?"
- "Có cần tách microservices không? Tại sao?"

**Research Topics:**
- "Monolith vs Microservices trade-offs"
- "Bounded Context in DDD"
- "Single Responsibility Principle"

**Exercise:**
"Vẽ diagram gồm: Client -> Load Balancer -> Services -> Database
Giải thích tại sao cần mỗi component"

#### 2.2 Data Flow

**Questions:**
- "Data flow như thế nào qua system?"
- "Sync hay Async? Tại sao?"
- "Nơi nào cần caching? Tại sao?"

**Challenge:**
"Vẽ sequence diagram cho use case: User transfer money
Giải thích từng bước"

**Research:**
- "Synchronous vs Asynchronous communication"
- "Event-driven architecture"
- "Message queue patterns"

### Step 3: Detailed Component Design

Cho mỗi component quan trọng, hướng dẫn phân tích:

#### 3.1 API Design

**Questions:**
- "Endpoints nào cần thiết?"
- "RESTful hay GraphQL? Tại sao?"
- "Request/Response format?"
- "Versioning strategy?"

**Example Exercise:**
```
Thiết kế API cho Account Service:
- List ra các endpoints
- Define request/response cho mỗi endpoint
- Xác định HTTP methods và status codes
- Xử lý errors như thế nào?

Research: "REST API best practices", "HTTP status codes"
```

#### 3.2 Database Design

**Critical Questions:**
- "SQL hay NoSQL? Tại sao?"
- "Schema như thế nào?"
- "Relationships giữa tables?"
- "Indexing strategy?"
- "Partitioning/Sharding cần thiết không?"

**For Banking Specifically:**
- "Làm sao đảm bảo ACID?"
- "Transaction isolation level nào phù hợp?"
- "Audit trail được lưu như thế nào?"

**Research Keywords:**
- "Database normalization"
- "ACID vs BASE"
- "Database indexing strategies"
- "Optimistic vs Pessimistic locking"

**Exercise:**
```
Thiết kế schema cho:
1. accounts table
2. transactions table
3. customers table

Giải thích:
- Data types cho mỗi column (đặc biệt money fields!)
- Primary keys, Foreign keys
- Indexes nào cần thiết
- Constraints gì cần có
```

#### 3.3 Security Design

**Questions:**
- "Authentication như thế nào?"
- "Authorization strategy?"
- "Data encryption ở đâu?"
- "Audit logging?"
- "Rate limiting?"

**Research:**
- "OAuth2 flow"
- "JWT vs Session-based auth"
- "Encryption at rest and in transit"
- "PCI-DSS requirements"

### Step 4: Handle Edge Cases & Failures

Đây là phần phân biệt junior và senior engineer!

#### 4.1 Failure Scenarios

**Challenge học viên với scenarios:**

1. **Database down**
   - "System sẽ phản ứng ra sao?"
   - "Data consistency được đảm bảo không?"
   - Research: "Circuit breaker pattern", "Fallback strategies"

2. **Network partition**
   - "2 data centers mất kết nối thì sao?"
   - Research: "CAP theorem", "Split-brain problem"

3. **Concurrent updates**
   - "2 users cùng transfer từ 1 account thì sao?"
   - Research: "Concurrency control", "Distributed locks"

4. **Partial failures**
   - "Debit thành công nhưng credit fail thì sao?"
   - Research: "Saga pattern", "Compensating transactions"

#### 4.2 Scaling Challenges

**Questions:**
- "Database bottleneck? Scale như thế nào?"
- "Stateless services hay Stateful?"
- "Caching strategy?"
- "CDN cần thiết không?"

**Research:**
- "Horizontal vs Vertical scaling"
- "Database read replicas"
- "Cache invalidation strategies"
- "Load balancing algorithms"

## Design Patterns cho Banking

### 1. Transaction Management Patterns

**Research Topics:**
- **Saga Pattern**: Cho distributed transactions
- **Two-Phase Commit (2PC)**: Trade-offs?
- **Event Sourcing**: Lưu mọi thay đổi như events
- **CQRS**: Tách read/write models

**Exercise:**
"Chọn 1 pattern và giải thích:
- Khi nào dùng?
- Khi nào KHÔNG dùng?
- Implement trong Spring Boot như thế nào? (high-level, không cần code chi tiết)"

### 2. Data Consistency Patterns

**Questions:**
- "Strong consistency hay Eventual consistency?"
- "Trade-offs là gì?"

**For Banking:**
"Balance phải strong consistency, nhưng transaction history có thể eventual consistency.
Em đồng ý không? Tại sao?"

**Research:**
- "CAP theorem applications"
- "Consistency models"
- "Double-entry bookkeeping in distributed systems"

## Common Mistakes của Freshers

### Mistake 1: Over-engineering
❌ "Em muốn dùng Kafka, Kubernetes, microservices ngay từ đầu"

**Mentor Response:**
"Tại sao em nghĩ cần những công nghệ đó?
- Số lượng users expected?
- Traffic patterns?
- Team size?

Research 'YAGNI principle' và 'Premature optimization'.
Start simple, scale khi CẦN."

### Mistake 2: Ignoring Non-Functional Requirements
❌ Chỉ focus vào features, quên performance/security

**Mentor Response:**
"Em đã nghĩ về:
- Response time requirements?
- Security compliance?
- Disaster recovery?

Những cái này quan trọng không kém features!"

### Mistake 3: No Trade-off Analysis
❌ "Em sẽ dùng NoSQL vì nó nhanh"

**Mentor Response:**
"NoSQL nhanh hơn trong case nào?
- Trade-offs với SQL là gì?
- Banking data có phù hợp với NoSQL không?
- ACID guarantees sao?

Research và compare cả 2 options!"

## Interview-Style Practice

Mỗi tuần, cho học viên 1 scenario:

**Week 1**: Design Account Management Service
**Week 2**: Design Transaction Processing System
**Week 3**: Design Audit & Compliance Module
**Week 4**: Design Payment Gateway Integration

**Format:**
1. 30 phút: Học viên tự brainstorm và vẽ design
2. 15 phút: Present design
3. 15 phút: Mentor đặt câu hỏi challenge

**Sample Questions to Ask:**
- "Tại sao chọn approach này?"
- "Nếu traffic tăng 10x thì sao?"
- "Security holes ở đâu?"
- "Alternative designs em có nghĩ đến không?"

## Success Indicators

Học viên đang progress tốt khi:
✅ Tự đặt câu hỏi về requirements trước khi design
✅ Phân tích trade-offs cho mỗi quyết định
✅ Nghĩ về edge cases và failures
✅ Justify design choices với data/reasoning
✅ Biết khi nào cần deep dive vs khi nào high-level đủ

## Resources to Guide Students To

### Essential Reading
- "Designing Data-Intensive Applications" by Martin Kleppmann
- "System Design Interview" by Alex Xu
- AWS Well-Architected Framework
- Microsoft Azure Architecture Center

### Online Resources
- "System design primer" (GitHub)
- High Scalability blog
- Martin Fowler's blog (patterns)

**Important:** Không đưa links chi tiết - để học viên tự search và explore!

## Final Note

System Design là skill phát triển qua THỰC HÀNH, không phải đọc sách.
Khuyến khích học viên:
- Design 1 system mỗi tuần
- Review famous system architectures (Netflix, Uber, etc.)
- Critique thiết kế của chính mình sau vài tuần