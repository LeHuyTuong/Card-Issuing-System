---
name: interview-preparation-junior-dev
description: Comprehensive interview preparation for junior Java/Spring Boot developer roles. Covers technical interviews, behavioral questions, system design, and portfolio presentation.
---

# Interview Preparation - Junior Java Developer

Month 4, Week 13-16: Transform từ "có skills" thành "hired"!

## Philosophy: "Interview là kỹ năng, không phải may mắn"

### Reality:
```
Có skills ≠ Pass interview
Pass interview = Skills + Preparation + Communication + Confidence

Interview prep = practice như coding!
```

## Week 13-14: Technical Interview Mastery

### Day 1-3: LeetCode Strategy

**Pattern-Based Learning:**
```
"Không solve random 500 problems!
Focus on PATTERNS (20-30 patterns cover 80% problems)

Core patterns for banking/fintech interviews:
1. Arrays & Hashing (20 problems)
2. Two Pointers (15 problems)
3. Sliding Window (10 problems)
4. Stack & Queue (15 problems)
5. LinkedList (10 problems)
6. Trees (15 problems)
7. Graphs (10 problems)
8. Dynamic Programming (10 easy/medium)

Total: ~100 problems, but DEEP understanding!"
```

**Daily Routine:**
```
Week 13:
Day 1: Arrays & Hashing (Easy: 3, Medium: 1)
Day 2: Arrays & Hashing (Easy: 2, Medium: 2)
Day 3: Two Pointers (Easy: 3, Medium: 1)
...

Week 14:
Continue patterns + Review earlier problems

Goal: 
- Recognize patterns immediately
- Solve without hints
- Explain approach clearly
- Analyze time/space complexity
```

**Problem-Solving Framework:**
```
"For EVERY problem, follow UMPIRE:

U - Understand
  "Clarify requirements, ask questions"
  
M - Match
  "Which pattern? Similar problems?"
  
P - Plan
  "Outline approach, pseudocode"
  
I - Implement
  "Code solution"
  
R - Review
  "Test cases, edge cases"
  
E - Evaluate
  "Time/space complexity, optimize?"

Practice này trong EVERY LeetCode session!"
```

**Mock Coding Interview:**
```
"Practice with timer (như real interview):

Setup:
- Choose random problem (Medium)
- Set timer: 30 minutes
- Speak out loud (explain thinking)
- Write code on whiteboard/paper first
- Then type and test

Review:
- Time management OK?
- Explained clearly?
- Handled edge cases?
- Code clean?

Do this 3x/week!"
```

### Day 4-6: Java Core Interview Questions

**Fundamental Topics:**
```
Research & prepare answers:

OOP Concepts:
- "Explain polymorphism với banking example"
- "Why use interfaces? Example from em's project"
- "Abstract class vs Interface?"
- "Composition vs Inheritance?"

Collections:
- "ArrayList vs LinkedList trade-offs"
- "HashMap internal working"
- "When to use TreeMap vs HashMap?"
- "ConcurrentHashMap for thread safety?"

Exception Handling:
- "Checked vs Unchecked exceptions"
- "Em's exception hierarchy trong banking project"
- "Finally block execution"
- "Try-with-resources"

Memory Management:
- "Stack vs Heap"
- "Garbage Collection basics"
- "Memory leak scenarios"
- "Prevent memory leaks?"

Multi-threading:
- "Thread vs Runnable"
- "Synchronized keyword"
- "Volatile, wait(), notify()"
- "Thread-safe banking operations?"

Practice: Giải thích BẰNG BANKING EXAMPLES!"
```

**Coding Challenges:**
```
"Common Java interview questions:

1. Reverse string without built-in methods
2. Find duplicates in array
3. Implement custom ArrayList
4. Thread-safe Singleton
5. Producer-Consumer problem
6. Custom exception handling
7. File I/O operations
8. Parse & validate JSON

Code 2 challenges/day
Time yourself: 15 minutes each
Explain to camera/mirror!"
```

### Day 7-9: Spring Boot Deep Dive

