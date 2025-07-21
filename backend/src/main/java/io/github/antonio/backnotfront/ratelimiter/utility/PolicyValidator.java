package io.github.antonio.backnotfront.ratelimiter.utility;

public class PolicyValidator {
    private static final Integer MIN_CAPACITY = 1;
    private static final Integer MAX_CAPACITY = 10_000;
    private static final Integer MIN_WINDOW_SIZE = 10;
    private static final Integer MAX_WINDOW_SIZE = 86_400;
    private static final Integer MAX_POLICY_NUMBER = 10;

    public static boolean validateCapacity(Integer capacity) {
        return MIN_CAPACITY <= capacity && MAX_CAPACITY >= capacity;
    }

    public static boolean validateWindowSize(Integer windowSize) {
        return MIN_WINDOW_SIZE <= windowSize && MAX_WINDOW_SIZE >= windowSize;
    }

    public static boolean validatePolicyNumber(Integer policyNumber) {
        return MAX_POLICY_NUMBER >= policyNumber;
    }

}
