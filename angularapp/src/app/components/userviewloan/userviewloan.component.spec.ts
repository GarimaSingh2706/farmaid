import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserviewloanComponent } from './userviewloan.component';

describe('UserviewloanComponent', () => {
  let component: UserviewloanComponent;
  let fixture: ComponentFixture<UserviewloanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserviewloanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserviewloanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
