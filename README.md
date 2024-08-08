# 🌟 Textile Design Application 🌟

## Overview
The Textile Design Application is a web-based platform designed to streamline the creation, customization, and management of textile designs. It provides distinct functionalities for different user roles, including Customers, Admins, Design Managers, and Order Managers. The application supports user registration, account management, design customization, order processing, and email notifications.

## Features
- 🔑 **User Management**: Registration, login, account updates, and account deletion.
- 🎨 **Design Management**: Create, update, delete, and view designs with custom logos and colors.
- 📦 **Order Management**: Place orders, update order statuses, and fetch order details.
- 🔒 **Role-Based Access Control**: Different functionalities based on user roles (Admin, Customer, Design Manager, Order Manager).
- ✉️ **Email Notifications**: Send confirmation and notification emails to users.

## Entity Relationships
![Entity Class Diagram](assets/entity_class_diagram.png)

- **User**: Attributes include id, name, email, password, phone number, and role.
- **Customer**: Extends User with additional attributes like address and registration_date.
- **Admin**: Extends User with administrative functionalities.
- **Design Manager**: Extends User with design-related functionalities.
- **Order Manager**: Extends User with order-related functionalities.
- **Design**: Attributes include design_id, category, color, size, logo, customer_id, and designer_id.
- **Order**: Attributes include id, design_id, customer_id, quantity, total_price, and status.
- **EmailService**: Handles sending confirmation and notification emails.

## Installation

### Prerequisites
- ☕ **Java JDK 11** or higher
- 🛠️ **Maven**
- 🗄️ **MySQL** or another relational database

### Steps
1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/textile-design-application.git
   cd textile-design-application




