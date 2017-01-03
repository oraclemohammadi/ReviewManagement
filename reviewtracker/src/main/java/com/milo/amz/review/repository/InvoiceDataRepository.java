package com.milo.amz.review.repository;

import com.milo.amz.review.domain.InvoiceData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceData entity.
 */
@SuppressWarnings("unused")
public interface InvoiceDataRepository extends JpaRepository<InvoiceData,Long> {

}
