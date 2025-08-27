import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { CreateloanComponent } from './components/createloan/createloan.component';
import { ViewloanComponent } from './components/viewloan/viewloan.component';
import { AdmineditloanComponent } from './components/admineditloan/admineditloan.component';
import { RequestedloanComponent } from './components/requestedloan/requestedloan.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { AuthGuard } from './guards/auth.guard';
import { UserviewloanComponent } from './components/userviewloan/userviewloan.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { UserappliedloanComponent } from './components/userappliedloan/userappliedloan.component';
import { LoanformComponent } from './components/loanform/loanform.component';
import { ErrorComponent } from './components/error/error.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { ServicesComponent } from './components/services/services.component';
import { PrivacyPolicyComponent } from './components/privacy-policy/privacy-policy.component';
import { TermsOfServiceComponent } from './components/terms-of-service/terms-of-service.component';
import { SupportComponent } from './components/support/support.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  { path: 'about', component: AboutUsComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'contact', component: SupportComponent },
  { path: 'privacy', component: PrivacyPolicyComponent },
  { path: 'terms', component: TermsOfServiceComponent },

  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomePageComponent },
  { path: 'add-loan', component: CreateloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'view-loan', component: ViewloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'edit-loan/:id', component: AdmineditloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'requested-loan', component: RequestedloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'feedbacks', component: AdminviewfeedbackComponent, canActivate: [AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'home', component: HomePageComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: 'view-loans', component: UserviewloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: 'apply-loan/:loanId', component: LoanformComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: 'applied-loan', component: UserappliedloanComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: 'post-feedback', component: UseraddfeedbackComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: 'my-feedbacks', component: UserviewfeedbackComponent, canActivate: [AuthGuard], data: { expectedRole: 'USER' } },
  { path: '**', component: ErrorComponent }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
