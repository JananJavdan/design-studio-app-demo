Textile Design Application
Overview
The Textile Design Application is a web-based platform designed to streamline the creation, customization, and management of textile designs. It provides distinct functionalities for different user roles, including Customers, Admins, Design Managers, and Order Managers. The application supports user registration, account management, design customization, order processing, and email notifications.

Features
User Management: Registration, login, account updates, and account deletion.
Design Management: Create, update, delete, and view designs with custom logos and colors.
Order Management: Place orders, update order statuses, and fetch order details.
Role-Based Access Control: Different functionalities based on user roles (Admin, Customer, Design Manager, Order Manager).
Email Notifications: Send confirmation and notification emails to users.
Entity Relationships

User: Attributes include id, name, email, password, phone number, and role.
Customer: Extends User with additional attributes like address and registration_date.
Admin: Extends User with administrative functionalities.
Design Manager: Extends User with design-related functionalities.
Order Manager: Extends User with order-related functionalities.
Design: Attributes include design_id, category, color, size, logo, customer_id, and designer_id.
Order: Attributes include id, design_id, customer_id, quantity, total_price, and status.
EmailService: Handles sending confirmation and notification emails.
Installation
Prerequisites
Java JDK 11 or higher
Maven
MySQL or another relational database
Steps
Clone the Repository

bash
Code kopiëren
git clone https://github.com/yourusername/textile-design-application.git
cd textile-design-application
Configure the Database

Create a database named textile_design.
Update the application.properties file with your database connection details.
Build the Application

bash
Code kopiëren
mvn clean install
Run the Application

bash
Code kopiëren
java -jar target/textile-design-application-0.0.1-SNAPSHOT.jar
Usage
Register a New User
Open the application.
Navigate to the registration page.
Enter the required details and submit the form.
Login
Navigate to the login page.
Enter your credentials and submit the form.
Account Management
Update Account Details: After logging in, navigate to the account settings page and update your details.
Delete Account: Admins can delete user accounts through the admin panel.
Design Management
Create a Design: Design Managers can create new designs by providing the necessary details.
Update/Delete Design: Modify or remove existing designs.
Fetch Designs: Retrieve design details for viewing or further customization.
Order Management
Place an Order: Customers can place orders for customized designs.
Update Order Status: Order Managers can update the status of orders.
Fetch Order Details: Retrieve details of existing orders.
Email Notifications
Confirmation and notification emails are sent automatically based on user actions like registration and order placement.
Diagrams
Use Case Diagram: Represents the interactions between different user roles and the system functionalities.
Flowchart Diagram: Shows the flow of different processes in the application.
Sequence Diagram: Illustrates the sequence of interactions between system components for various functionalities.
Class Diagram: Details the structure of the system in terms of classes and their relationships.
Contributing
We welcome contributions! Please fork the repository and submit a pull request for review.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
For any questions or feedback, please contact [your email address].
