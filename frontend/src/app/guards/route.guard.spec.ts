import { TestBed } from '@angular/core/testing';

import { RouteGuard } from './route.guard';

describe('RouteGuardService', () => {
  let service: RouteGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});