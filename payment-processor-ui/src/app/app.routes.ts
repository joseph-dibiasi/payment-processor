import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Payment } from '../payment/payment';

export const routes: Routes = [
  { path: 'payment-processor', component: Payment },
  { path: '**', redirectTo: 'payment-processor' } // Wildcard route for undefined paths
];

