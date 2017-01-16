import { Component, OnInit, ElementRef , ViewChild, AfterViewInit, Input, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { PolymerElement } from '@vaadin/angular2-polymer';



@Component({
  templateUrl: './user-layout.component.html',
  styleUrls: ['./user-layout.component.scss'],
  viewProviders: [PolymerElement('paper-dialog')]
})
export class UserLayoutComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('dialog') dialog: ElementRef;
  public self: Element;

  private bindIronOverlayOpened: EventListenerObject;

  constructor(private router: Router, elementRef: ElementRef) {
    this.self = elementRef.nativeElement;
  }

  public ngOnInit(): void {
  }

  public ngAfterViewInit(): void {
    this.bindIronOverlayOpened = this.ironOverlayOpend.bind(this);
    this.dialog.nativeElement.addEventListener('iron-overlay-opened', this.bindIronOverlayOpened);
  }

  public ironOverlayOpend(event: Event) {
    this.dialog.nativeElement.center();
    this.dialog.nativeElement.notifyResize();
  }

  public ngOnDestroy(): void {
    this.dialog.nativeElement.removeEventListener('iron-overlay-opened', this.bindIronOverlayOpened);
  }
}
