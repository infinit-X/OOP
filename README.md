# 5COSC019W — OOP & Enterprise Application Development (Spring Boot) Revision Repo

A mobile-friendly study repository containing all completed practice projects, a full-syllabus worked reference project, and a quick exam/viva cheatsheet.

## 📖 Start Here (Best for Reading on Your Phone!)
- 👉 **[EXAM_CHEATSHEET.md](./EXAM_CHEATSHEET.md)** — All 14 Spring Boot annotations, 8 exam patterns (safe math, ceiling division, multi-level sorting, greedy selection, validation, `@RestControllerAdvice`), top exam traps, and 10 Viva Q&As.

---

## 📂 Projects Included

| Folder | Description | Key Concepts Covered |
| :--- | :--- | :--- |
| **[01_Practice_Product_API](./01_Practice_Product_API)** | Built from scratch during revision | Encapsulation, Inheritance, Abstract `Product`, Custom Exceptions, `ProductRepository`, Constructor Injection, Budget Planner, Swagger UI |
| **[02_SIMPLE_Parking_API](./02_SIMPLE_Parking_API)** | Completed *Simple Parking Access & Fee Quotation* practical | Ceiling division `(m + 59L) / 60L`, Tiered fees, Java `record` DTOs (`Quote`, `ApiError`, `NewHourlyPass`), `400`/`404`/`201` HTTP mappings |
| **[03_MODERATE_Drone_Dispatch_API](./03_MODERATE_Drone_Dispatch_API)** | Completed *Moderate Emergency Dispatch Energy Planner* mock practical | Polymorphic `DispatchMission`, `Math.toIntExact`, 3-level `Comparator` sort (`priority DESC, energy ASC, id ASC`), Greedy capacity allocation, `urgentOnly` filter, `Map.of` JSON |
| **[04_BONUS_Cloud_Compute_Scheduler_API](./04_BONUS_Cloud_Compute_Scheduler_API)** | Full-syllabus worked reference project | Combines **everything** in Weeks 1–7, including `interface` (`Billable`) + `abstract class` (`ComputeJob`), **Interface Repository (`JobRepository`) + `Optional<T>`**, ceiling & tiered math, 4-level sort, greedy scheduling, `DELETE` (`204 No Content`), and `ArithmeticException` handling |

---

## ▶️ Running Any Project Locally
1. Open the desired project folder in **IntelliJ IDEA** (JDK 25 for projects 01, 03, 04; JDK 17+ for project 02).
2. Run the main `@SpringBootApplication` class.
3. Test live endpoints in your browser via Swagger UI:  
   **`http://localhost:8080/swagger-ui.html`**
