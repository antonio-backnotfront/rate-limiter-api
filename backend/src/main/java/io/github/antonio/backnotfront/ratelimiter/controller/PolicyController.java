package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.dto.request.CreatePolicyRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.CreatePolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GetPolicyResponseDto>> getPolicies(){
        List<GetPolicyResponseDto> list = service.getPoliciesByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetPolicyResponseDto> getPolicy(@PathVariable String id){
        GetPolicyResponseDto policy = service.getPolicyById(id);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreatePolicyRequestDto requestDto) {
        CreatePolicyResponseDto responseDto = service.createPolicy(requestDto);
        return ResponseEntity.created(URI.create("/api/policy/" + responseDto.id())).body(responseDto);
    }
}
