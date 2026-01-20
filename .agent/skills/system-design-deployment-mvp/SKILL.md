---
name: system-design-deployment-mvp
description: Guides students through production deployment of banking MVP. Covers architecture review, infrastructure, CI/CD, monitoring, and going live.
---

# System Design & Deployment - Banking MVP

Month 3, Week 11-12: Deploy banking system to PRODUCTION và make it **battle-ready**.

## Philosophy: "If It's Not Deployed, It Doesn't Exist"

### Reality Check:
```
Code trên laptop: Demo project
Code trên production: REAL product

Deployment skills = Junior → Mid-level differentiation
```

## Week 11: Architecture Review & System Design

### Day 21-22: Architecture Assessment

**Self-Review Questions:**
```
"Review toàn bộ banking system:

1. Architecture hiện tại:
   - Monolith hay microservices?
   - Layers nào có?
   - Dependencies thế nào?
   
2. Vẽ architecture diagram:
   - Components
   - Data flow
   - External dependencies
   
3. Pain points:
   - Bottlenecks ở đâu?
   - Coupling chặt ở đâu?
   - Tech debt nào?

Prepare presentation cho mentor!"
```

**Mentor Will Ask:**
```
"Giải thích architecture choices:
- Tại sao chọn monolith? (hoặc microservices?)
- Database schema có normalized không?
- Caching strategy?
- Security implementation?
- Error handling approach?

DEFEND your decisions with reasoning!"
```

**Research Assignment:**
```
Keywords:
- "Monolith vs Microservices for MVPs"
- "12-Factor App principles"
- "Clean Architecture"
- "Hexagonal Architecture"
- "Domain-Driven Design"

Question: "Em's architecture follow best practices không?"
```

### Day 23-24: Scalability Analysis

**Capacity Planning:**
```
"Banking MVP scale requirements:

Expected load:
- Users: 1000 concurrent
- Transactions: 100/second
- Data growth: 1GB/month

Questions:
1. Current architecture handle được không?
2. Bottlenecks ở đâu?
   - Database?
   - Application server?
   - Network?
   
3. Scale strategy:
   - Vertical scaling (bigger server)?
   - Horizontal scaling (more servers)?
   - Database scaling?

Analyze và đề xuất!"
```

**Load Testing:**
```
Research: JMeter, Gatling, K6

Exercise:
"Setup load test:
- Simulate 100 users
- Each user: login + transfer + check balance
- Run for 10 minutes
- Measure: throughput, latency, error rate

Results analysis:
- System handle load?
- Response time acceptable?
- Errors occur?
- Resource utilization?

Em sẽ setup như thế nào?"
```

**Database Optimization:**
```
"Review database performance:

1. Query analysis:
   - Slow queries?
   - Missing indexes?
   - N+1 problems?

2. Enable query logging:
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

3. Analyze execution plans:
   EXPLAIN SELECT ...

4. Add indexes where needed:
   @Index on frequently queried columns

5. Connection pooling:
   HikariCP configuration

Challenge: Optimize top 5 slowest queries!"
```

### Day 25-26: Security Hardening

**Security Audit:**
```
"Complete security checklist:

Authentication & Authorization:
□ JWT properly implemented?
□ Password hashing (BCrypt)?
□ Token expiration?
□ Refresh token mechanism?
□ Role-based access control?

Input Validation:
□ All inputs validated?
□ SQL injection prevention?
□ XSS prevention?
□ CSRF protection?

Data Protection:
□ Sensitive data encrypted (at rest)?
□ HTTPS enforced (in transit)?
□ Database credentials secure?
□ API keys not in code?

Audit Logging:
□ All financial operations logged?
□ User actions tracked?
□ Failed login attempts logged?

Research: OWASP Top 10, PCI-DSS basics"
```

**Secrets Management:**
```
"NEVER commit secrets to Git!

Current problems:
- Database password in application.properties?
- API keys hardcoded?

Solutions:
1. Environment variables
2. Spring Cloud Config
3. HashiCorp Vault (advanced)
4. AWS Secrets Manager (if on AWS)

For MVP: Environment variables

Exercise:
Move all secrets to .env file
Load with Spring @Value or @ConfigurationProperties
Add .env to .gitignore
Document in README how to setup"
```

### Day 27-28: API Documentation

