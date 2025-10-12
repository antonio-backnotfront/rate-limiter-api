package io.github.antonio.backnotfront.ratelimiter.repository;

import io.github.antonio.backnotfront.ratelimiter.model.RateLimitCacheEntry;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.RedisRateLimiterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RateLimiterRepository Tests")
public class RateLimiterRepositoryTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    private RedisRateLimiterRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RedisRateLimiterRepository(redisTemplate);
    }

    @Test
    @DisplayName("getCacheEntry should return existing cache entry")
    public void getCacheEntry_WhenEntryExists_ShouldReturnCacheEntry() {
        // Arrange
        String key = "test-key";
        Integer expectedTokens = 10;
        Long expectedLastRequest = System.currentTimeMillis();
        
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.get(key, "tokenCount")).thenReturn(expectedTokens);
        when(hashOperations.get(key, "lastRequest")).thenReturn(expectedLastRequest);

        // Act
        RateLimitCacheEntry result = repository.getCacheEntry(key);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTokens, result.getTokens());
        assertEquals(expectedLastRequest, result.getLastRequest());
    }

    @Test
    @DisplayName("getCacheEntry should return null when entry does not exist")
    public void getCacheEntry_WhenEntryDoesNotExist_ShouldReturnNull() {
        // Arrange
        String key = "non-existent-key";
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.get(key, "tokenCount")).thenReturn(null);
        when(hashOperations.get(key, "lastRequest")).thenReturn(null);

        // Act
        RateLimitCacheEntry result = repository.getCacheEntry(key);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("putCacheEntry should store entry with expiration")
    public void putCacheEntry_ShouldStoreEntryWithExpiration() {
        // Arrange
        String key = "test-key";
        Integer tokenCount = 5;
        Duration duration = Duration.ofMinutes(1);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        repository.putCacheEntry(key, tokenCount, duration);

        // Assert
        verify(hashOperations).put(key, "tokenCount", tokenCount);
        verify(redisTemplate).expire(key, duration);
    }

    @Test
    @DisplayName("incrementToken should increment token count by 1")
    public void incrementToken_ShouldIncrementByOne() {
        // Arrange
        String key = "test-key";
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        repository.incrementToken(key);

        // Assert
        verify(hashOperations).increment(key, "tokenCount", 1);
    }

    @Test
    @DisplayName("decrementToken should decrement token count by 1")
    public void decrementToken_ShouldDecrementByOne() {
        // Arrange
        String key = "test-key";
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        repository.decrementToken(key);

        // Assert
        verify(hashOperations).increment(key, "tokenCount", -1);
    }

    @Test
    @DisplayName("incrementTokenBy should increment token count by specified value")
    public void incrementTokenBy_ShouldIncrementBySpecifiedValue() {
        // Arrange
        String key = "test-key";
        Integer value = 5;
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        repository.incrementTokenBy(key, value);

        // Assert
        verify(hashOperations).increment(key, "tokenCount", value);
    }

    @Test
    @DisplayName("decrementTokenBy should decrement token count by specified value")
    public void decrementTokenBy_ShouldDecrementBySpecifiedValue() {
        // Arrange
        String key = "test-key";
        Integer value = 3;
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        repository.decrementTokenBy(key, value);

        // Assert
        verify(hashOperations).increment(key, "tokenCount", -value);
    }

    @Test
    @DisplayName("updateLastRequest should update last request timestamp")
    public void updateLastRequest_ShouldUpdateTimestamp() {
        // Arrange
        String key = "test-key";
        Long timestamp = System.currentTimeMillis();

        // Act
        repository.updateLastRequest(key, timestamp);

        // Assert
        verify(hashOperations).put(key, "lastRequest", timestamp);
    }

    @Test
    @DisplayName("modifyCacheEntryExpiration should update expiration")
    public void modifyCacheEntryExpiration_ShouldUpdateExpiration() {
        // Arrange
        String key = "test-key";
        Duration duration = Duration.ofMinutes(5);

        // Act
        repository.modifyCacheEntryExpiration(key, duration);

        // Assert
        verify(redisTemplate).expire(key, duration);
    }
}