**Interview Topics:**
```
Master these thoroughly:

1. Spring Core:
   Q: "Explain Dependency Injection"
   A: "In my banking project, AccountService depends on
       AccountRepository. Instead of creating repository
       instance inside service (tight coupling), Spring
       injects it via constructor. Benefits: testability,
       loose coupling, easier mocking..."

2. Spring Boot Auto-configuration:
   Q: "How does @SpringBootApplication work?"
   A: Explain @Configuration, @EnableAutoConfiguration,
      @ComponentScan...

3. Spring Data JPA:
   Q: "What's N+1 problem? How did you solve it?"
   A: "In my project, loading accounts with customers
       caused N+1. I used @EntityGraph and JOIN FETCH..."

4. Transaction Management:
   Q: "Explain @Transactional propagation"
   A: "For money transfer, I use REQUIRED. If transfer
       fails, entire transaction rolls back..."

5. Spring Security:
   Q: "How did you implement authentication?"
   A: "JWT-based. User logs in → generates token →
       token validated on each request..."

Prepare CRISP 2-minute answers!"
```

**Project-Based Questions:**
```
"They WILL ask about your banking project!

Prepare for:
- "Walk me through your architecture"
- "How do you ensure data consistency?"
- "Explain your security implementation"
- "How do you handle concurrent transfers?"
- "What was the hardest bug you fixed?"
- "How would you scale this system?"
- "What would you improve?"

Practice presenting project: 5-10 minutes
Record yourself, review, improve!"
```

### Day 10-12: System Design Basics

**Junior-Level System Design:**
```
"You won't design Netflix, but expect:
- Design URL shortener
- Design parking lot system
- Design ATM system
- Design simple e-commerce

Framework:
1. Clarify requirements (5 min)
2. High-level design (10 min)
3. Detailed design (15 min)
4. Scale considerations (5 min)
5. Trade-offs discussion (5 min)
```

**Banking-Related Design:**
```
Practice: "Design a simple payment system"

Approach:
1. Functional requirements:
   - Users can add payment methods
   - Users can make payments
   - Transaction history

2. Non-functional:
   - Secure (PCI compliance)
   - Reliable (no double charging)
   - Fast (<1 second)

3. High-level:
   [Client] → [API Gateway] → [Payment Service]
                                      ↓
                               [Database]
                                      ↓
                            [Payment Gateway]

4. Deep dive:
   - How to ensure idempotency?
   - How to handle failures?
   - Database schema?
   - Security measures?

5. Scale:
   - Caching layer
   - Database replication
   - Message queue for async

Vẽ diagrams, explain trade-offs!"
```

### Day 13-14: Database & SQL

**SQL Interview Questions:**
```
"Practice writing queries:

Given schema:
accounts(id, user_id, balance, created_at)
transactions(id, from_account_id, to_account_id, amount, timestamp)

Write queries:
1. Find top 10 accounts by balance
2. Calculate total transferred by each user
3. Find accounts with no transactions
4. Detect suspicious transactions (>$10k in 1 hour)
5. Monthly transaction summary
6. Find duplicate transactions

Practice on paper (no autocomplete)!"
```

**Database Design:**
```
"Em sẽ thiết kế database như thế nào?

Questions they ask:
- "Normalize to 3NF?"
- "Indexing strategy?"
- "Handle concurrency?"
- "Transaction isolation level?"
- "Backup strategy?"
- "Scale database?"

Reference: Em's banking database
Explain choices with REASONING!"
```

## Week 15-16: Behavioral & Portfolio Presentation

### Day 15-17: STAR Method Mastery

**Behavioral Questions Framework:**
```
"Use STAR for EVERY behavioral question:

Situation - Context, background
Task - Your responsibility
Action - What YOU did (focus on "I")
Result - Outcome, metrics, learning

Example:
Q: "Tell me about a challenging bug you fixed"

S: "In my banking project, we had intermittent
    transaction failures - debit succeeded but credit
    failed randomly."

T: "As the developer, I needed to ensure atomicity
    of transfers to prevent money loss."

A: "I analyzed logs, identified the issue was race
    condition on concurrent transfers. I implemented
    pessimistic locking with @Lock annotation and
    added comprehensive integration tests."

R: "Bug eliminated. Zero transaction failures in
    2 months of testing. I documented the solution
    for team and added it to code review checklist."

Prepare 10-15 STAR stories!"
```

**Common Behavioral Questions:**
```
Prepare for:

Technical challenges:
- "Most difficult bug you debugged?"
- "Time you optimized performance?"
- "How do you learn new technologies?"
- "Describe a technical decision you made"

Teamwork:
- "Conflict with teammate?"
- "Help someone learn something?"
- "Receive critical feedback?"
- "Disagree with technical approach?"

Project management:
- "Manage tight deadline?"
- "Prioritize features?"
- "Handle changing requirements?"

Growth mindset:
- "Biggest failure and learning?"
- "Weakness and how you improve?"
- "Recent learning accomplishment?"

Write answers, practice out loud!"
```

