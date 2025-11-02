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
    /*
     * Backend endpoint (matches server-side controller mapping)
     */
    url = '/payment-processor/v1';

    /*
    * Headers specificying content type and initial mock up of auth token.
    */ 
    headers = new HttpHeaders()
      .set('Authorization', 'Bearer authToken123')
      .set('Content-Type', 'application/json');

  testShippingDetails = {
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@example.com',
    phone: '555-123-4567',
    address: '123 Main St',
    city: 'Springfield',
    state: 'IL',
    zip: '62704',
    country: 'USA',
  };

  testBillingDetails = {
    firstName: 'John',
    lastName: 'Doe',
    address: '123 Main St',
    city: 'Springfield',
    state: 'IL',
    zip: '62704',
    country: 'USA',
    cardNumber: '1111-1111-1111-1111',
    cardName: 'John Doe',
    expirationDate: '12/25',
    cvv: '123',
    amountRequested: 49.99,
  };

  formModel: any = {
    shippingFirstName: '',
    shippingLastName: '',
    shippingEmail: '',
    shippingPhone: '',
    shippingAddress: '',
    shippingCity: '',
    shippingState: '',
    shippingZip: '',
    shippingCountry: '',
    billingFirstName: '',
    billingLastName: '',
    billingAddress: '',
    billingCity: '',
    billingState: '',
    billingZip: '',
    billingCountry: '',
    cardNumber: '',
    cardName: '',
    expirationDate: '',
    cvv: '',
    amountRequested: 0,
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
      this.formModel.billingFirstName = this.formModel.shippingFirstName;
      this.formModel.billingLastName = this.formModel.shippingLastName;
      this.formModel.billingAddress = this.formModel.shippingAddress;
      this.formModel.billingCity = this.formModel.shippingCity;
      this.formModel.billingState = this.formModel.shippingState;
      this.formModel.billingZip = this.formModel.shippingZip;
      this.formModel.billingCountry = this.formModel.shippingCountry;
    } else {
      this.formModel.billingFirstName = '';
      this.formModel.billingLastName = '';
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
      this.formModel.shippingFirstName = this.testShippingDetails.firstName;
      this.formModel.shippingLastName = this.testShippingDetails.lastName;
      this.formModel.shippingEmail = this.testShippingDetails.email;
      this.formModel.shippingPhone = this.testShippingDetails.phone;
      this.formModel.shippingAddress = this.testShippingDetails.address;
      this.formModel.shippingCity = this.testShippingDetails.city;
      this.formModel.shippingState = this.testShippingDetails.state;
      this.formModel.shippingZip = this.testShippingDetails.zip;
      this.formModel.shippingCountry = this.testShippingDetails.country;

      this.formModel.billingFirstName = this.testBillingDetails.firstName;
      this.formModel.billingLastName = this.testBillingDetails.lastName;
      this.formModel.billingAddress = this.testBillingDetails.address;
      this.formModel.billingCity = this.testBillingDetails.city;
      this.formModel.billingState = this.testBillingDetails.state;
      this.formModel.billingZip = this.testBillingDetails.zip;
      this.formModel.billingCountry = this.testBillingDetails.country;

      this.formModel.cardNumber = this.testBillingDetails.cardNumber;
      this.formModel.cardName = this.testBillingDetails.cardName;
      this.formModel.expirationDate = this.testBillingDetails.expirationDate;
      this.formModel.cvv = this.testBillingDetails.cvv;
      this.formModel.amountRequested =
        this.testBillingDetails.amountRequested;
    } else {
      this.formModel = {
        shippingFirstName: '',
        shippingLastName: '',
        shippingEmail: '',
        shippingPhone: '',
        shippingAddress: '',
        shippingCity: '',
        shippingState: '',
        shippingZip: '',
        shippingCountry: '',
        billingFirstName: '',
        billingLastName: '',
        billingAddress: '',
        billingCity: '',
        billingState: '',
        billingZip: '',
        billingCountry: '',
        cardNumber: '',
        cardName: '',
        expirationDate: '',
        cvv: '',
        amountRequested: 0,
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
      firstName: v.shippingFirstName || '',
      lastName: v.shippingLastName || '',
      email: v.shippingEmail || '',
      phone: v.shippingPhone || '',
      address: v.shippingAddress || '',
      city: v.shippingCity || '',
      state: v.shippingState || '',
      zip: v.shippingZip || '',
      country: v.shippingCountry || '',
    };

    const billingDetails = {
      firstName:
        v.billingFirstName ||
        (this.sameAsShipping ? shippingDetails.firstName : ''),
      lastName:
        v.billingLastName ||
        (this.sameAsShipping ? shippingDetails.lastName : ''),
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
      expirationDate: v.expirationDate || '',
      cvv: v.cvv || '',
      amountRequested: v.amountRequested || 0,
    };

    const payload = {
      shippingDetails,
      billingDetails,
    };

    let headers = this.headers;

    this.http.post(this.url + "/make-payment", payload, { headers }).subscribe({
      next: (res) => {
        console.log('Payment authorization response:', res);
      },
      error: (err) => {
        console.error('Payment authorization failed:', err);
      },
    });
  }

  // Manually initiate payment settlement process.
  settleAuthorizedPayments() {
    let headers = this.headers;

    this.http.get(this.url + "/settle-payments", { headers }).subscribe({
      next: (res) => {
        console.log('Payment Settlement Successful:');
        console.log(res);
      },
      error: (err) => {
        console.error('Payment Settlement Failed:', err);
      },
    });
  }
}
