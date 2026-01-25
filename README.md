# Code review kotlin

## Getting Started:

### Prerequisites

- JDK 17 or later
- Gradle 8.5
- Spring Boot 3.5.x

To get started with this Kotlin Spring Boot project, follow these steps:

### 1. Clone the repository:

```
 git clone https://github.com/SchwarzIT/kotlin-codereview.git
```

### 2. Navigate to the project directory:

```
 cd kotlin-codereview
```

### 3. Build the project using Gradle:

```
 ./gradlew clean
```

### 4. Run the application:

```
./gradlew build
```

### 5. Access the application in your browser:

- For the REST API endpoints, navigate to `http://localhost:8080/api/`.
- For the Swagger UI documentation, navigate to `http://localhost:8080/swagger-ui/index.html`.

**Documentation:**

- The `apply` method is a POST endpoint that accepts an `CouponDto` object in the request body. It applies the currently active promotions and coupons from the request to the requested basket. If the basket is not found or the application is successful, appropriate HTTP responses are returned.

- The `create` method is a POST endpoint that accepts a `ApplicationRequestDto` object in the request body. It creates a new coupon using the provided data.

- The `getCoupons` method is a POST endpoint that accepts a `CouponRequestDto` object in the request body. It retrieves a list of coupons based on the provided criteria.
