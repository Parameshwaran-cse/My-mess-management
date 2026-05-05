package com.mess.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

/**
 * Main entry point for the Mess Management System Spring Boot application.
 *
 * ─── HOW TO CONNECT MYSQL LATER ──────────────────────────────────────────────
 *  Step 1: Remove the 'exclude' list from @SpringBootApplication below.
 *  Step 2: Uncomment the DB block in application.properties.
 *  Step 3: Uncomment the MySQL dependency in pom.xml.
 *  Step 4: Delete all InMemory* repository classes.
 *  Step 5: Create JpaRepository interfaces (UserRepository extends JpaRepository<User, Long>).
 * ─────────────────────────────────────────────────────────────────────────────
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class MessManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessManagementApplication.class, args);
        System.out.println("\n======================================================");
        System.out.println("  Mess Management System started on port 8080");
        System.out.println("  API Base URL: http://localhost:8080/api");
        System.out.println("======================================================\n");
    }
}
