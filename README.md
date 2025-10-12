<div align="center">
  <a href="https://github.com/antonio-backnotfront/rate-limiter-api/stargazers">
    <img src="https://img.shields.io/github/stars/antonio-backnotfront/rate-limiter-api?style=for-the-badge" alt="GitHub stars">
  </a>
  <a href="https://github.com/antonio-backnotfront/rate-limiter-api/issues">
    <img src="https://img.shields.io/github/issues/antonio-backnotfront/rate-limiter-api.svg?style=for-the-badge" alt="GitHub issues">
  </a>
  <a href="https://github.com/antonio-backnotfront/rate-limiter-api/blob/main/LICENSE.txt">
    <img src="https://img.shields.io/github/license/antonio-backnotfront/rate-limiter-api.svg?style=for-the-badge" alt="License">
  </a>
<br>
<a href="https://linkedin.com/in/anton-solianyk-906453221">
  <img src="https://img.shields.io/badge/🔗%20LinkedIn-Connect-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
</a>

  <a href="mailto:solyanicks@gmail.com">
    <img src="https://img.shields.io/badge/Email-solyanicks%40gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail">
  </a>
</div>



<h1 align="center">🛑 Rate Limiter</h1>
<p align="center">High-performance, light-weight service allowing to control the number of requests incoming to your API.</p>

<p align="center">Prevents abuse. Controls traffic. Flexible. Simple. Fast.</p>
<p align="center">No docs needed. 5 minute to get used to!</p>

---

<p align="center">

<a href="#-architecture" style="padding-right: 12px;"><strong>Architecture</strong></a>
<a href="#-how-to-use" style="padding-right: 12px;"><strong>How to use</strong></a> •
<a href="#-technologies-used" style="padding-right: 12px;"><strong>Technologies used</strong></a> •
<a href="#-license" style="padding-right: 12px;"><strong>License</strong></a>
</p>

---

## Architecture

Rate-limiter is designed to be fast and its architecture should prevent slowing down the resource, which is utilizing
this service.

All the operations directly connected with rate-limiting do not communicate with any external database. Instead, Redis
cache is used, providing the performance. Only authentication might make calls to external database
and is highly optimized.

To use rate-limiter, one must specify a policy used, and a user, which is being rate-limited at the moment. The user might be specified through any unique information expressed in String format (e.g. username, user email, ip address, unique id etc.)

Token bucket was chosen as the rate-limiting algorithm.

#### Policies
The policy contains the following information:
- window size in seconds,
- capacity (number of allowable requests per window),
- endpoint string, defining the information about endpoint, which is being controlled. Any string is allowed.

Policy allows for flexibility and maintainability of the existing restrictions (e.g. subscriptions, tariffs etc.). Beneficial for large systems with established rate-limiting regulations.

A standard user may creat up to 10 policies.

#### Algorithm: Token bucket

The service is using Token bucket algorithm that is based on consistent token replenishments. It prevents sudden request bursts and preaches the stable flow of requests. However, it is not the most accurate.

## 🚀 How to use

### Prerequisites
- Java 17 or higher
- Docker and Docker Compose
- Git

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/antonio-backnotfront/rate-limiter-api.git
   cd rate-limiter-api
   ```

2. **Start the infrastructure**
   ```bash
   cd backend
   docker-compose up -d
   ```
   This will start Redis (port 6379) and MySQL (port 3306) services.

3. **Configure environment variables**
   Set the following environment variables:
   ```bash
   export DB_URL=jdbc:mysql://localhost:3306/your_database
   export DB_USER=your_username
   export DB_PASSWORD=your_password
   export DB_SCHEMA=your_schema
   export MYSQL_ROOT_PASSWORD=your_root_password
   export MYSQL_DATABASE=your_database_name
   ```

4. **Run the application**
   ```bash
   ./gradlew bootRun
   ```
   The API will be available at `http://localhost:8080`

### API Usage

#### Authentication

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'
```

#### Policy Management

**Create a rate limiting policy:**
```bash
curl -X POST http://localhost:8080/api/policy \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "endpoint_pattern": "/api/users/**",
    "capacity": 100,
    "window_size": 60
  }'
```

**Get all policies:**
```bash
curl -X GET http://localhost:8080/api/policy \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Get specific policy:**
```bash
curl -X GET http://localhost:8080/api/policy/{policy_id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Rate Limiting Check

**Check if request is allowed:**
```bash
curl -X POST http://localhost:8080/api/rate-limiter/check \
  -H "X-POLICY-ID: your_policy_id" \
  -H "X-USER: user_identifier"
```

**Response Headers:**
- `X-RateLimit-Limit`: Maximum requests allowed per window
- `X-RateLimit-Remaining`: Remaining requests in current window
- `X-RateLimit-Reset`: Time when the window resets

**Response Codes:**
- `200 OK`: Request is allowed
- `429 Too Many Requests`: Rate limit exceeded

### Integration Example

```java
// Example of integrating rate limiter in your API gateway or middleware
@RestController
public class YourApiController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/api/users")
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        // Check rate limit before processing
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-POLICY-ID", "your_policy_id");
        headers.set("X-USER", getUserIdentifier(request));
        
        ResponseEntity<String> rateLimitResponse = restTemplate.exchange(
            "http://rate-limiter:8080/api/rate-limiter/check",
            HttpMethod.POST,
            new HttpEntity<>(headers),
            String.class
        );
        
        if (rateLimitResponse.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }
        
        // Process your actual request
        return ResponseEntity.ok("Your data here");
    }
}
```

## 🛠 Technologies used

**Backend:**
- **Java 17** - Programming language
- **Spring Boot 3.5.3** - Backend framework
- **Spring Security** - Authentication/authorization system
- **Spring Data JPA** - Data persistence layer
- **JWT (JJWT 0.12.6)** - Token-based authentication

**Databases:**
- **MySQL 8.0** - Relational database for user data and policies
- **Redis 8.0.3** - In-memory cache for rate limiting data

**Development Tools:**
- **Flyway** - Database migration management
- **Lombok** - Code generation library for reducing boilerplate
- **Gradle** - Build automation tool
- **Docker Compose** - Container orchestration for development

**Architecture Pattern:**
- **Token Bucket Algorithm** - Rate limiting implementation
- **RESTful API** - HTTP API design
- **Microservice Ready** - Can be deployed as standalone service

## 📄 License

MIT License

Copyright (c) 2025 Anton Solianyk
