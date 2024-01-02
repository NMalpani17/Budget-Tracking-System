# Welcome to the course CSYE6200 - Object Oriented Design
> Northeastern University, College of Engineering


## Professor: Daniel Peters

### Requirements
1. Eclipse or VS Code or IntelliJ.

Note: If you are using Eclipse, please have git CLI installed on your system or GitHub Desktop to commit the code in this repository

### SetUp Instructions
1. Please clone the repository on your local system
2. For Eclipse Import the project as Existing Maven Project, For IntelliJ you can directlty open it using 'Get from VCS'.
4. All code should be pushed to the main branch
3. Ensure the GitHub actions are successful post push

Submissions will have deadlines, failed GitHub Actions would result in point deductions.

### References
1. Cloning a Repository: <https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository>
2. Any GitHub Setup: Please refer to the Git & GitHub Fundamentals Repository shared to you by your respective TA and refer the README.md section

Please reach out to your respective TA if you need any help in regards with submission/ GitHub

Author:
- Ruchika Sharma (sharma.ruc@northeastern.edu)

# Financial Budget Tracking System

## Overview
The Financial Budget Tracking System is an innovative expense-sharing application designed to assist individuals and groups in tracking and managing shared expenses. Whether you're collaborating with professionals on a project, sharing costs with friends during a trip, managing family expenses, or simply splitting bills with the general public, our system ensures fair and transparent expense distribution.

## Features
1. User sign-up if not registered, else user can login directly with the credentials 
2. User-Friendly Interface: Enjoy a sleek, intuitive design for effortless expense tracking.
3. Users get an option to add a new group or get added into an existing group. Users can search the groups by name.
4. Users can remove a group when no longer required.
5. Expense Logging: Easily log individual expenses with detailed information, including date, category, and payer.
6. Users can view group expenses which shows description, date, amount, payer details.
7. Users can view group debts among the group members, which shows up as a paginated response.
8. Users can simplify the debts among one-to-one within a group to avoid confusion and hassle.
9. Group Management: Create groups for different activities or shared responsibilities and share the expenses equally or unequally among the group members.
10. Real-time Updates: Stay informed with real-time expense updates, ensuring transparency and accountability.
11. Add an equally split expense using uploading CSV file, multiple expenses can be added at a single shot.
12. Calculator (Java Swing): Users can perform calculations within the application, enhancing its functionality.

## Technologies Used
1. Java SpringBoot: Powering the backend with a robust and scalable framework.
2. Java: Employed for various backend functionalities and logic.
3. Java Flyway Migration: Used to create the tables on the go when the server comes up.
4. React: Delivering a dynamic and responsive user interface for a seamless user experience.
5. MySQL: Providing a reliable and efficient database solution for data storage.
6. Java Swing: Integrating a calculator feature for users to perform calculations within the application. Users can see the latest operations performed.
7. Spark : Used to host swing application using spring boot on a specific post to allow React application to communicate with Swing application.

## Getting Started

### Prerequisites
Before you get started with the Financial Budget Tracking System, ensure that you have the following prerequisites installed on your machine:
1. Java Development Kit (JDK) 20: Visit the official Oracle website to download and install JDK 20.
2. Visual Studio Code (VSCode) for React: Download and install VSCode from the official website. Install the necessary React extensions for a smooth development experience.
3. IntelliJ IDEA for Java SpringBoot: Download and install IntelliJ IDEA from the official website. Set up your SpringBoot project within IntelliJ for efficient Java development.
4. MySQL Database: Download and install MySQL from the official website. Set up a MySQL database to store application data.

### Installation
1. Clone the repository: https://github.com/CSYE6200-Object-Oriented-DesignFall2023/final-project-final-group-6
2. Navigate to the backend directory in Eclipse and run the Driver class.
3. Open the MySQL 80 CLI: Fetch the password from the application.properties and enter it correctly. It will provide access to show databases in the application.
4. Navigate to the Services settings of the Local system and make sure that the MySQL80 service is running.
5. Navigate to the frontend directory and run: npm install && npm start.
6. Access the application at http://localhost:3000 in your web browser.
7. Start the Calculator.java class manually which runs on the port 8081 and spring boot application runs on the port 8080.
8. Enable Allow CORS (use CORS chrome plugin) on the browser if required, to avoid CORS policy error when react and java applications are trying to communicate on different ports. 

### Future Goals
1. Uploading an image while adding an expense, and returning the image with the list of expenses. Backend code is developed as of now.
2. Authorize Admin access to the application to provide more access, backend changes to Authorise admin is completed as of now.
3. Usage of @Aspect to perform authorization based on user roles.
4. Linking bank account to the application to provide access for users to pay their debts on time.







