# M-advise

# Installation of this API
To run the M-advise API locally as a Spring Boot application, follow these steps:

- ***Prerequisites***:
    - Java 17 or higher installed.
    - Database credentials (DB_URL, DB_USER, DB_PASSWORD) for PostgreSQL.
- ***Environment Setup***:
    - Configure your $JAVA_HOME environment variable.
    - Set up the following environment variables in your application.properties or application.yml file:
    <pre>
        spring.application.name=route
        spring.flyway.locations=classpath:/db/migration
        spring.jpa.hibernate.ddl-auto=none
        spring.jpa.show-sql=false
        spring.jpa.open-in-view=true
        spring.datasource.url=${DB_URL}
        spring.datasource.username=${DB_USER}
        spring.datasource.password=${DB_PASSWORD}
        gemini.project.id=${GEMINI_PROJECT_ID}
        gemini.location=${GEMINI_LOCATION}
        gemini.type=gemini-pro
        gemini.api.key=${GEMINI_API_KEY}
        sentry.dsn=${SENTRY_DSN}
        sentry.environment=${SENTRY_ENV}
        firebase.private.key=${FIREBASE_API_KEY}
        bucket.name=greenroute
    </pre>
- ***Run the Application***:
Use your IDE or the command line to build and run the Spring Boot application.