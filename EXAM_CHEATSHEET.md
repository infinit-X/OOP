# ⚡ 5COSC019W OOP & Spring Boot — Quick Bus Cheatsheet & Viva Guide

> **Exam Timing:** 100 minutes total (**90 min coding + 10 min ZIP submission**).  
> **First 5 seconds in IntelliJ:** Click the `src` folder $\rightarrow$ press **`Ctrl + Alt + L`** to auto-format squished starter files cleanly!

---

## 1. The 5-Package Layered Architecture

```
HTTP Request ──► @RestController (Speaks HTTP only: URLs, status codes, JSON)
                       │
                       ▼  (Constructor Injection)
                 @Service        (Business rules, validation, sorting, throws Exceptions)
                       │
                       ▼  (Constructor Injection)
                 @Repository     (Data storage only: Map/List, find/exists/save)
                       │
                       ▼
                 model / dto     (Abstract parent, concrete subclasses, request/response classes)

Safety Net:      @RestControllerAdvice (Catches Exceptions globally -> 400 / 404 JSON)
```

---

## 2. All 14 Spring Boot Annotations in Your Syllabus

| Category | Annotation | What It Does |
| :--- | :--- | :--- |
| **Class Labels** | `@SpringBootApplication` | Placed on Main class; boots Tomcat on port 8080 & scans packages. |
| | `@RestController` | Marks class as REST API controller returning JSON. |
| | `@Service` | Marks business logic class (Spring creates 1 bean). |
| | `@Repository` | Marks storage class (Spring creates 1 bean). |
| | `@RestControllerAdvice` / `@ControllerAdvice` | Global error catcher watching all controllers. |
| **Routes** | `@RequestMapping("/api/items")` | Base URL prefix on top of the Controller class. |
| | `@GetMapping("/{id}/cost")` | Handles HTTP `GET`. |
| | `@PostMapping("/air")` | Handles HTTP `POST`. |
| | `@DeleteMapping("/{id}")` | Handles HTTP `DELETE`. |
| **Inputs** | `@PathVariable("id") long id` | Reads `{id}` from the URL path (`/items/101`). |
| | `@RequestParam("limit") int limit` | Reads `?limit=60` after `?` in the URL. |
| | `@RequestParam(name="urgentOnly", defaultValue="false")` | Optional query param with fallback default. |
| | `@RequestBody NewItem req` | Reads JSON body from `POST` (**never import Swagger's `@RequestBody`!**). |
| **Status / Errors** | `@ResponseStatus(HttpStatus.CREATED)` | Returns `201 Created` on `POST` (`NO_CONTENT` = `204` on `DELETE`). |
| | `@ExceptionHandler(MyException.class)` | Catches specific exception in `@RestControllerAdvice`. |

---

## 3. Copy-Paste Mental Templates (Memorize These 6 Patterns!)

### Pattern A: Safe Integer Arithmetic (`long` + `Math.toIntExact`)
Never do multiplication in `int` (risks silent negative overflow above 2.1 billion).
```java
long total = 3L * getDistanceKm() + 8L * getLoadKg() + 2L * windPenalty;
return Math.toIntExact(total);
```

### Pattern B: Ceiling Division (Rounding Up Partial Hours/Blocks)
- **Wrong:** `(minutes / 60) + 1` $\rightarrow$ FAILS at exact multiples like `60`, `120`!
- **Always Right:** `(minutes + (blockSize - 1)) / blockSize`
```java
// 1-60 mins = 1 hr, 61-120 mins = 2 hrs
long hours = (stayMinutes + 59L) / 60L;

// Tiered rule: flat up to 480 mins, extra per commenced hour above 480
long extraHours = ((stayMinutes - 480L) + 59L) / 60L;
```

### Pattern C: Multi-Level Sorting (`list.sort(this::compareItems)`)
- **Ascending (Small $\rightarrow$ Big):** `(first, second)`
- **Descending (Big $\rightarrow$ Small):** `(second, first)`
```java
eligible.sort(this::compareMissions);

private int compareMissions(DispatchMission first, DispatchMission second) {
    int result = Integer.compare(second.priority(), first.priority()); // Priority DESC
    if (result != 0) return result;

    result = Integer.compare(first.energyUnits(), second.energyUnits()); // Energy ASC
    if (result != 0) return result;

    return Long.compare(first.getId(), second.getId()); // ID ASC
}
```

### Pattern D: Greedy Capacity Selection (Keep Checking After Skipping!)
```java
List<PlanLine> selected = new ArrayList<>();
List<Long> skipped = new ArrayList<>();
int used = 0;

for (DispatchMission m : eligible) {
    int cost = m.energyUnits();
    if (cost <= capacity - used) {
        selected.add(new PlanLine(
            m.getId(),
            m.getClass().getSimpleName(), // Polymorphic class name (NO instanceof!)
            m.priority(),
            cost
        ));
        used += cost;
    } else {
        skipped.add(m.getId()); // Skip, do NOT break! Later smaller items may fit!
    }
}
int remaining = capacity - used;
```

### Pattern E: Validation & Custom Exceptions (400 vs 404)
- **"Positive"** $\rightarrow$ reject **`<= 0`** (0 is NOT positive!)
- **"Non-negative"** $\rightarrow$ reject **`< 0`** (0 IS allowed!)
- **Blank String** $\rightarrow$ `if (str == null || str.isBlank())` + `.trim()` before saving!
- **Lookup Missing ID** $\rightarrow$ `if (item == null) throw new ItemNotFoundException(id);` (or `repo.findById(id).orElseThrow(...)`)
- **Duplicate ID** $\rightarrow$ `if (repo.exists(id)) throw new IllegalArgumentException("...");`
- **Overflow Pre-Check** $\rightarrow$ Call `item.energyUnits();` **before** `repo.save(item);`!

### Pattern F: Global Exception Handler (`@RestControllerAdvice`)
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ItemNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }
}
```

### Pattern G: 1-Field JSON Response (`Map.of`)
If an endpoint must return `{"energyUnits": 39}`:
```java
@GetMapping("/{id}/energy")
public Map<String, Integer> energy(@PathVariable("id") long id) {
    return Map.of("energyUnits", service.energy(id));
}
```

### Pattern H: Java `record` vs Ordinary Class
- **Normal class:** `body.getId()`, `body.getRegistration()`
- **Java `record`:** `body.id()`, `body.registration()` (**NO `get` prefix!**)

---

## 4. Top 8 Exam Traps That Cost Students Marks

1. **Wrong `@RequestBody` import:** IntelliJ sometimes auto-imports `io.swagger.v3.oas.annotations.parameters.RequestBody`. Always ensure it comes from `org.springframework.web.bind.annotation.*`!
2. **Case-sensitive `@RequestParam`:** `@RequestParam("Capacity")` fails if URL is `?capacity=60`. Match exact capitalization!
3. **`=` vs `-` typo:** Double-check `int remaining = capacity - used;`.
4. **`< 0` instead of `<= 0`:** "Positive" strictly rejects `0` too (`<= 0`).
5. **Forgetting `if (obj == null) throw NotFoundException`:** Causes HTTP 500 `NullPointerException` instead of HTTP 404.
6. **Forgetting to add the new subclass seed in Repository:** Check Section 3 of the exam paper!
7. **Forgetting to delete `throw new UnsupportedOperationException(...)`:** Leftover TODO lines crash endpoints.
8. **Not stopping old servers on Port 8080:** Always click the Red Stop button (`■`) before running a new Spring Boot project.

---

## 5. Top 10 Viva Defense Questions & Model Answers

1. **Why must `super(...)` be the first line in a subclass constructor?**
   > **Object Initialization Order:** The parent portion of the object must be initialized in memory before the child can initialize its own fields. Putting code before `super(...)` causes a **compile-time error**.

2. **Why does Jackson need getters and a no-arg constructor?**
   > For **serialization** (Java $\rightarrow$ JSON), Jackson reads **public getters**, not private fields. For **deserialization** (JSON $\rightarrow$ Java), Jackson calls the **no-arg constructor** first to create an empty object, then calls the **setters** to populate it.

3. **Abstract Class vs. Interface?**
   > An **abstract class (`extends`)** defines *what an object IS*—it can hold private fields, constructors, and mix concrete + abstract methods (single inheritance). An **interface (`implements`)** defines *what an object CAN DO*—it has no instance fields or constructors, only method contracts (a class can implement multiple interfaces).

4. **How does Polymorphism work in your Service without `instanceof`?**
   > The parent declares `abstract` methods (`energyUnits()`, `priority()`). Subclasses `@Override` them. The Service stores all subclasses in a `List<Parent>` and invokes `m.energyUnits()` via **dynamic method dispatch**—the JVM picks the right subclass method at runtime based on the actual object type.

5. **Method Overriding vs. Method Overloading?**
   > **Overriding (`@Override`)** is across parent/child classes with the **exact same name and parameters** (runtime). **Overloading** is inside the **same class** with the same name but **different parameter lists** (compile-time).

6. **Why do custom exceptions extend `RuntimeException`?**
   > Two reasons: (1) Java's `throw`/`catch` requires inheriting from `Throwable` (giving us `getMessage()` and stack traces), and (2) `RuntimeException` is **unchecked**, so we don't have to clutter every Service and Controller method signature with `throws`.

7. **Why use `@RestControllerAdvice` instead of `try-catch` in every Controller?**
   > **Separation of Concerns & DRY:** The Service decides *when* a business rule fails (throws domain exception without knowing about HTTP), the Controller handles happy-path HTTP, and `@RestControllerAdvice` centralizes all error-to-HTTP-status (`400`/`404`) translation in one place.

8. **Why Constructor Injection instead of `@Autowired` field injection (or `new Repository()`)?**
   > (1) **Immutability (`private final`)**—cannot be reassigned or `null`; (2) **Explicit dependencies** in the constructor signature; (3) **Loose coupling & easy testing** without booting Spring.

9. **Why write `List<Product> list = new ArrayList<>();` instead of `ArrayList<Product>` on the left?**
   > Programming to the **`List` interface** decouples our code from the concrete implementation, so we could swap `ArrayList` for `LinkedList` without breaking any other line.

10. **Why `long` + `Math.toIntExact(...)` for calculations?**
    > Intermediate `int` multiplication can silently overflow past 2.1 billion into a negative number. Using `long` prevents intermediate overflow, and `Math.toIntExact(...)` safely converts back to `int` or throws `ArithmeticException` if it exceeds `int` range.
