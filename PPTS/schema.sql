-- Create the database
CREATE DATABASE IF NOT EXISTS ppts_db;
USE ppts_db;

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    current_rating INT DEFAULT 0,
    target_rating INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Problems table
CREATE TABLE IF NOT EXISTS problems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    problem_name VARCHAR(255) NOT NULL,
    topic VARCHAR(100) NOT NULL,
    platform VARCHAR(100) NOT NULL, -- e.g., LeetCode, Codeforces, CodeChef
    difficulty VARCHAR(50) NOT NULL, -- e.g., Easy, Medium, Hard
    language VARCHAR(50) NOT NULL, -- e.g., C++, Java, Python
    solved_date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
