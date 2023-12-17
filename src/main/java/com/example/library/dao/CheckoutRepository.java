package com.example.library.dao;

import com.example.library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findCheckoutByUserEmailAndBookId(String userEmail, Long bookId);
    List<Checkout> findBookByUserEmail(String userEmail);
}
