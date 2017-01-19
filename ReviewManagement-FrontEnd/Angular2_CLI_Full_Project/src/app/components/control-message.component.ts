//I got this idea from this source :https://coryrylan.com/blog/
import { Component, Input } from '@angular/core';
import {  FormControl } from '@angular/forms';
import {ValidationService} from '../services/validation.service';

@Component(
    {
        selector:"control-message",
        template: `<div *ngIf="errorMessage !== null">{{errorMessage}}</div>`
    }
)


export class ControlMessagesComponent{
  @Input() control: FormControl;
  
  constructor(){

  }
  get errorMessage() {
    for (let propertyName in this.control.errors) {
      if (this.control.errors.hasOwnProperty(propertyName) && this.control.touched) {
        return ValidationService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]);
      }
    }
    
    return null;
  }
}