package com.example.library.dao;

import com.example.library.entity.Book;
import com.example.library.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findReviewByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    Review findReviewByUserEmailAndBookId(String userEmail, Long bookId);

    @Modifying
    @Query("delete from Review c where c.bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
