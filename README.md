# Boobank Demo Application
This is the test bank application by Java Spring Boot Framework. This application provides adding new transactions, viewing the monthly and cumulative balances, and using the API transaction list call.

# How to install and use an application

## Requirements
- Java 17 (openjdk 17.0.7)
- Apache Maven 3.6.3
- PostgreSQL 14.8

## Installation

We list the methods, how to use the Java Spring Application.
### Docker (recommended)
- Install the Docker desktop on your computer: https://docs.docker.com/engine/install/
- Complete the Docker installation (if you are using the WSL, remember checked)
- Git clone the repository `git clone https://github.com/jussipalanen/bonkbank-java-spring.git` in your workspace e.g `/home/user/projects/boobank` or `C:/projects/boobank`
- Go to directory and run the shell-script file `sh toolbox build` in your workspace directory or otherwise e.g.
`docker build -t boobank-demo` and then `docker compose up`
- If the build was built successfully, try to go browser and type into the address bar: 
    - http://localhost:8080/ (Web UI)
    - http://localhost:5000/ (Adminer)

### WSL
- Install Java, Maven, and PostgreSQL in your computer (see the requirements section)
- In Postgres installation, remember the username and password, what are you using. You can create the new database and give a name: `boobank`. The username and password are required in database configuration `application.properties`. 
- Fill the `spring.datasource.username` and `spring.datasource.password`.

## Usage
You can see the links on the top navbar: "Home", "Add transaction", "Balances", and "API docs"....
### Home
Nothing special. You can see onky the current balance of test customer. "Demo effect."

### Add Transaction
This is a basic form. You can give input of amount, message, date, and customer. Fill in all of the fields, and try to submit a transaction. 

### Balances (transactions)
This is a view of the balances (transactions). The table contains the ID (UUID), amount, message, and cumulative balance of transactions. The pagination, year, and month filters have been implemented in this section.

### API
#### List all of the transactions
- URL: `http://localhost:8080/api/v1/transactions`
- Optional query GET parameters:
    - page=1
    - size=10
    - sortDirection=ASC|DESC
    - sortBy=id|amount|date
- Example: `http://localhost:8080/api/v1/transactions?page=1&size=100&sortBy=date&sortDirection=DESC`

#### Count of the transactions
- URL: `http://localhost:8080/api/v1/transactions/count`

https://www.postman.com/downloads/


## Extras

### How this application is testable?
- Check the section in this README.md file: `How to install and use an application`. 
I recommend the Docker, because it's already configured and easy to use.

### Deploying in remote location
You can deploy this application somewhere remote location e.g AWS (Amazon Web Services) https://aws.amazon.com/  is good place to host, and Kinsta is pretty good too https://kinsta.com/.

- Buy a plan, and create an new host (like Ubuntu & Linux virtual host)
- Install all of the required libraries and packages for this application: 
    - Java
    - Maven
    - PostgreSQL
    - and optional libraries, if needed (like Adminer for PostgreSQL Web UI).
- Create a new SSH user (later for the automation development like CI/CD. This is a simply and good enough (https://buddy.works/)
- Clone the repository in your remote host somwhere workspace dirrectory.
- Configure the `application.properties` file for the database settings and connection.
- Clean and package the project manually in your remote host: `mvn clean package`. 
- You can run the application manually in your remote host: `mvn spring-boot:run`. 
- All done in remote server! You can configure the CI/CD automation for the auto-deployment. The automation deployment needs the SSH key of user, and create the build commands.