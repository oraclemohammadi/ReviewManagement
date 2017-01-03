package com.milo.amz.review.repository;

import com.milo.amz.review.domain.PaymentExecution;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PaymentExecution entity.
 */
@SuppressWarnings("unused")
public interface PaymentExecutionRepository extends JpaRepository<PaymentExecution,Long> {

}
