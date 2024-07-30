# GitHub README: Flight Booking System Architecture

**Overview**</br>
This Spring Boot application provides a flight booking platform with user authentication, flight management, ticket booking, and real-time notifications. The system leverages PostgreSQL for data storage, Spring Security and JWT for authentication, and Firebase Cloud Messaging (FCM) for push notifications.

# Architecture
## Components
- **Spring Boot Backend:** Handles REST APIs for user management, flight operations, ticket booking, and notification management.
- **PostgreSQL Database:** Stores user information, flight details, ticket data, and notification metadata.
- **Firebase Cloud Messaging:** Delivers push notifications to subscribed devices.
- **SMTP Server:** Sends email notifications to users.

## Data Model
- **User:** Stores user credentials, personal information, and user roles (admin, user).
- **Flight:** Contains flight details including number, source, destination, departure/arrival times, status, gate number, seat count, and price.
- **Ticket:** Stores booked tickets with a composite primary key (flight ID, user ID, seat number).
- **Notification:** Stores user device tokens and userId for FCM.

## Authentication
- Uses Spring Security and JWT for user authentication and authorization.
- only admin can update flight status
- Provides secure token-based authentication for API access.
- all apis except flight status need authentication

## Flight Management
- Flight status and gate number can be updated by admins.

## Ticket Booking
- Users can select and book available flights.
- Ticket booking updates flight seat availability and user's ticket history.

## Notifications
- subscibe notfication also comes when browser not in focus
- Users subscribe to flight-specific notification channels upon ticket booking.
- Flight status updates trigger notifications to subscribed users.
- System sends email notifications to users for ticket confirmations and updates.
- Real-time flight status updates are pushed to active users via FCM.

## System Flow
- User registers or logs in using Spring Security.
- User selects and books a flight.
- System creates a ticket and subscribes the user to the corresponding flight channel on FCM.
- Flight status updates trigger notifications to subscribed users via FCM and email.
- Users can view their ticket details and flight status.

## Technology Stack
- **Backend:** Spring Boot, Java, PostgreSQL, Spring Security, JWT, Firebase Admin SDK, JavaMail API
- **Frontend:** React
- **Database:** PostgreSQL
- **Messaging:** Firebase Cloud Messaging

## Key Features
- User authentication and authorization
- Flight search and booking
- Real-time flight status updates
- Push notifications
- Email notifications
- Admin flight management

## Additional Notes
Could consider using a separate service for notification handling, didnt created sepearete project for this becuase didnt know could use any technology, better solution would be to create a queue and host mail and notification there, but still here i am creating new thread for this.
