/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AutoGuardService } from './auto-guard.service';

describe('Service: AutoGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AutoGuardService]
    });
  });

  it('should ...', inject([AutoGuardService], (service: AutoGuardService) => {
    expect(service).toBeTruthy();
  }));
});
