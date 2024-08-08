# 👗 Textile Design Application 👕
[![Textile Design Application](https://img.shields.io/badge/Textile%20Design-Application-brightgreen.svg)](https://github.com/JananJavdan/demo-textiledesign-application)

## Overview
The Textile Design Application is a web-based platform designed to streamline the creation, customization, and management of textile designs. It provides distinct functionalities for different user roles, including Customers, Admins, Design Managers, and Order Managers. The application supports user registration, account management, design customization, order processing, and email notifications.

## Features
- 🔑 **User Management**: Registration, login, account updates, and account deletion.
- 🎨 **Design Management**: Create, update, delete, and view designs with custom logos and colors.
- 📦 **Order Management**: Place orders, update order statuses, and fetch order details.
- 🔒 **Role-Based Access Control**: Different functionalities based on user roles (Admin, Customer, Design Manager, Order Manager).
- ✉️ **Email Notifications**: Send confirmation and notification emails to users.

## Entity Relationships
![Entity Class Diagram](https://github.com/JananJavdan/demo-textiledesign-application/blob/main/entity_class_diagram.png)

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
   git clone https://github.com/JananJavdan/demo-textiledesign-application.git
   cd demo-textiledesign-application



### Steps to Ensure Correct Path:

1. **Ensure the Image is in the Correct Directory:**
   - Place the `entity_class_diagram.png` image in the `assets` folder at the root of your project directory.

2. **Add and Commit the Image and README.md:**
   - Ensure that you add and commit both the image and the updated README.md file to your repository.

```bash
git add assets/entity_class_diagram.png
git add README.md
git commit -m "Add entity class diagram and update README"
git push origin main
