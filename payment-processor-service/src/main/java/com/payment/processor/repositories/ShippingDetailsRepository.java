package com.payment.processor.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.processor.models.ShippingDetails;
/**
 * An interface for database actions concerning ShippingDetails class.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, UUID> {
}
