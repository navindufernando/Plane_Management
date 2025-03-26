# Plane Seat Management System

A Java application for managing airplane seat reservations with ticket generation and tracking capabilities.

## Features

- Buy seats
- Cancel reservations
- Find first available seat
- View seating plan
- Print ticket information
- Search ticket details
- Automated ticket file generation

## System Requirements

- Java Runtime Environment (JRE) 8 or higher
- Java Development Kit (JDK) for compilation

## Seating Layout

The plane has 4 rows (A-D):
- Row A: 14 seats
- Row B: 12 seats
- Row C: 12 seats
- Row D: 14 seats

## Pricing Structure

- Seats 1-5: £200
- Seats 6-9: £150
- Seats 10+: £180

## Usage

1. Compile the Java files:
```bash
javac w2053138_PlaneManagement.java Person.java Ticket.java
```

## Menu Options

1. **Buy a Seat**
   - Select row (A-D)
   - Choose seat number
   - Enter personal details

2. **Cancel a Seat**
   - Input row and seat number
   - Automatically removes ticket file

3. **Find First Available Seat**
   - Shows first unoccupied seat

4. **Show Seating Plan**
   - Displays current seat status
   - O: Available
   - X: Occupied

5. **Print Tickets Information**
   - Shows all ticket details
   - Displays total sales

6. **Search Ticket**
   - Find specific ticket details by seat

## File Structure

```
├── w2053138_PlaneManagement.java
├── Person.java
├── Ticket.java
└── Generated Files
    └── [RowSeat].txt    # e.g., A1.txt
```

## Classes

### Person
- Stores passenger information
- Name, surname, email
- Data validation

### Ticket
- Manages ticket information
- Row, seat, price
- File operations
- Links to Person data

### PlaneManagement
- Main application logic
- User interface
- Seat management
- Input validation
