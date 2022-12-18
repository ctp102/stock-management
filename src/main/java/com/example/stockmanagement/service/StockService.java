package com.example.stockmanagement.service;

import com.example.stockmanagement.domain.Stock;
import com.example.stockmanagement.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    /**
     * race condition 문제가 발생하는 메서드
     *
     * @param id
     * @param quantity
     */
    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    /**
     * synchronized 키워드를 사용해서 race condition 해결
     * 테스트를 위해 @Transactional 제거
     *
     * @param id
     * @param quantity
     */
    public synchronized void decreaseV2(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

}
