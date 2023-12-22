package com.example.library.dao;

import com.example.library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findCheckoutByUserEmailAndBookId(String userEmail, Long bookId);
    List<Checkout> findBookByUserEmail(String userEmail);
    @Modifying
    @Query("delete from Checkout c where c.bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
