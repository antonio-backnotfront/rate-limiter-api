package io.github.antonio.backnotfront.ratelimiter.utility;

import io.github.antonio.backnotfront.ratelimiter.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Test validation methods in PolicyValidator class")
public class PolicyValidatorTest {


    @DisplayName("too big capacity => false")
    @Test
    public void testValidateCapacity_WhenTooBigCapacity_ShouldReturnFalse() {
        // arrange
        int capacity = 99_999;
        Boolean expectedResult = false;

        // act
        Boolean val = PolicyValidator.validateCapacity(capacity);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("too small capacity => false")
    @Test
    public void testValidateCapacity_WhenTooSmallCapacity_ShouldReturnFalse() {
        // arrange
        int capacity = 0;
        Boolean expectedResult = false;

        // act
        Boolean val = PolicyValidator.validateCapacity(capacity);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("normal capacity => true")
    @Test
    public void testValidateCapacity_WhenNormalCapacity_ShouldReturnTrue() {
        // arrange
        int capacity = 90;
        Boolean expectedResult = true;

        // act
        Boolean val = PolicyValidator.validateCapacity(capacity);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("too small window size => true")
    @Test
    public void testValidateWindowSize_WhenToSmallWindowSize_ShouldReturnFalse() {
        // arrange
        int windowSize = 2;
        Boolean expectedResult = false;

        // act
        Boolean val = PolicyValidator.validateWindowSize(windowSize);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("too large window size => false")
    @Test
    public void testValidateWindowSize_WhenToBigWindowSize_ShouldReturnFalse() {
        // arrange
        int windowSize = 200_000;
        Boolean expectedResult = false;

        // act
        Boolean val = PolicyValidator.validateWindowSize(windowSize);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("normal window size => true")
    @Test
    public void testValidateWindowSize_WhenNormalWindowSize_ShouldReturnTrue() {
        // arrange
        int windowSize = 60;
        Boolean expectedResult = true;

        // act
        Boolean val = PolicyValidator.validateWindowSize(windowSize);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("too many policies => false")
    @Test
    public void testValidatePolicyNumber_WhenTooManyPolicies_ShouldReturnFalse() {
        // arrange
        int policyNumber = 991;
        Boolean expectedResult = false;

        // act
        Boolean val = PolicyValidator.validatePolicyNumber(policyNumber);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }

    @DisplayName("normal number of policies => true")
    @Test
    public void testValidatePolicyNumber_WhenNormalNumberOfPolicies_ShouldReturnTrue() {
        // arrange
        int policyNumber = 6;
        Boolean expectedResult = true;

        // act
        Boolean val = PolicyValidator.validatePolicyNumber(policyNumber);

        // assert
        Assertions.assertEquals(val, expectedResult);
    }
}
