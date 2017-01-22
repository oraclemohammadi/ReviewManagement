package com.milo.amz.review.service;

public interface ContactReviewerService {
   public void sendEmail(String orderId,String buyerId,String marketPlaceId,String message);
}
