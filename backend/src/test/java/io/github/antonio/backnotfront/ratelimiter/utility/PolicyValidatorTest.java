package io.github.antonio.backnotfront.ratelimiter.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//@DataJpaTest
public class PolicyValidatorTest {


    @Test
    public void validateCapacity_TooBigCapacity_False(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateCapacity(99_999);
        // assert
        Assertions.assertEquals(val, false);
    }
    @Test
    public void validateCapacity_TooSmallCapacity_False(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateCapacity(0);
        // assert
        Assertions.assertEquals(val, false);
    }
    @Test
    public void validateCapacity_NormalCapacity_True(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateCapacity(90);
        // assert
        Assertions.assertEquals(val, true);
    }
    @Test
    public void validateWindowSize_ToSmallWindowSize_False(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateWindowSize(2);
        // assert
        Assertions.assertEquals(val, false);
    }
    @Test
    public void validateWindowSize_ToBigWindowSize_False(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateWindowSize(200_000);
        // assert
        Assertions.assertEquals(val, false);
    }
    @Test
    public void validateWindowSize_NormalWindowSize_True(){
        // arrange
        // act
        Boolean val = PolicyValidator.validateWindowSize(60);
        // assert
        Assertions.assertEquals(val, true);
    }
    @Test
    public void validatePolicyNumber_TooManyPolicies_False(){
        // arrange
        // act
        Boolean val = PolicyValidator.validatePolicyNumber(991);
        // assert
        Assertions.assertEquals(val, false);
    }
    @Test
    public void validatePolicyNumber_NormalNumberOfPolicies_True(){
        // arrange
        // act
        Boolean val = PolicyValidator.validatePolicyNumber(6);
        // assert
        Assertions.assertEquals(val, true);
    }
}