### Day 18-20: Portfolio Presentation

**GitHub Profile Optimization:**
```
"Make em's GitHub SHINE:

Profile README:
- Brief intro
- Current focus (Java/Spring Boot)
- Notable projects
- Tech stack
- Contact info

Banking Project README:
- Clear description
- Features list
- Architecture diagram
- Tech stack with versions
- Setup instructions
- API documentation link
- Screenshots/demo video
- Live demo link (deployed!)
- Test coverage badge
- CI/CD badge

Code quality:
- Clean, consistent
- Well-commented
- Tests included
- No secrets committed
- .gitignore proper

Pin banking project to profile!"
```

**Live Demo Preparation:**
```
"Practice 5-minute demo:

Script:
1. Introduction (30s)
   'This is a banking system I built to learn
    Spring Boot and system design...'

2. Architecture (1 min)
   Show diagram
   Explain components
   Highlight technologies

3. Key features demo (2 min)
   - User registration/login
   - Account creation
   - Money transfer (show transaction)
   - Transaction history

4. Technical highlights (1 min)
   - Point out code structure
   - Show test coverage
   - Mention security features
   - Explain deployment

5. Challenges & learnings (30s)
   - Hardest part
   - What you learned
   - Future improvements

Practice until SMOOTH!"
```

**Code Walkthrough:**
```
"Be ready to explain ANY code in project:

Interviewer might ask:
'Show me the transfer method'
'Explain this design pattern'
'Why did you choose this approach?'
'How do you test this?'

Practice:
1. Navigate code quickly
2. Explain clearly
3. Discuss trade-offs
4. Show tests
5. Mention improvements

Open random file, explain it out loud!"
```

### Day 21-23: Mock Interviews

**Full Mock Interview Setup:**
```
"Simulate real interview (2 hours):

Part 1: Behavioral (30 min)
- Introduction
- 5-6 STAR questions
- Questions for interviewer

Part 2: Technical (45 min)
- 2 LeetCode problems
- Explain approach
- Code & test
- Complexity analysis

Part 3: Project Discussion (30 min)
- Walk through banking project
- Deep dive on architecture
- Code review of specific parts
- Discussion on improvements

Part 4: Questions (15 min)
- You ask thoughtful questions

Record yourself or practice with friend
Get feedback, improve!"
```

**Questions to Ask Interviewer:**
```
"ALWAYS prepare 5-10 good questions:

Team & Culture:
- 'What's the team structure?'
- 'Mentorship opportunities for juniors?'
- 'Code review process?'
- 'How do you measure success?'

Technical:
- 'Tech stack and why chosen?'
- 'Biggest technical challenge currently?'
- 'Testing practices?'
- 'Deployment frequency?'

Growth:
- 'Learning budget/time?'
- 'Career progression for juniors?'
- 'Types of projects I'd work on?'

Product:
- 'Biggest product challenge?'
- 'How does team prioritize features?'

Avoid: Salary, benefits (save for later)"
```

### Day 24-26: Company Research

**Before Each Interview:**
```
"Research thoroughly:

Company:
- Products/services
- Recent news (funding, launches)
- Company culture
- Tech blog posts
- Engineering challenges

Role:
- Job description details
- Required skills (match with yours)
- Nice-to-have skills (mention if you have)
- Responsibilities

Interviewers (if known):
- LinkedIn profile
- GitHub (if public)
- Blog posts/talks
- Shared interests

Prepare:
- Why this company?
- Why this role?
- How can you contribute?

Write 1-page notes per company!"
```

### Day 27-28: Final Polish

**Resume Optimization:**
```
"Tailor for each application:

Header:
- Name, location, phone, email, GitHub, LinkedIn
- Portfolio site (if have)

Summary (2-3 sentences):
'Junior Java Developer with hands-on experience
building production banking system using Spring Boot.
Strong foundation in OOP, data structures, and
system design. Passionate about clean code and
learning new technologies.'

Projects:
Banking System MVP (your star project!)
- 3-4 bullet points highlighting:
  * Technologies used
  * Key features implemented
  * Challenges solved
  * Metrics (test coverage, performance)

Skills:
Languages: Java 17
Frameworks: Spring Boot 3, Spring Security, Spring Data JPA
Database: PostgreSQL, Redis
Tools: Git, Docker, Maven, IntelliJ IDEA
Testing: JUnit, Mockito, Testcontainers
Cloud: [Platform you deployed to]
Others: REST APIs, CI/CD, Agile

Experience (if any):
- Internships
- Part-time work
- Open source contributions
- Relevant coursework

Education:
- Degree, University, Year
- Relevant coursework
- GPA (if >3.5)

Keep to 1 page!
Proofread 5 times!
Have someone review!"
```

