import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminnavComponent } from './components/adminnav/adminnav.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { CreateloanComponent } from './components/createloan/createloan.component';
import { ErrorComponent } from './components/error/error.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoanformComponent } from './components/loanform/loanform.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RequestedloanComponent } from './components/requestedloan/requestedloan.component';
import { SignupComponent } from './components/signup/signup.component';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { UserappliedloanComponent } from './components/userappliedloan/userappliedloan.component';
import { UsernavComponent } from './components/usernav/usernav.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { UserviewloanComponent } from './components/userviewloan/userviewloan.component';
import { ViewloanComponent } from './components/viewloan/viewloan.component';
import { AdmineditloanComponent } from './components/admineditloan/admineditloan.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { ServicesComponent } from './components/services/services.component';
import { SupportComponent } from './components/support/support.component';
import { PrivacyPolicyComponent } from './components/privacy-policy/privacy-policy.component';
import { TermsOfServiceComponent } from './components/terms-of-service/terms-of-service.component';

@NgModule({
  declarations: [
    AppComponent,
    AdmineditloanComponent,
    AdminnavComponent,
    AdminviewfeedbackComponent,
    CreateloanComponent,
    ErrorComponent,
    HomePageComponent,
    LoanformComponent,
    LoginComponent,
    NavbarComponent,
    RequestedloanComponent,
    SignupComponent,
    UseraddfeedbackComponent,
    UserappliedloanComponent,
    UsernavComponent,
    UserviewfeedbackComponent,
    UserviewloanComponent,
    ViewloanComponent,
    FooterComponent,
    AboutUsComponent,
    ServicesComponent,
    SupportComponent,
    PrivacyPolicyComponent,
    TermsOfServiceComponent,


  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
