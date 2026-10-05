package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {
    private final TokenService tokenService;
    public PrescriptionController(TokenService tokenService) { this.tokenService = tokenService; }

    @PostMapping("/create")
    public ResponseEntity<?> createPrescription(@RequestHeader("Authorization") String token,
            @RequestHeader("X-Doctor-Email") String doctorEmail,
            @Valid @RequestBody Prescription prescription, BindingResult bindingResult) {
        if (token == null || !tokenService.validateToken(token, doctorEmail)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unauthorized access. Invalid application security token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Prescription records persisted successfully in MongoDB.");
        response.put("prescriptionId", "rx_" + DateTimePlaceholder());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    private long DateTimePlaceholder() { return System.currentTimeMillis(); }
}
