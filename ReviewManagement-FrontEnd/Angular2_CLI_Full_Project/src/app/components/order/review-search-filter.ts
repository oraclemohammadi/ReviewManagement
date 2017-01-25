import { Component, EventEmitter, Output, Input } from '@angular/core';

@Component({
  selector: 'search-filters',
  templateUrl: './review-search-filter.html'
  //,  styleUrls: ['./app/search-filters/search_filters.component.css']
})
export class ReviewSearchFilters{
  filters: any = {};

  @Output() filtersChange = new EventEmitter();

  @Input() merchants: string[];

  activeFilterCount = 0;

  private updateStatus(e: any) {
    const rating = e.target.name;
    const toggle = e.target.checked;

    if (!this.filters.rating) {
      this.filters.rating = [];
    }
    if (toggle) {
      this.filters.rating.push(rating)
    } else {
      this.filters.rating.splice(this.filters.rating.indexOf(rating), 1);
    }
    console.log(this.filters.rating);
  }

  private filtersChanged() {
      this.filtersChange.emit(this.filters);  //inform reviewgrid that filter changed
    // Count active filters.
    this.activeFilterCount = ['after', 'before', 'merchant', 'asin', 'max', 'rating'].filter((field) => {
      return this.filters[field] && this.filters[field].length > 0;
    }).length;
    console.log('active filed count'+this.activeFilterCount);
  }
}
