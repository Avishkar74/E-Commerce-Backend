# E-Commerce Backend Assignment

A minimal E-Commerce Backend API built with Spring Boot and MongoDB, featuring Razorpay payment integration.

## 📋 Features
This project implements all mandatory requirements and bonus challenges.

###  Core Features
- **Products**: Create and list products.
- **Cart**: Add items, view cart, and clear cart.
- **Orders**: Create orders from cart, calculate totals, and manage stock.
- **Payments**: Fully integrated **Razorpay** payment gateway.
- **Webhooks**: Handles `payment.captured` event to automatically update order status to `PAID`.

###  Bonus Challenges Implemented (+20 Points)
- **Order History**: View all orders for a specific user.
- **Order Cancellation**: Cancel orders (restores product stock).
- **Razorpay Integration**: Real payment gateway implementation instead of Mock.

##  Tech Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3
- **Database**: MongoDB
- **Build Tool**: Maven

##  Setup & Running

### 1. Prerequisites
- Java 17 JDK installed.
- MongoDB installed and running locally on port `27017`.

### 2. Configuration
The application requires Razorpay credentials to function correctly.
1.  Open `src/main/resources/application.yaml`.
2.  Update the following fields with your keys:
    ```yaml
    razorpay:
      key:
        id: YOUR_RAZORPAY_KEY_ID
        secret: YOUR_RAZORPAY_KEY_SECRET
    ```

### 3. Build and Run
Open a terminal in the project root and run:
```bash
./mvnw clean spring-boot:run
```
The server will start at `http://localhost:8080`.

## 🔌 API Endpoints
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Product** | POST | `/api/products` | Create a new product |
| | GET | `/api/products` | Get all products |
| **Cart** | POST | `/api/cart/add` | Add item to user's cart |
| | GET | `/api/cart/{userId}` | Get user's cart |
| | DELETE | `/api/cart/{userId}/clear` | Clear user's cart |
| **Order** | POST | `/api/orders` | Create order from cart |
| | GET | `/api/orders/{orderId}` | Get order details |
| | GET | `/api/orders/user/{userId}` | Get user's order history |
| | POST | `/api/orders/{orderId}/cancel` | Cancel an order |
| **Payment**| POST | `/api/payments/create` | Initiate Razorpay payment |
| **Webhook**| POST | `/api/webhooks/payment` | Handle Razorpay callbacks |

## 🧪 Testing with Postman
1.  **Create Product** -> Copy `id`.
2.  **Add to Cart** using `productId`.
3.  **Create Order** -> Copy `id` (this is your internal Order ID).
4.  **Initiate Payment** -> Copy `paymentId` (this is the **Razorpay Order ID** starting with `order_...`).
5.  **Simulate Webhook** -> Use the Razorpay Order ID in the payload as `order_id`.
6.  **Get Order** -> Verify status is `PAID`.

## 📂 Project Structure
```
src/main/java/com/Avishkar/E_Commerce_Backend
├── config       # App configurations (Razorpay)
├── controller   # REST APIs
├── dto          # Data Transfer Objects
├── model        # MongoDB Entities
├── repository   # Database Interfaces
├── service      # Business Logic
├── webhook      # Payment Webhook Handler
└── ECommerceBackendApplication.java
```
