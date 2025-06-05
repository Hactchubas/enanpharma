package com.ecommerce.enanpharma.controller;

import com.ecommerce.enanpharma.dto.ServerStatusDTO;
import com.ecommerce.enanpharma.service.PharmaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmaController {

    private final PharmaService pharmaService;

    public PharmaController(PharmaService pharmaService) {
        this.pharmaService = pharmaService;
    }

    @GetMapping("/")
    public ServerStatusDTO index() {
        return pharmaService.getServerStatus();
    }
}