**OpenAPI/Swagger:**
```
Research: SpringDoc, Swagger UI

Challenge:
"Document ALL APIs:

For each endpoint:
- Description
- Request parameters
- Request body schema
- Response schema
- Error responses
- Example requests/responses
- Authentication requirements

Setup Swagger UI:
http://localhost:8080/swagger-ui.html

Review documentation như user mới!"
```

**API Versioning:**
```
"Plan for future changes:

Strategy options:
1. URL versioning: /api/v1/accounts
2. Header versioning: Accept: application/vnd.api+json;version=1
3. Query parameter: /api/accounts?version=1

For MVP: URL versioning (simplest)

Setup:
@RequestMapping("/api/v1")

Question: Khi nào release v2?"
```

## Week 12: Deployment & DevOps

### Day 29-30: Containerization

**Docker Fundamentals:**
```
Research:
- "Docker basics"
- "Dockerfile best practices"
- "Multi-stage builds"
- "Docker Compose"

Questions:
"Tại sao cần Docker?
- Consistency (works everywhere)
- Isolation (no conflicts)
- Portability (cloud-ready)
- Scalability (orchestration ready)"
```

**Dockerfile Creation:**
```
Challenge: "Write Dockerfile cho Banking app

Requirements:
- Base image: openjdk:17-slim
- Copy JAR file
- Expose port 8080
- Run application
- Health check endpoint

Advanced:
- Multi-stage build (Maven build + Runtime)
- Non-root user (security)
- Layer caching optimization

Show anh Dockerfile của em!"
```

**Docker Compose:**
```
"Local dev environment với Docker Compose:

Services:
- banking-app (Spring Boot)
- postgres (database)
- redis (caching)
- adminer (DB admin UI)

docker-compose.yml:
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/bankingdb
      
  postgres:
    image: postgres:15
    environment:
      - POSTGRES_DB=bankingdb
      - POSTGRES_PASSWORD=secret
    volumes:
      - postgres-data:/var/lib/postgresql/data

Em sẽ hoàn thiện file này!"
```

### Day 31-32: Cloud Deployment

**Platform Selection:**
```
"Options for MVP deployment:

1. Heroku (easiest, free tier)
   Pros: Simple, managed database
   Cons: Cold starts, limited free tier

2. Railway (modern alternative)
   Pros: Easy, good free tier
   Cons: Newer platform

3. AWS EC2 (flexible)
   Pros: Full control, scalable
   Cons: More complex setup

4. DigitalOcean (balanced)
   Pros: Simple, affordable
   Cons: Manual setup

For learning: Choose 1, try deploy!

Research deployment guides for chosen platform"
```

**Environment Configuration:**
```
"Setup environments:

1. Development (local)
   - H2 database
   - Debug logging
   - Hot reload

2. Staging (pre-production)
   - PostgreSQL
   - Production-like
   - Testing environment

3. Production
   - PostgreSQL
   - Minimal logging
   - Optimized config

Use Spring Profiles:
- application-dev.yml
- application-staging.yml
- application-prod.yml

Configure for each environment!"
```

**Database Migration:**
```
Research: Flyway, Liquibase

Challenge:
"Setup Flyway for database versioning:

1. Install dependency
2. Create migration scripts:
   V1__create_accounts_table.sql
   V2__create_transactions_table.sql
   V3__add_indexes.sql

3. Configure Flyway
4. Test migrations
5. Document rollback strategy

Why important?
- Version control for database
- Reproducible deployments
- Safe production updates"
```

### Day 33-34: CI/CD Pipeline

**GitHub Actions:**
```
Research: "GitHub Actions for Java"

Exercise: "Create CI/CD pipeline

Stages:
1. Build
   - Checkout code
   - Run Maven build
   - Run tests
   - Check code coverage

2. Quality Check
   - SonarQube analysis (optional)
   - Dependency security scan
   - Code style check

3. Deploy (manual trigger)
   - Build Docker image
   - Push to registry
   - Deploy to staging/production

.github/workflows/ci.yml:
name: CI/CD Pipeline
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Build with Maven
        run: mvn clean verify
      - name: Run tests
        run: mvn test
      - name: Code coverage
        run: mvn jacoco:report

Em hoàn thiện pipeline này!"
```

**Automated Testing in CI:**
```
"CI pipeline phải run MỌI tests:

- Unit tests (always)
- Integration tests (always)
- Security scans (important)
- Performance tests (optional, slow)

Pipeline fails if:
- Any test fails
- Coverage < 80%
- Security vulnerabilities found
- Build errors

Question: Em's pipeline có đủ strict không?"
```

