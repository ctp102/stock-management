package com.example.stockmanagement.repository;

import com.example.stockmanagement.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {




}
