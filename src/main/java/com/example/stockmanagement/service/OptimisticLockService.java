package com.example.stockmanagement.service;

import com.example.stockmanagement.domain.Stock;
import com.example.stockmanagement.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Optimistic Lock은 별도의 Lock을 잡지 않고 race condition 문제가 발생할 때 version 전략을 사용하여 해결하기 때문에
 * Pessimistic Lock보다 성능상 이점이 있다.
 */
@Service
@RequiredArgsConstructor
public class OptimisticLockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

}