### Day 35-36: Monitoring & Observability

**Application Monitoring:**
```
Research: Spring Boot Actuator

Setup:
1. Add dependency: spring-boot-starter-actuator
2. Enable endpoints:
   management.endpoints.web.exposure.include=health,metrics,info
3. Secure endpoints (not public!)
4. Configure health checks

Endpoints to expose:
- /actuator/health (liveness & readiness)
- /actuator/metrics (performance)
- /actuator/info (app info)

Test: curl http://localhost:8080/actuator/health"
```

**Logging Strategy:**
```
"Production logging setup:

1. Log aggregation:
   - ELK Stack (Elasticsearch, Logstash, Kibana)
   - Datadog (managed)
   - CloudWatch (AWS)
   
   For MVP: File-based + rotation

2. Log format: JSON (structured)
   {
     "timestamp": "2024-01-15T10:00:00Z",
     "level": "INFO",
     "logger": "AccountService",
     "message": "Transfer completed",
     "userId": "123",
     "amount": 100,
     "correlationId": "abc-xyz"
   }

3. Log rotation:
   - Max file size: 10MB
   - Keep: 30 days
   - Compress old logs

Configure Logback for this!"
```

**Alerting:**
```
"Setup alerts for critical issues:

Critical (immediate):
- Application down
- Database connection lost
- High error rate (>5%)
- Payment gateway down

Warning (review):
- Slow response time (>1s)
- High memory usage (>80%)
- Unusual traffic pattern

For MVP:
- Email alerts (simple)
- Uptime monitoring (UptimeRobot, Pingdom)

Advanced:
- PagerDuty
- Prometheus + Alertmanager

Setup basic monitoring cho MVP!"
```

### Day 37-38: Performance Tuning

**JVM Tuning:**
```
"Optimize JVM for production:

Heap size:
-Xms512m -Xmx1024m (initial 512MB, max 1GB)

Garbage Collection:
-XX:+UseG1GC (G1 collector for low latency)

Monitoring:
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps

Research: JVM performance tuning

Challenge:
Test with different heap sizes:
- 512MB
- 1GB
- 2GB

Measure:
- Response time
- Throughput
- Memory usage

Find optimal configuration!"
```

**Database Connection Pooling:**
```
"HikariCP configuration:

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000

Questions:
- Pool size bao nhiêu là đủ?
- Connection timeout bao lâu?

Test:
- 10 connections: enough?
- 20 connections: better?
- 50 connections: too many?

Load test with different configs!"
```

**Caching Implementation:**
```
Research: Spring Cache, Redis

Exercise:
"Add caching for:
- Account lookup (frequently accessed)
- Exchange rates (rarely change)
- User profiles (read-heavy)

NOT cache:
- Account balance (always fresh!)
- Transaction history (audit requirement)

Setup Redis:
1. Add dependency
2. Configure cache
3. Add @Cacheable annotations
4. Test cache hit/miss
5. Monitor cache effectiveness

Measure performance improvement!"
```

### Day 39-40: Go Live Preparation

**Pre-Launch Checklist:**
```
Technical:
□ All tests passing (CI green)
□ Database migrations ready
□ Environment configs set
□ Secrets configured
□ SSL certificate (HTTPS)
□ Domain configured
□ Backups automated
□ Monitoring enabled
□ Alerts configured
□ Load tested
□ Security audit passed

Documentation:
□ API documentation complete
□ Deployment guide written
□ Runbooks for incidents
□ Architecture documented
□ Database schema documented

Operational:
□ Rollback plan
□ Incident response plan
□ Support contacts
□ Maintenance window planned
```

**Deployment Strategy:**
```
"Options:

1. Big Bang (risky)
   - Deploy all at once
   - High risk if issues
   
2. Blue-Green (safer)
   - Run two environments
   - Switch traffic
   - Easy rollback

3. Canary (safest)
   - Deploy to small % users
   - Monitor
   - Gradually increase
   
For MVP: Blue-Green approach

Plan:
1. Deploy to staging
2. Final testing
3. Deploy to production (blue)
4. Smoke tests
5. Switch traffic from green to blue
6. Monitor closely
7. Keep green running (rollback ready)

Em sẽ execute plan này!"
```

**Smoke Tests:**
```
"After deployment, verify:

Critical flows:
□ User can register
□ User can login
□ User can create account
□ User can transfer money
□ User can view history
□ API returns correct responses
□ Database accessible
□ External services working

Automated smoke test script:
curl -X POST /api/auth/login
curl -X GET /api/accounts
curl -X POST /api/transfers
...

Run after EVERY deployment!"
```

