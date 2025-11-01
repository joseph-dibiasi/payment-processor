package com.payment.processor.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.processor.models.BillingDetails;
/**
 * An interface for database actions concerning BillingDetails class.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, UUID> {

	List<BillingDetails> findByAuthorizedAndSettledAndBillingDate(Boolean authorized, Boolean settled, LocalDateTime localDateTime);
}
