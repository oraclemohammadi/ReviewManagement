import { Component,
         OnInit,
         ViewChild,
         ElementRef,
         Output,
         EventEmitter,
         AfterViewInit } from '@angular/core';
import { DataService } from '../dataservices/dataservice';
import { UserDto } from '../dto/User.DTO';

@Component({
  // tslint:disable-next-line:component-selector-prefix
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [ DataService ]
})
export class UserListComponent implements OnInit, AfterViewInit {
  private _users: UserDto[] = [];
  private _user: UserDto;
  private _filterUsers: UserDto[] = [];
  private _isLoading: boolean;
  private _errorMessage: string;

  @ViewChild('grid')
  public grid: ElementRef;
  @Output()
  public edit = new EventEmitter;

  public get Users(): UserDto[] {
    return this._users;
  }


  constructor(private _dataService: DataService) { }

  public ngOnInit(): void {
    this.getUsers();
  }

  public ngAfterViewInit(): void {
    this.grid.nativeElement.then(() => {
      this.gridReady(this.grid.nativeElement);
    });
  }

  private gridReady(grid: any): void {
    grid.cellClassGenerator = (cell: any) => {
      if (cell.columnName === 'status') {
        return 'status-' + cell.data.replace(/ /g, '-').toLowerCase();
      }
    };
  }

  private getUsers(): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      // TODO: show progress bar
      this._dataService.getRepository<UserDto>(UserDto)
      .getAll()
      .then(users => {
        this._users = users;
        resolve(true);
      })
      .catch(error => {
        // console.log(error);
        // TODO: Toast the message
        this._errorMessage = error;
        reject(error);
      });
    });
  }

  public selected(grid: any) {
    let selection = grid.selection.selected();
    if (selection.length === 1) {
      grid.selection.clear();
      grid.getItem(selection[0], (err: any, item: any) => {
        this.edit.emit(item);
      });
    }
  }

  refreshItems() {
    console.log('refreshing item');
    this.getUsers();
  }
}
