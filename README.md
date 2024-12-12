# GalacTicket - Event Ticket Management System

## Overview
GalacTicket is an event ticket management system designed to simplify ticket sales, customer management, and event organization. This backend project uses Java, Spring Boot, and JPA, providing robust and scalable APIs for managing venues, customers, events, tickets, and reservations.

---

## Features
1. **Venue Management**
   - Add, update, delete, and retrieve venues.
   - Filter venues by location, capacity, and more.

2. **Customer Management**
   - Add, update, delete, and retrieve customer profiles.
   - Search by name, email, or phone.

3. **Event Management**
   - Manage event details such as name, location, and date.

4. **Ticket Management**
   - Create, update, and delete tickets.
   - Filter by event, customer, and status.

5. **Reservation Management**
   - Reserve tickets for specific events.
   - Assign tickets to customers.

---

## Technologies Used
- **Backend Framework**: Spring Boot
- **Database**: H2 Database (for development), supports MySQL or PostgreSQL
- **Build Tool**: Maven
- **Language**: Java
- **Testing Tool**: Postman for API testing

---

## Prerequisites
- JDK 17 or higher
- Maven
- IDE (e.g., IntelliJ IDEA, Eclipse)

---

## Setup Instructions

### Clone the Repository
```bash
git clone https://github.com/your-repo/GalacTicket.git
cd GalacTicket
```

### Build and Run the Project
1. Navigate to the project directory.
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at:
   ```
   http://localhost:8080
   ```

### H2 Database Console
The H2 Database console is available at:
```
http://localhost:8080/h2-console
```
Use the default credentials provided in `application.properties`.

---

## API Endpoints

### Venue Endpoints
| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| GET    | /venues             | Retrieve all venues      |
| GET    | /venues/{id}        | Retrieve a venue by ID   |
| POST   | /venues             | Create a new venue       |
| PUT    | /venues/{id}        | Update a venue           |
| DELETE | /venues/{id}        | Delete a venue           |

### Customer Endpoints
| Method | Endpoint             | Description               |
|--------|----------------------|---------------------------|
| GET    | /customers          | Retrieve all customers    |
| GET    | /customers/{id}     | Retrieve a customer by ID |
| POST   | /customers          | Create a new customer     |
| PUT    | /customers/{id}     | Update a customer         |
| DELETE | /customers/{id}     | Delete a customer         |

### Ticket Endpoints
| Method | Endpoint              | Description               |
|--------|-----------------------|---------------------------|
| GET    | /tickets             | Retrieve all tickets      |
| GET    | /tickets/{id}        | Retrieve a ticket by ID   |
| POST   | /tickets             | Create a new ticket       |
| DELETE | /tickets/{id}        | Delete a ticket           |

---

## Testing
- Use **Postman** or **cURL** to test the endpoints.
- Import the provided Postman collection (`postman-collection.json`) into Postman.

### Example cURL Request
Retrieve all customers:
```bash
curl -X GET http://localhost:8080/customers
```

---

## Future Enhancements
- Add authentication and role-based access control.
- Integrate email notifications for ticket confirmations.
- Enhance reporting with analytics and charts.

---

## Contributors
- **Kambale Kiregha Ezechiel**
- **INGABIRE Juliette Jolie**

---

## License
This project is licensed under the MIT License.

