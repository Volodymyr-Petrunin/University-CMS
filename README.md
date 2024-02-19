# University-CMS

This project is about the university class schedule. You can look UML-diagram in directory `docs.`

All data for the database is generated from resources. For example, lessons, students and teachers.

To make development easier, I create an `AdminFiller class`. This class creates an administrator account in the database.

You can then log into the webpage (it may not look very nice, but I'm a backend developer :) ) and view your schedule.

Also all CRUDs work for the entire domain. I mean you can do whatever you want with the models.

In this project I use:
+ Spring Boot 
+ Spring MVC
+ Spring Data
+ Spring Security 6
+ Thymeleaf
+ PostgresSQL
+ Flyway
+ WebJars
+ Bootstrap
+ Lombock
+ Mapstract
+ GreenMail (for sending simulated email messages)
