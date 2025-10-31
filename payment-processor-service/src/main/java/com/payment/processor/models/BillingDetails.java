package com.payment.processor.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Model representing Billing/credit card information sent from the client.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
@Entity
@Table(name = "billing_details")
public class BillingDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String address;
    private String city;
    private String state;
    private Integer zip;
    private String country;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "expiration_date")
    private String expirationDate;

    private Integer cvv;

    @Column(name = "amount_requested")
    private Integer amountRequested;

    public BillingDetails() {}

    // getters / setters ...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public Integer getZip() { return zip; }
    public void setZip(Integer zip) { this.zip = zip; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }
    public String getExpiryDate() { return expirationDate; }
    public void setExpiryDate(String expirationDate) { this.expirationDate = expirationDate; }
    public Integer getCvv() { return cvv; }
    public void setCvv(Integer cvv) { this.cvv = cvv; }
    public Integer getAmountRequested() { return amountRequested; }
    public void setAmountRequested(Integer amountRequested) { this.amountRequested = amountRequested; }
}
