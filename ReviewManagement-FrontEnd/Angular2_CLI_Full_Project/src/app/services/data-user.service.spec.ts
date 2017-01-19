/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DataUserService } from './data-user.service';

describe('Service: DataUser', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DataUserService]
    });
  });

  it('should ...', inject([DataUserService], (service: DataUserService) => {
    expect(service).toBeTruthy();
  }));
});
