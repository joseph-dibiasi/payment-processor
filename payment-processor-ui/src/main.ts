import { bootstrapApplication } from '@angular/platform-browser';
import { Payment } from './payment/payment';
import { appConfig } from './app/app.config';

bootstrapApplication(Payment, appConfig)
  .catch((err) => console.error(err));
