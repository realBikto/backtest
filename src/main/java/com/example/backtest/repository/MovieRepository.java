package com.example.backtest.repository;

import com.example.backtest.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value =
            "SELECT EXISTS ( " +
                    "   SELECT 1 " +
                    "   FROM movie " +
                    "   WHERE title = :title " +
                    ") "
    )
    boolean exists(String title);

    @Query(nativeQuery = true,
            value = "SELECT * FROM movie WHERE (:title IS NULL OR title ILIKE CONCAT('%', :title, '%')) AND :maxResults >= 0",
            countQuery = "SELECT count(*) FROM movie WHERE (:title IS NULL OR title ILIKE CONCAT('%', :title, '%')) LIMIT :maxResults"
    )
    Page<Movie> list(String title, Pageable pageable, Integer maxResults);
}
