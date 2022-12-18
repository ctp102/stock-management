package com.example.stockmanagement.facade;

import com.example.stockmanagement.repository.LockRepository;
import com.example.stockmanagement.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final StockService stockService;
    private final LockRepository lockRepository;

    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockService.decreaseV3(id, quantity);
        } finally {
            lockRepository.release(id.toString());
        }
    }

}
