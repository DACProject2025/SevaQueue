# SevaQueue

SevaQueue is a full‑stack web application designed to digitize and manage queues in government offices. The system follows a microservice‑ready architecture with a separate authentication service and a centralized .NET‑based logging microservice, while core queue management is implemented using Spring Boot REST APIs.

The application supports role‑based workflows for Citizens, Staff, and Admins, enabling token generation, real‑time queue status tracking, counter management, and service monitoring. Business rules such as office timings, Sunday closure, daily token limits, counter availability, and average service time–based waiting time estimation are enforced at the service layer.

The backend is built using Java (J2EE), Spring Boot, JPA/Hibernate, and MySQL, with APIs tested via Postman and documented using Swagger. The frontend is developed using React, Redux Toolkit, Bootstrap, and Axios, providing a responsive and professional user interface suitable for real‑world government systems.

This project emphasizes clean architecture, data integrity (soft delete), scalability, and practical system design.
