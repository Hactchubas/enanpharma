package com.ecommerce.enanpharma.service;

import com.ecommerce.enanpharma.dto.ServerStatusDTO;
import org.springframework.stereotype.Service;

@Service
public class PharmaService {

    public ServerStatusDTO getServerStatus() {
        return new ServerStatusDTO("Ok");
    }
}
