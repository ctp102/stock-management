package com.example.stockmanagement.service;

import com.example.stockmanagement.domain.Stock;
import com.example.stockmanagement.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticLockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id); // mysql에 lock을 걸고 데이터를 가지고 옴
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

}
