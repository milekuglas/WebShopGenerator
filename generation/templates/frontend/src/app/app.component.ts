import { Component, OnInit } from '@angular/core';

import { AuthService } from './auth/shared/auth.service';
import { Category } from './category/shared/category.model';
import { CategoryService } from './category/shared/category.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  interpolation: ["{a", "a}"]
})
export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private categoryService: CategoryService
  ) {}
  categories = [];
  catDict = {};

  ngOnInit() {
    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
      this.categories.forEach(cat => {
        cat.subcategories = [];
        this.catDict[cat.id] = cat;
      });
      this.categories.forEach(cat => {
        if (cat.superCategoryId) {
          if (this.catDict[cat.superCategoryId].subcategories === undefined) {
            this.catDict[cat.superCategoryId].subcategories = [];
          }
          this.catDict[cat.superCategoryId].subcategories.push(cat);
        }
      });
      this.categories = [];
      for (const [key, value] of Object.entries(this.catDict)) {
        this.categories.push(value);
      }
    });
  }

  getCatDictSize() {
    return Object.keys(this.catDict).length;
  }

  user() {
    return this.authService.user;
  }

  logout() {
    this.authService.logout();
  }
}
