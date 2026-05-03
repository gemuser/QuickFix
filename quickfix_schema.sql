CREATE DATABASE IF NOT EXISTS quickfix_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quickfix_db;

DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS complaints;
DROP TABLE IF EXISTS ratings_feedback;
DROP TABLE IF EXISTS booking_status_history;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS booking_statuses;
DROP TABLE IF EXISTS provider_availability;
DROP TABLE IF EXISTS provider_services;
DROP TABLE IF EXISTS service_categories;
DROP TABLE IF EXISTS provider_profiles;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(25),
    password_hash VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE','PENDING','BLOCKED','REJECTED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE addresses (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    line1 VARCHAR(150) NOT NULL,
    city VARCHAR(80) NOT NULL,
    state VARCHAR(80),
    postal_code VARCHAR(20),
    country VARCHAR(80) NOT NULL DEFAULT 'Nepal',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE provider_profiles (
    profile_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    bio TEXT,
    experience_years INT NOT NULL DEFAULT 0,
    verification_status ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING',
    average_rating DECIMAL(3,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE service_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(80) NOT NULL UNIQUE,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE provider_services (
    service_id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    category_id INT NOT NULL,
    service_title VARCHAR(120) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (provider_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES service_categories(category_id)
);

CREATE TABLE provider_availability (
    availability_id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    available_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (provider_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE booking_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    provider_id INT NOT NULL,
    service_id INT NOT NULL,
    status_id INT NOT NULL,
    booking_date DATE NOT NULL,
    booking_time TIME NOT NULL,
    address_id INT,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    FOREIGN KEY (provider_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES provider_services(service_id),
    FOREIGN KEY (status_id) REFERENCES booking_statuses(status_id),
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

CREATE TABLE booking_status_history (
    history_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    status_id INT NOT NULL,
    changed_by INT NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note VARCHAR(255),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES booking_statuses(status_id),
    FOREIGN KEY (changed_by) REFERENCES users(user_id)
);

CREATE TABLE ratings_feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    provider_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    FOREIGN KEY (provider_id) REFERENCES users(user_id)
);

CREATE TABLE complaints (
    complaint_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    raised_by INT NOT NULL,
    against_user_id INT,
    subject VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('OPEN','IN_REVIEW','RESOLVED','REJECTED') NOT NULL DEFAULT 'OPEN',
    admin_response TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE SET NULL,
    FOREIGN KEY (raised_by) REFERENCES users(user_id),
    FOREIGN KEY (against_user_id) REFERENCES users(user_id)
);

CREATE TABLE notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(120) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO roles (role_name) VALUES ('CUSTOMER'), ('PROVIDER'), ('ADMIN');
INSERT INTO booking_statuses (status_name) VALUES ('PENDING'), ('ACCEPTED'), ('REJECTED'), ('IN_PROGRESS'), ('COMPLETED'), ('CANCELLED');
INSERT INTO service_categories (category_name, description) VALUES
('Plumbing', 'Pipe, faucet, leakage, and bathroom repair'),
('Electrical', 'Wiring, fixtures, appliance, and safety repairs'),
('Carpentry', 'Furniture, doors, cabinets, and woodwork'),
('Cleaning', 'Home, kitchen, bathroom, and deep cleaning');

INSERT INTO users (role_id, full_name, email, phone, password_hash, status)
VALUES (3, 'System Admin', 'admin@quickfix.local', '9800000000',
'1000:AQIDBAUGBwgJCgsMDQ4PEA==:0N5Ap2V4qGNQrHTGAan8uYSAR4oCHZLPpxd5TlJKvRk=',
'ACTIVE');
