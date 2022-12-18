package com.example.stockmanagement.facade;

import com.example.stockmanagement.service.OptimisticLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockService optimisticLockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {

        while (true) {
            try {
                optimisticLockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }

    }

}
