# Boobank Demo Application
This is the test bank application by Java Spring Boot Framework. This application provides adding new transactions, viewing the monthly and cumulative balances, and using the API transaction list call.

# How to install and use an application

## Requirements
- Java 17 (openjdk 17.0.7)
- Apache Maven 3.6.3
- PostgreSQL 14.8

## Installation

We list the methods, and how to use the Java Spring Application.

### Docker (recommended)
- Install the Docker desktop on your computer: https://docs.docker.com/engine/install/
- Complete the Docker installation (if you are using the WSL, remember to check WSL integration option)
    - If you are new on WSL, please read this: `https://learn.microsoft.com/en-us/windows/wsl/install`
- Git clone the repository `git clone https://github.com/jussipalanen/bonkbank-java-spring.git` in your workspace e.g `/home/user/projects/boobank` or `C:/projects/boobank`
- Go to the directory and run the main hell-script file `sh toolbox build` in your workspace directory or otherwise e.g.
`docker build -t boobank-demo` and then `docker compose up`
- The compose file installs containers and required libraries, and initializes the database for JPA entities. You do not need to create a new database, because the compose file creates itself.
- If the build was built successfully, try to go browser and type into the address bar: 
    - http://localhost:8080/ (Web UI)
    - http://localhost:5000/ (Adminer)

### WSL (Windows Subsystem for Linux)
- Install Java, Maven, and PostgreSQL on your local server (see the requirements section)
- In PostgreSQL installation, remember the username and password, and what are you using. 
- You can create the new database and give it a name: `boobank`. The username and password are required in database configuration in the file `application.properties`. 
- Fill the `spring.datasource.username` and `spring.datasource.password`. Check the `spring.datasource.url`, if it's a valid host. It can be `jdbc:postgresql://127.0.0.1:5432/boobank` or `jdbc:postgresql://localhost:5432/boobank`

## Usage
You can see the links on the top navbar: "Home", "Add transaction", "Balances", and "API docs"....
### Home
Nothing special. You can see only the current balance of the test customer. "Demo effect."

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

### How is this application testable?
- Yes, this application is testable locally or remotely. Check the following section: `How to install and use an application`. As Windows user, I recommend Docker Desktop, because it's already configured and easy to use.

### Deploying in a remote location
- You can deploy this application somewhere remote e.g. 
    - https://aws.amazon.com/ AWS (Amazon Web Services) 
    - https://kinsta.com/
    - https://www.hetzner.com/
    - Microsoft Azure
- Buy a plan, and create a new host (like Ubuntu & Linux virtual host)
- Install all of the required libraries and packages for this application: 
    - Java
        - `sudo apt-get install openjdk-17-jdk openjdk-17-jre`
        - If you want, you can check the java version by command: `java --version`
    - Maven
        - `sudo apt-get install maven`
    - PostgreSQL
        - `sudo apt install postgresql postgresql-contrib`
        - after installation, configure the username, password and database 
    - and optional libraries, if needed (like Adminer for PostgreSQL Web UI).
- Create a new SSH user (later for automation development like CI/CD. This is a simple and good enough (https://buddy.works/)
- Clone the repository in your remote host in somewhere your workspace directory.
- Configure the `application.properties` file for the database settings and connection.
- Clean and package the project manually in your remote host: `mvn clean package`. 
- You can run the application manually in your remote host: `mvn spring-boot:run`. 
- All done on a remote server! You can configure the CI/CD automation for the auto-deployment. The automation deployment needs the SSH key of the user and creates the build commands.


### How to test Rest APIs?
- Check the API section in this file. Postman application is useful to use those things.

### This application is a demo version at the moment, what kind of features are upcoming on full release?
- User registration
- User authentication with username and password
- Strong authentication for user login and transaction confirmation. This uses the external OpenClient APIs like "Telia vahva tunnistautuminen".
- Better validation of the forms
- Filters on the transaction view
- ... any ideas?

Hopefully this guide works! 

Best regards,
Jussi Alanen
