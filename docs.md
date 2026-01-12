#  My Core Banking Learning Notes (Weeks 1-4)
## 1. Architecture: Modular Monolith

### A. Tại sao KHÔNG dùng N-Layer (Layered Architecture)?
**Vấn đề của N-Layer:**
*   Tất cả Entity gộp chung 1 thư mục → Khi dự án lớn (30+ entities), không biết Entity nào thuộc về nghiệp vụ nào.
*   Service gộp chung → Khi được giao task "Làm feature Ship hàng", không biết cần sửa Service nào (OrderService? ShippingService? DeliveryService?).
*   Merge conflict nhiều → 3 người cùng sửa `OrderService.java`, conflict liên tục.
*   **Kết luận:** N-Layer phù hợp với dự án nhỏ (5-10 entities), không phù hợp môi trường team thực tế.

### B. Modular Monolith là gì?
Thay vì chia theo Layer (Controller/Service/Repo), tôi chia theo **Module/Domain** (Customer, Card, Ledger, Transaction).

**Cấu trúc thực tế:**
```
bank.cardissuing/
├── customer/          ← Module Quản lý khách hàng
│   ├── api/          (Controller - Giao tiếp bên ngoài)
│   ├── application/  (Service - Xử lý nghiệp vụ chính)
│   ├── domain/       (Entity, Enums - Trái tim của logic)
│   ├── exception/    (Custom Exception)
│   └── infrastructure/ (Repository - Làm việc với DB)
├── card/             ← Module Quản lý thẻ
├── ledger/           ← Module Sổ cái
└── transaction/      ← Module Xử lý ủy quyền giao dịch
```

### C. Lợi ích khi đi làm thực tế
*   **Phân công task rõ ràng:** Anh lead bảo "Em làm Authorization" → Vào `transaction/` là biết ngay phải sửa gì.
*   **Giảm conflict:** Mỗi người làm 1 module riêng → merge code nhanh, ít bug.
*   **Dễ onboard Junior:** Dev mới chỉ cần đọc 1 module (VD: `card/`), không phải hiểu cả codebase.
*   **Bounded Context:** Logic của Card nằm trọn trong `card/`, không lẫn lộn với Ledger hay Customer.

### D. Nguyên tắc giao tiếp giữa các Module (Module Boundary)
*   ✅ **Đúng:** `AuthorizationService` gọi `LedgerService.debit()` (qua Service - public interface).
*   ❌ **Sai:** `AuthorizationService` gọi `LedgerEntryRepository.save()` (vi phạm module boundary).
*   **Quy tắc vàng:** Module A chỉ được gọi Service của Module B, KHÔNG được gọi trực tiếp Repository của B.

### E. So sánh tổng kết
| Tiêu chí | N-Layer | Modular Monolith |
|----------|---------|------------------|
| Tìm code khi được giao task | Khó, phải hỏi anh chị | Dễ, vào module tương ứng |
| Merge conflict | Nhiều (cùng sửa 1 Service) | Ít (mỗi người 1 module) |
| Phù hợp team | 1-2 người | 5-10+ người |
| Dễ test | Phải mock nhiều | Test module độc lập |

## 2. JPA & Database Best Practices
*   **Enum lưu String:** Luôn dùng `@Enumerated(EnumType.STRING)` để lưu "ACTIVE" thay vì số 0, 1. Giúp DB dễ đọc và tránh lỗi khi đổi thứ tự Enum.
*   **Relationship:** Dùng `@JoinColumn` ở bảng con để quản lý khóa ngoại. Hạn chế mapping 2 chiều (Bidirectional) để tránh vòng lặp vô tận (Infinite Recursion) khi convert sang JSON.
*   **Constructor:** JPA bắt buộc cần một Constructor rỗng. Dùng `@NoArgsConstructor(access = AccessLevel.PROTECTED)` để vừa thỏa mãn JPA, vừa ngăn code bên ngoài tạo object rỗng bừa bãi.

## 3. Banking Concepts Implemented
### A. Card Lifecycle (Vòng đời thẻ)
*   Thẻ không chỉ có "Bật/Tắt". Nó là một State Machine:
    *   `CREATED`: Mới tạo, chưa dùng được.
    *   `ACTIVE`: Đã kích hoạt.
    *   `BLOCKED`: Bị khóa (mất thẻ, fraud).
*   **Bài học:** Luôn kiểm tra trạng thái cũ trước khi chuyển sang trạng thái mới (VD: Không thể Active một thẻ đang bị Blocked).

### B. Ledger (Sổ cái) - Bất biến (Immutability)
*   **Quy tắc vàng:** Ledger chỉ được **THÊM**, không được **SỬA/XÓA**.
*   **Implementation:**
    *   Xóa Setter của `amount`.
    *   Dùng `@PreUpdate` và `@PreRemove` để ném lỗi (`throw Exception`) nếu ai đó cố tình can thiệp vào DB.
*   **Tại sao?** Để đảm bảo tính minh bạch (Audit Trail). Nếu sai, tạo giao dịch bù (Reversal), chứ không sửa cái cũ.

### C. Money & Precision
*   **Không dùng `double`:** Vì lỗi làm tròn số thực.
*   **Dùng `BigDecimal`:** Để đảm bảo chính xác tuyệt đối từng xu.

## 4. Testing
*   **Unit Test:** Test logic nội tại của Entity (VD: Gọi hàm `active()` thì status phải đổi, gọi sai phải lỗi).
*   **Service Test:** Dùng Mockito để giả lập Database, test logic luồng đi (Flow) mà không cần kết nối DB thật.

Cấu trúc UnitTest : AAA Pattern 
*   **A - Arrange:** Chuẩn bị dữ liệu đầu vào.
when khoong gọi data base thật , giả lập data trả về
*   **A - Act:** Gọi hàm cần test.
gọi method cần test trong service 
*   **A - Assert:** Kiểm tra kết quả trả về có đúng không.
assert kiểm tra kết quả trả về đúng như mong đợi
assertEquals : kiểm tra giá trị trả về có đúng không 
assertThrows : kiểm tra có ném exception không

verify : kiểm tra  hành vi, ví dụ có gọi method save không

## 5. Transaction & Concurrency (Week 5)

**A. Race Condition (Tranh chấp dữ liệu)**
- Vấn đề: 2 giao dịch đọc balance cùng lúc -> cả 2 thấy đủ tiền -> trừ 2 lần -> tài khoản âm.
- Giải pháp: Dùng Pessimistic Lock `@Lock(LockModeType.PESSIMISTIC_WRITE)` để khóa row trong DB.

**B. Atomicity (Tính nguyên tử)**
- Vấn đề: Nếu `debit()` thành công nhưng `getBalance()` lỗi -> tiền đã trừ nhưng không trả kết quả.
- Giải pháp: Dùng `@Transactional`. Nếu bất kỳ bước nào fail -> toàn bộ rollback.

**C. Optional Handling**
- Lỗi phổ biến: Gọi method trả `Optional<T>` nhưng không xử lý `.orElseThrow()`.
- Chuẩn: Luôn handle Optional, không bao giờ `.get()` trực tiếp.

**D. Service Layer Design**
- Nguyên tắc: Service A không nên gọi Repository của B. Thay vào đó, gọi Service B.
- Ví dụ: AuthorizationService gọi `LedgerService.debit()`, không gọi `LedgerEntryRepository.save()`.