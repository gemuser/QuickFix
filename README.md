# QuickFix Home Services System

Java Servlet + JSP MVC starter project for connecting customers with local providers.

## Tech Stack
- Java 11
- Jakarta Servlet/JSP
- JDBC
- MySQL
- Apache Tomcat 10
- Vanilla CSS

## Setup
1. Create the database:
   ```sql
   SOURCE quickfix_schema.sql;
   ```
2. Update database settings if needed in:
   `src/main/java/com/quickfix/util/DBConnection.java`
3. Build:
   ```bash
   mvn clean package
   ```
4. Deploy `target/quickfix.war` to Tomcat 10.
5. Open:
   `http://localhost:8080/quickfix/`

## Default Admin
- Email: `admin@quickfix.local`
- Password: `admin123`

## MVC Flow
JSP pages submit to controller servlets. Controllers call service classes, services call DAO classes, and DAOs access MySQL using JDBC `PreparedStatement`.

## Tables Used
The schema includes all 13 required tables: roles, users, addresses, provider_profiles, service_categories, provider_services, provider_availability, booking_statuses, bookings, booking_status_history, ratings_feedback, complaints, and notifications.
