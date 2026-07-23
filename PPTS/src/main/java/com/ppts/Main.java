package com.ppts;

import com.ppts.dao.ProblemDao;
import com.ppts.dao.UserDao;
import com.ppts.models.Problem;
import com.ppts.models.User;
import com.ppts.service.TrackerService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserDao userDao = new UserDao();
    private static ProblemDao problemDao = new ProblemDao();
    private static TrackerService trackerService = new TrackerService();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  Welcome to Placement Preparation Tracker System ");
        System.out.println("==================================================");

        while (true) {
            if (loggedInUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showAuthMenu() {
        System.out.println("\n--- Authentication Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Exiting PPTS. Good luck with your preparation!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDao.loginUser(username, password);
        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + username);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your current competitive rating (e.g. 1100): ");
        int currentRating = getIntInput();
        System.out.print("Enter your target rating (e.g. 1600): ");
        int targetRating = getIntInput();

        User user = new User(0, username, password, currentRating, targetRating);
        if (userDao.registerUser(user)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Username might be taken.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Solved Problem");
        System.out.println("2. View All Solved Problems");
        System.out.println("3. Search Problems (By Topic/Platform)");
        System.out.println("4. Update Problem");
        System.out.println("5. Delete Problem");
        System.out.println("6. Update Current Rating");
        System.out.println("7. View Stats & Readiness Score");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: addProblem(); break;
            case 2: viewProblems(); break;
            case 3: searchProblems(); break;
            case 4: updateProblem(); break;
            case 5: deleteProblem(); break;
            case 6: updateRating(); break;
            case 7: trackerService.generateStats(loggedInUser); break;
            case 8: 
                loggedInUser = null; 
                System.out.println("Logged out successfully.");
                break;
            default: System.out.println("Invalid option.");
        }
    }

    private static void addProblem() {
        System.out.print("Problem Name: ");
        String name = scanner.nextLine();
        System.out.print("Topic (e.g. DP, Graphs, Arrays): ");
        String topic = scanner.nextLine();
        System.out.print("Platform (e.g. LeetCode, Codeforces): ");
        String platform = scanner.nextLine();
        System.out.print("Difficulty (Easy/Medium/Hard): ");
        String diff = scanner.nextLine();
        System.out.print("Language (e.g. C++, Java, Python): ");
        String lang = scanner.nextLine();

        Problem p = new Problem(0, loggedInUser.getId(), name, topic, platform, diff, lang, new Date(System.currentTimeMillis()));
        if (problemDao.addProblem(p)) {
            System.out.println("Problem added successfully!");
        } else {
            System.out.println("Failed to add problem.");
        }
    }

    private static void viewProblems() {
        List<Problem> problems = problemDao.getProblemsByUserId(loggedInUser.getId());
        if (problems.isEmpty()) {
            System.out.println("No problems solved yet. Start practicing!");
            return;
        }
        System.out.println("\n--- Your Solved Problems ---");
        for (Problem p : problems) {
            System.out.println(p);
        }
    }

    private static void searchProblems() {
        System.out.println("Search by: 1. Topic  2. Platform");
        int type = getIntInput();
        String filterType = (type == 1) ? "topic" : "platform";
        
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        
        List<Problem> problems = problemDao.searchProblems(loggedInUser.getId(), filterType, keyword);
        if (problems.isEmpty()) {
            System.out.println("No matching problems found.");
            return;
        }
        System.out.println("\n--- Search Results ---");
        for (Problem p : problems) {
            System.out.println(p);
        }
    }

    private static void updateProblem() {
        viewProblems();
        System.out.print("\nEnter the ID of the problem to update: ");
        int id = getIntInput();
        
        System.out.print("New Problem Name: ");
        String name = scanner.nextLine();
        System.out.print("New Topic: ");
        String topic = scanner.nextLine();
        System.out.print("New Platform: ");
        String platform = scanner.nextLine();
        System.out.print("New Difficulty: ");
        String diff = scanner.nextLine();
        System.out.print("New Language: ");
        String lang = scanner.nextLine();

        Problem p = new Problem(id, loggedInUser.getId(), name, topic, platform, diff, lang, null);
        if (problemDao.updateProblem(p)) {
            System.out.println("Problem updated successfully!");
        } else {
            System.out.println("Update failed. Ensure the ID is correct.");
        }
    }

    private static void deleteProblem() {
        viewProblems();
        System.out.print("\nEnter the ID of the problem to delete: ");
        int id = getIntInput();
        
        if (problemDao.deleteProblem(id, loggedInUser.getId())) {
            System.out.println("Problem deleted successfully.");
        } else {
            System.out.println("Deletion failed. Ensure the ID is correct.");
        }
    }

    private static void updateRating() {
        System.out.print("Enter your new current rating: ");
        int newRating = getIntInput();
        if (userDao.updateRating(loggedInUser.getId(), newRating)) {
            loggedInUser.setCurrentRating(newRating);
            System.out.println("Rating updated successfully!");
        } else {
            System.out.println("Failed to update rating.");
        }
    }

    private static int getIntInput() {
        int val = -1;
        try {
            val = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        return val;
    }
}
