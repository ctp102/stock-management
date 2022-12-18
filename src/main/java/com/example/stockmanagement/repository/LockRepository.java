package com.example.stockmanagement.repository;

import com.example.stockmanagement.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(
            @Param("key") String key
    );

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void release(@Param("key") String key
    );
}
