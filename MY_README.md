Once the server is started, information about available apis can be found at
http://localhost:8080/swagger-ui/index.html

If you want to run the application, there are 2 ways of running it.

1) With Docker Desktop
You need to have installed Docker Desktop

In command line go to module ClientExistenceApi and execute command "docker build -t existence ."

In command line go to module ClientReputationApi and execute command "docker build -t reputation ."

In command line go to root of the project and execute command "docker build -t banking ."

In command line go to root of the project and execute command "docker compose up"

The application should be up and running.

2) From IDE, without Docker Desktop

Open main project in IDE

In application.properties uncomment "spring.profiles.active=dev"

Run the project from BankingSystemApplication main function

Open ClientExistenceApi in IDE in another window

Run the existence app from ClientExistenceApiApplication main function

Open ClientReputationApi in IDE in another window

Run the reputation app from ClientReputationApiApplication main function

The app should be good to go now.



--------------------------------------------------------------

In db of clientExistenceApi there are these values inserted by default

NationalId --- Name

('2990101030021', 'Andreea');

('1990101030021', 'Dragos');

Example of json format when calling /clients/verify endpoint
{
"creationDate": "2023-02-02",
"expirationDate" : "2024-01-01",
"nationalId" : "1990101030021",
"fullName" : "Dragos"
}

---------------------------------------------------------------------

The rule used for reputation is
return 10 when nationalId starts with 2
return 60 when nationalId starts with 1
return 100 otherwise