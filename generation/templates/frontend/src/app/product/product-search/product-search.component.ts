import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../shared/product.service';
import 'rxjs/add/operator/switchMap';
import { Product } from '../shared/product.model';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ProductSearch } from './product-search.model';

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

  search(seachQuery) {
    this.productService.getProductsSearch(seachQuery).subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
