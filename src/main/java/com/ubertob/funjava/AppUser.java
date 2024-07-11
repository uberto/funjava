package com.ubertob.funjava;

import jakarta.persistence.*;

//@Entity
//@Table(name = "users")
//public record AppUser(
//        @Id
//        Long id,
//        String name,
//        String email
//) {}

@Entity
@Table(name = "users")
public class AppUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String email;

        // Default constructor required by JPA
        public AppUser() {
        }

        // Parameterized constructor for convenience
        public AppUser(String name, String email) {
                this.name = name;
                this.email = email;
        }

        // Getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }
}