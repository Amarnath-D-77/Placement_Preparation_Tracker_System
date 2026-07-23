package com.ppts.service;

import com.ppts.dao.ProblemDao;
import com.ppts.models.Problem;
import com.ppts.models.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrackerService {
    private ProblemDao problemDao = new ProblemDao();

    public void generateStats(User user) {
        List<Problem> problems = problemDao.getProblemsByUserId(user.getId());
        
        System.out.println("\n--- Preparation Statistics ---");
        System.out.println("Total Problems Solved: " + problems.size());
        
        Map<String, Long> byTopic = problems.stream()
            .collect(Collectors.groupingBy(Problem::getTopic, Collectors.counting()));
        
        System.out.println("\nBy Topic:");
        byTopic.forEach((topic, count) -> System.out.printf(" - %-15s : %d%n", topic, count));
        
        Map<String, Long> byPlatform = problems.stream()
            .collect(Collectors.groupingBy(Problem::getPlatform, Collectors.counting()));
            
        System.out.println("\nBy Platform:");
        byPlatform.forEach((platform, count) -> System.out.printf(" - %-15s : %d%n", platform, count));

        Map<String, Long> byDifficulty = problems.stream()
            .collect(Collectors.groupingBy(Problem::getDifficulty, Collectors.counting()));
            
        System.out.println("\nBy Difficulty:");
        byDifficulty.forEach((diff, count) -> System.out.printf(" - %-15s : %d%n", diff, count));

        System.out.println("\n--- Rating Progression ---");
        System.out.println("Current Rating: " + user.getCurrentRating());
        System.out.println("Target Rating : " + user.getTargetRating());
        
        if (user.getCurrentRating() < 1200) {
            System.out.println("Band: Pupil/Newbie (Focus on logic and implementation)");
        } else if (user.getCurrentRating() < 1400) {
            System.out.println("Band: Specialist (Focus on standard algorithms)");
        } else if (user.getCurrentRating() < 1600) {
            System.out.println("Band: Expert (Great! Keep pushing for Candidate Master)");
        } else {
            System.out.println("Band: Advanced (Placement Ready!)");
        }
        
        int readinessScore = calculateReadinessScore(user, problems);
        System.out.println("\n=> PLACEMENT READINESS SCORE: " + readinessScore + "/100");
    }

    private int calculateReadinessScore(User user, List<Problem> problems) {
        int score = 0;
        
        // Volume of practice (max 40 points for 200+ problems)
        int volumeScore = Math.min(40, (int)((problems.size() / 200.0) * 40));
        score += volumeScore;
        
        // Rating score (max 40 points for 1600+ rating)
        int ratingScore = Math.min(40, (int)(((user.getCurrentRating() - 800) / 800.0) * 40));
        if (ratingScore < 0) ratingScore = 0;
        score += ratingScore;
        
        // Diversity score (max 20 points for practicing across multiple topics)
        long uniqueTopics = problems.stream().map(Problem::getTopic).distinct().count();
        int diversityScore = Math.min(20, (int)(uniqueTopics * 2));
        score += diversityScore;
        
        return Math.min(100, score);
    }
}
