import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'payment',
  imports: [FormsModule],
  templateUrl: './payment.html',
  styleUrl: './payment.scss',
  standalone: true,
})
export class Payment {
  title = 'payment-processor';
  defaultTestData = false;
  sameAsShipping = false;

  testShippingDetails = {
    fullName: 'John Doe',
    email: 'john.doe@example.com',
    phone: '555-123-4567',
    address: '123 Main St',
    city: 'Springfield',
    state: 'IL',
    zip: '62704',
    country: 'USA',
  };

  testBillingDetails = {
    fullName: 'John Doe',
    address: '123 Main St',
    city: 'Springfield',
    state: 'IL',
    zip: '62704',
    country: 'USA',
    cardNumber: '1111 1111 1111 1111',
    cardName: 'John Doe',
    expiryDate: '12/25',
    cvv: '123',
    paymentRequested: 49.99,
  };

  formModel: any = {
    shippingFullName: '',
    shippingEmail: '',
    shippingPhone: '',
    shippingAddress: '',
    shippingCity: '',
    shippingState: '',
    shippingZip: '',
    shippingCountry: '',
    billingFullName: '',
    billingAddress: '',
    billingCity: '',
    billingState: '',
    billingZip: '',
    billingCountry: '',
    cardNumber: '',
    cardName: '',
    expiryDate: '',
    cvv: '',
    paymentRequested: 0,
  };

  /*
   * Standlone component means we are injecting http client directly.
   * As the application grows, this would be consolidating into a service handling API calls.
   */
  constructor(private http: HttpClient) {}

  /*
   * Billing address can be quickly filled out from shipping address.
   */
  onSameAsShippingChange() {
    if (this.sameAsShipping) {
      this.formModel.billingFullName = this.formModel.shippingFullName;
      this.formModel.billingAddress = this.formModel.shippingAddress;
      this.formModel.billingCity = this.formModel.shippingCity;
      this.formModel.billingState = this.formModel.shippingState;
      this.formModel.billingZip = this.formModel.shippingZip;
      this.formModel.billingCountry = this.formModel.shippingCountry;
    } else {
      this.formModel.billingFullName = '';
      this.formModel.billingAddress = '';
      this.formModel.billingCity = '';
      this.formModel.billingState = '';
      this.formModel.billingZip = '';
      this.formModel.billingCountry = '';
    }
  }

  /*
   * Test data for demonstration purposes.
   */
  loadDefaultData() {
    if (this.defaultTestData) {
      this.sameAsShipping = true;
      this.formModel.shippingFullName = this.testShippingDetails.fullName;
      this.formModel.shippingEmail = this.testShippingDetails.email;
      this.formModel.shippingPhone = this.testShippingDetails.phone;
      this.formModel.shippingAddress = this.testShippingDetails.address;
      this.formModel.shippingCity = this.testShippingDetails.city;
      this.formModel.shippingState = this.testShippingDetails.state;
      this.formModel.shippingZip = this.testShippingDetails.zip;
      this.formModel.shippingCountry = this.testShippingDetails.country;

      this.formModel.billingFullName = this.testBillingDetails.fullName;
      this.formModel.billingAddress = this.testBillingDetails.address;
      this.formModel.billingCity = this.testBillingDetails.city;
      this.formModel.billingState = this.testBillingDetails.state;
      this.formModel.billingZip = this.testBillingDetails.zip;
      this.formModel.billingCountry = this.testBillingDetails.country;

      this.formModel.cardNumber = this.testBillingDetails.cardNumber;
      this.formModel.cardName = this.testBillingDetails.cardName;
      this.formModel.expiryDate = this.testBillingDetails.expiryDate;
      this.formModel.cvv = this.testBillingDetails.cvv;
      this.formModel.paymentRequested =
        this.testBillingDetails.paymentRequested;
    } else {
      this.formModel = {
        shippingFullName: '',
        shippingEmail: '',
        shippingPhone: '',
        shippingAddress: '',
        shippingCity: '',
        shippingState: '',
        shippingZip: '',
        shippingCountry: '',
        billingFullName: '',
        billingAddress: '',
        billingCity: '',
        billingState: '',
        billingZip: '',
        billingCountry: '',
        cardNumber: '',
        cardName: '',
        expiryDate: '',
        cvv: '',
        paymentRequested: 0,
      };
      this.sameAsShipping = false;
    }
  }

  // Called from the template-driven form on submit
  onSubmit(form: NgForm) {
    if (!form || !form.valid) {
      console.warn('Form is invalid or not passed to onSubmit', form);
      return;
    }

    const v = this.formModel || form.value || {};

    const shippingDetails = {
      fullName: v.shippingFullName || '',
      email: v.shippingEmail || '',
      phone: v.shippingPhone || '',
      address: v.shippingAddress || '',
      city: v.shippingCity || '',
      state: v.shippingState || '',
      zip: v.shippingZip || '',
      country: v.shippingCountry || '',
    };

    const billingDetails = {
      fullName:
        v.billingFullName ||
        (this.sameAsShipping ? shippingDetails.fullName : ''),
      address:
        v.billingAddress ||
        (this.sameAsShipping ? shippingDetails.address : ''),
      city: v.billingCity || (this.sameAsShipping ? shippingDetails.city : ''),
      state:
        v.billingState || (this.sameAsShipping ? shippingDetails.state : ''),
      zip: v.billingZip || (this.sameAsShipping ? shippingDetails.zip : ''),
      country:
        v.billingCountry ||
        (this.sameAsShipping ? shippingDetails.country : ''),
      cardNumber: v.cardNumber || '',
      cardName: v.cardName || '',
      expiryDate: v.expiryDate || '',
      cvv: v.cvv || '',
      paymentRequested: v.paymentRequested || 0,
    };

    const payload = {
      shippingDetails,
      billingDetails,
    };

    /*
     * Backend endpoint (matches server-side controller mapping)
     */
    const url = '/payment-processor/make-payment';

    /*
    * Headers specificying content type and initial mock up of auth token.
    */ 
    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer authToken123')
      .set('Content-Type', 'application/json');

    this.http.post(url, payload, { headers }).subscribe({
      next: (res) => {
        console.log('Payment authorization response:', res);
      },
      error: (err) => {
        console.error('Payment authorization failed:', err);
      },
    });
  }
}
