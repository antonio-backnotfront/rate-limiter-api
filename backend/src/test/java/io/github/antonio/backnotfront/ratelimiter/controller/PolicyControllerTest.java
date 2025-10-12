package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.dto.request.CreatePolicyRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.CreatePolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.service.PolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PolicyController Tests")
public class PolicyControllerTest {

    @Mock
    private PolicyService policyService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PolicyController policyController;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    @DisplayName("getPolicies should return list of policies for authenticated user")
    public void getPolicies_WhenCalled_ShouldReturnListOfPolicies() {
        // Arrange
        String userEmail = "test@example.com";
        when(authentication.getName()).thenReturn(userEmail);
        
        GetPolicyResponseDto policy1 = new GetPolicyResponseDto("1", "/api/test", 100, 60, userEmail);
        GetPolicyResponseDto policy2 = new GetPolicyResponseDto("2", "/api/test2", 50, 120, userEmail);
        List<GetPolicyResponseDto> expectedPolicies = Arrays.asList(policy1, policy2);
        
        when(policyService.getPoliciesByUserEmail(userEmail)).thenReturn(expectedPolicies);

        // Act
        ResponseEntity<List<GetPolicyResponseDto>> response = policyController.getPolicies();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPolicies, response.getBody());
        assertEquals(2, response.getBody().size());
        verify(policyService).getPoliciesByUserEmail(userEmail);
    }

    @Test
    @DisplayName("getPolicies should return empty list when user has no policies")
    public void getPolicies_WhenUserHasNoPolicies_ShouldReturnEmptyList() {
        // Arrange
        String userEmail = "test@example.com";
        when(authentication.getName()).thenReturn(userEmail);
        when(policyService.getPoliciesByUserEmail(userEmail)).thenReturn(List.of());

        // Act
        ResponseEntity<List<GetPolicyResponseDto>> response = policyController.getPolicies();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("getPolicy should return policy by id")
    public void getPolicy_WhenValidId_ShouldReturnPolicy() {
        // Arrange
        String policyId = "test-policy-id";
        GetPolicyResponseDto expectedPolicy = new GetPolicyResponseDto(
            policyId, "/api/test", 100, 60, "test@example.com"
        );
        when(policyService.getPolicyById(policyId)).thenReturn(expectedPolicy);

        // Act
        ResponseEntity<GetPolicyResponseDto> response = policyController.getPolicy(policyId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPolicy, response.getBody());
        assertEquals(policyId, response.getBody().id());
        verify(policyService).getPolicyById(policyId);
    }

    @Test
    @DisplayName("create should create new policy and return created response")
    public void create_WhenValidRequest_ShouldCreatePolicy() {
        // Arrange
        CreatePolicyRequestDto requestDto = new CreatePolicyRequestDto("/api/test", 100, 60);
        CreatePolicyResponseDto expectedResponse = new CreatePolicyResponseDto(
            "new-policy-id", "/api/test", 100, 60, "test@example.com"
        );
        when(policyService.createPolicy(requestDto)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = policyController.create(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        assertTrue(response.getHeaders().getLocation().toString().contains("/api/policy/new-policy-id"));
        verify(policyService).createPolicy(requestDto);
    }
}
