# Java Authentication Application

This is a simple Java authentication application where users can login, create new accounts, and change passwords.

## Database Setup

### MySQL Database

1. Create a new MySQL database using the provided SQL script:

   ```bash
   mysql -u your_username -p < mysql_db/au_sys.sql

   
- Replace your_username with your MySQL username, and you will be prompted to enter your MySQL password.
The SQL script creates the users table. The schema includes fields like id, username, password, etc.

## Default User
The default user is created during the database initialization:
- Username: admin1212
- Password: Admin@143

## Application Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/miraj004/java-authentication-app.git

## IntelliJ IDEA:
### Connect MySQL Connector/J:
1. Open IntelliJ IDEA and your project.
2. In the Project Explorer, right-click on your project.
3. Select "Open Module Settings" or press F4.
4. In the "Project Structure" dialog, navigate to "Libraries."
5. Click the + icon to add a new library.
6. Choose "Java" and select both the MySQL Connector/J JAR file (drivers/mysql-connector-java-8.0.22.jar) and rs2xml.jar.
7. Click "OK" to apply the changes.


## Eclipse:
### Connect MySQL Connector/J:
1. Open Eclipse and your project.
2. Right-click on your project in the "Project Explorer."
3. Select "Properties."
4. In the "Properties" dialog, navigate to "Java Build Path."
5. Go to the "Libraries" tab.
6. Click "Add External JARs" and select both the MySQL Connector/J JAR file (drivers/mysql-connector-java-8.0.22.jar) and rs2xml.jar.
7. Click "Apply and Close" to apply the changes.

## Usage
Launch the application and follow the on-screen prompts to login, create a new account, or change passwords.
Use the default user credentials mentioned above to log in initially.

## Dependencies
- MySQL Connector/J (version 8.0.22)
- rs2xml