**LinkedIn Profile:**
```
"Optimize for recruiters:

Headline:
'Junior Java Developer | Spring Boot | Building
Banking Systems | Open to Opportunities'

About:
- Brief intro
- What you're passionate about
- Skills highlight
- Link to GitHub
- Call to action

Experience:
- List projects as experience
- Use action verbs
- Quantify results

Skills:
- Add all relevant skills
- Get endorsements

Recommendations:
- Ask mentor, peers
- Offer to write for them

Activity:
- Share learnings
- Comment on posts
- Write occasional posts about journey

Profile photo: Professional!"
```

## Interview Day Preparation

### Day Before:
```
□ Review company notes
□ Review common questions
□ Practice elevator pitch
□ Prepare questions to ask
□ Check tech setup (for remote)
□ Get good sleep!
```

### Interview Day:
```
□ Dress appropriately
□ Arrive/login 10 minutes early
□ Have water ready
□ Notebook & pen
□ Calm, confident mindset
□ Smile & be yourself!
```

### During Interview:
```
✅ Listen carefully
✅ Ask clarifying questions
✅ Think out loud
✅ Admit if don't know (then reason through it)
✅ Be enthusiastic
✅ Take notes

❌ Don't rush
❌ Don't lie/exaggerate
❌ Don't be arrogant
❌ Don't bad-mouth previous experience
❌ Don't memorize word-for-word
```

### After Interview:
```
□ Send thank-you email (within 24h)
□ Reflect on what went well/poorly
□ Note questions you struggled with
□ Follow up if promised timeline passes
□ Keep applying elsewhere (don't wait)
```

## Red Flags to Avoid

**Technical:**
```
❌ "I just copy code from StackOverflow"
❌ "I don't know, I never tested that"
❌ "My code always works first try"
❌ "I don't need to understand, just use it"
❌ Can't explain own project code
```

**Behavioral:**
```
❌ Blaming others for failures
❌ No examples of learning from mistakes
❌ "I work best alone" (for team role)
❌ Bad-mouthing previous boss/team
❌ Only motivated by money
```

## Success Metrics

**Ready for interviews when:**
```
✅ Solve Medium LeetCode in 30 min
✅ Explain banking project confidently
✅ Answer Spring Boot questions clearly
✅ Design simple systems
✅ Write SQL queries quickly
✅ 10+ STAR stories prepared
✅ Resume polished
✅ GitHub impressive
✅ Questions for interviewer ready
✅ Mock interviews practiced
```

## Interview Pipeline Management

**Track Applications:**
```
Spreadsheet columns:
- Company name
- Position
- Date applied
- Application status
- Interview date
- Follow-up date
- Notes
- Decision

Goal: 20-30 applications
→ 5-10 phone screens
→ 2-5 onsite
→ 1-2 offers
```

**Weekly Application Goal:**
```
Week 15: Apply to 10 companies
Week 16: Apply to 10 more + interviews starting
Month 5+: Continue applying + interviewing

Don't stop applying until offer SIGNED!"
```

## Offer Evaluation

**When you get offers:**
```
"Compare holistically:

Compensation:
- Base salary
- Bonuses
- Stock options
- Benefits

Learning:
- Mentorship
- Tech stack (modern?)
- Project complexity
- Learning budget

Growth:
- Clear career path?
- Promotion timeline
- Skill development

Culture:
- Team vibe
- Work-life balance
- Remote policy
- Values alignment

Don't just take highest salary!
First job = LEARNING opportunity!"
```

## Final Advice

**Mindset:**
```
"Interview preparation = Marathon, not sprint

- Start early (don't cram)
- Practice consistently
- Learn from rejections
- Stay positive
- Keep improving

Rejection normal:
- Top candidates: 10-20% success rate
- Junior: maybe 5-10%
- Need 20-30 applications

Each interview = Practice
Each rejection = Learning
Each offer = Validation

Em's banking project + preparation = Strong candidate!

Remember:
'The best time to start was 3 months ago.
The second best time is NOW.'

You got this! 💪🚀"
```

## Post-Offer: Onboarding Prep

**Before starting:**
```
□ Review job description again
□ Research team's tech stack
□ Brush up on relevant skills
□ Prepare questions for day 1
□ Set learning goals for first 90 days
□ Celebrate! Em earned it! 🎉
```