**Rollback Plan:**
```
"If deployment fails:

1. Immediate rollback triggers:
   - Critical API not working
   - Data corruption
   - Security breach
   - High error rate
   
2. Rollback procedure:
   - Switch traffic back to old version
   - Revert database migrations (if needed)
   - Notify team
   - Document incident
   
3. Post-mortem:
   - What went wrong?
   - Why not caught in testing?
   - How to prevent?
   
Practice rollback before going live!"
```

## Production Operations

### Daily Operations:
```
Morning:
- Check monitoring dashboards
- Review logs for errors
- Check system health
- Verify backups completed

During day:
- Monitor alerts
- Respond to incidents
- Review performance metrics

Evening:
- Daily deployment (if needed)
- Review day's operations
- Plan tomorrow
```

### Incident Response:
```
"When something breaks:

1. Assess severity:
   - Critical (money affected): All hands!
   - High (features down): Fix in 1 hour
   - Medium (degraded): Fix in 4 hours
   - Low (minor): Fix in 1 day

2. Immediate actions:
   - Alert team
   - Check monitoring
   - Review recent changes
   - Rollback if needed

3. Communication:
   - Update status page
   - Notify users (if customer-facing)
   - Document timeline

4. Resolution:
   - Fix issue
   - Test thoroughly
   - Deploy fix
   - Verify resolution

5. Post-mortem:
   - Root cause analysis
   - Action items
   - Update runbooks"
```

## Week 11-12 Deliverable: Complete MVP

**Must Have:**
```
✅ Production-deployed application
✅ Custom domain with HTTPS
✅ Database with backups
✅ Monitoring & alerting
✅ CI/CD pipeline
✅ API documentation
✅ Comprehensive tests (>80% coverage)
✅ Error handling & logging
✅ Security hardened
✅ Performance optimized
```

**Documentation Package:**
```
1. README.md
   - Project overview
   - Setup instructions
   - API endpoints
   - Architecture diagram

2. DEPLOYMENT.md
   - Deployment steps
   - Environment config
   - Rollback procedure

3. RUNBOOK.md
   - Common operations
   - Incident response
   - Troubleshooting guide

4. ARCHITECTURE.md
   - System design
   - Technology choices
   - Trade-offs
   - Future improvements
```

## Final Review Session

**Mentor Will Ask:**
```
"Demo full system:
1. Show deployed application
2. Explain architecture
3. Demonstrate features
4. Show monitoring
5. Trigger alert (simulate issue)
6. Show logs
7. Explain deployment process
8. Show CI/CD pipeline
9. Discuss trade-offs
10. Future improvements?

Prepare 30-min presentation!"
```

## Success Metrics

After Week 11-12:
```
✅ System running in production
✅ Handle expected load
✅ No critical security issues
✅ Automated deployments working
✅ Monitoring catching issues
✅ Can deploy updates confidently
✅ Can rollback if needed
✅ Documentation complete
✅ Ready to show in interviews!
```

## Common Mistakes to Avoid

```
❌ "Works on my machine" attitude
❌ Secrets in code repository
❌ No rollback plan
❌ Deploy Friday afternoon
❌ No monitoring
❌ Manual deployment steps
❌ No documentation
❌ Skip testing in CI
❌ Ignore security
❌ No backup strategy
```

## Portfolio Showcase

**For Job Applications:**
```
"Banking System MVP" in portfolio:

Highlights:
- Production-deployed (link!)
- 80%+ test coverage
- CI/CD automated
- Monitored & secured
- Documented (GitHub)
- Handles X users, Y transactions/sec

Tech stack:
- Java 17, Spring Boot 3
- PostgreSQL, Redis
- Docker, GitHub Actions
- Deployed on [platform]

Show:
- Architecture diagram
- API documentation
- Performance metrics
- Security measures
- Code quality (SonarQube)

→ IMPRESSIVE for Junior role!
```

## Remember

```
"Deployment không phải 'final step',
mà là 'beginning of operations'!

Good deployment = Boring deployment
(No drama, no surprises, just works)

Your goal:
- Deploy confidently
- Monitor effectively
- Respond quickly
- Improve continuously

Production experience >> Tutorial experience
→ This sets em apart from other juniors!

Congratulations - em đã build & deploy
a REAL banking system! 🎉🚀"
```