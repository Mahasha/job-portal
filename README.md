# Job Portal Application

![image](https://github.com/Mahasha/job-portal/assets/29414359/120f0757-139a-4a33-a67e-405038a5ea7b)

## Overview

The Job Portal Application is designed to connect job seekers with potential employers. It provides a seamless experience for users to search for jobs, apply for positions, and manage their applications. Recruiters can post job listings, review applications, and manage their recruitment process.

## Features

- User authentication and authorization
- Job search and filtering
- Job application submission and tracking
- Recruiter job posting and management
- Role-based access control
- Responsive design for mobile and desktop

## Technologies Used

- **Backend**: Java 22, Spring Boot, Spring Data JPA, Spring Security, Lombok
- **Frontend**: Angular
- **Database**: PostgreSQL
- **Tools**: Maven, Git, Docker

## Getting Started

### Prerequisites

- JDK 22
- Node.js and npm
- PostgreSQL
- Docker (optional for containerized deployment)

### Installation

1. **Clone the repository**

    ```bash
    git clone https://github.com/mahasha/job-portal.git
    cd job-portal
    ```

2. **Backend Setup**

    - Navigate to the backend directory:

        ```bash
        cd backend
        ```

    - Update the `application.properties` with your PostgreSQL configuration:

        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/jobportal
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
        ```

    - Build and run the backend application:

        ```bash
        mvn clean install
        mvn spring-boot:run
        ```

3. **Frontend Setup**

    - Navigate to the frontend directory:

        ```bash
        cd frontend
        ```

    - Install dependencies and run the Angular application:

        ```bash
        npm install
        ng serve
        ```

4. **Access the Application**

    - Open your browser and navigate to `http://localhost:4200` for the frontend and `http://localhost:8080` for the backend.

## Usage

### For Job Seekers

- **Sign Up**: Create an account to start applying for jobs.
- **Search Jobs**: Use the search functionality to find jobs that match your skills.
- **Apply for Jobs**: Submit your application with a single click.

### For Recruiters

- **Post Jobs**: Add new job listings with detailed descriptions.
- **Manage Applications**: Review and manage applications from potential candidates.
- **Role-Based Access**: Manage access and permissions based on user roles.

## Contributing

We welcome contributions from the community! To contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

- **Rethsphile Mahasha** - [LinkedIn](https://linkedin.com/in/mahasha) - [Twitter](https://twitter.com/mahasha_peu)
- **Project Link**: [https://github.com/mahasha/job-portal](https://github.com/mahasha/job-portal)
