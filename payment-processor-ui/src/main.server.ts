import { bootstrapApplication, BootstrapContext } from '@angular/platform-browser';
import { Payment } from './payment/payment';
import { config } from './app/app.config.server';

const bootstrap = (context: BootstrapContext) => bootstrapApplication(Payment, config, context);

export default bootstrap;
