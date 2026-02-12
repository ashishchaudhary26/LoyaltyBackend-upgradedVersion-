package com.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PutMapping("/internal/users/{userId}/add-reward")
    void addReward(@PathVariable Long userId,
            @RequestParam Double amount);
}
