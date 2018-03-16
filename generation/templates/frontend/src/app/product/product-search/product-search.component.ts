import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../shared/product.service';
import 'rxjs/add/operator/switchMap';
import { Product } from '../shared/product.model';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ProductSearch } from './product-search.model';
import { CategoryService } from '../../category/shared/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css'],
  interpolation: ["{a", "a}"]
})
export class ProductSearchComponent implements OnInit {
  displayedColumns = [
  {% for property in product.properties %}
    {% if property.name != 'id' %}
    '{{ property.name }}'{{ "," if not loop.last }}
    {% endif %}
  {% endfor %}];
  searchQuery: ProductSearch = new ProductSearch();

  products: Product[] = [];
  dataSource: MatTableDataSource<Product>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  {% for property in product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int"
        and property.type.name != "Double" and property.type.name != "Float"
        and property.type.name != "String" %}
    {{property.type.name}}Enums = [
      {% for enum in property.type.values %}
      {name: '{{enum}}', value: '{{enum}}'},
      {% endfor %}];
    {% endif %}
  {% endfor %}

  search(seachQuery) {
    this.productService.getProductsSearch(seachQuery).subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  goToProductInfoPage(product: Product) {
    this.categoryService.getCategory(product.categoryId).subscribe(category => {
      const url = '/' + category.name.toLowerCase() + '/' + product.id;
      console.log(url);
      this.router.navigateByUrl(url);
    });
  }

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit() {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
