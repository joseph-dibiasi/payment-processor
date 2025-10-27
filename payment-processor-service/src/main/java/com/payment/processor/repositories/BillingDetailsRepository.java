package com.payment.processor.repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.processor.models.BillingDetails;
/**
 * An interface for database actions concerning BillingDetails class.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, UUID> {
}
