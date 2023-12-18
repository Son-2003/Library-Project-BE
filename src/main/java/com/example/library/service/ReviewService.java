package com.example.library.service;

import com.example.library.dao.BookRepository;
import com.example.library.dao.CheckoutRepository;
import com.example.library.dao.ReviewRepository;
import com.example.library.entity.Checkout;
import com.example.library.entity.Review;
import com.example.library.requestmodels.ReviewRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
        Review validateReview = reviewRepository.findReviewByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        if(validateReview != null){
            throw  new Exception("Review already created");
        }

        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString)
                    .orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long bookId){
        Review validateReview = reviewRepository.findReviewByUserEmailAndBookId(userEmail, bookId);
        if(validateReview != null){
            return true;
        }else {
            return false;
        }
    }
}
