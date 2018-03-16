import { Component, OnInit, ViewChild } from '@angular/core';
import { {{product.name|capitalize}}Service } from '../shared/{{product.name|lower}}.service';
import 'rxjs/add/operator/switchMap';
import { {{product.name|capitalize}} } from '../shared/{{product.name|lower}}.model';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { {{product.name|capitalize}}Search } from './{{product.name|lower}}-search.model';

@Component({
  selector: 'app-{{product.name|lower}}-search',
  templateUrl: './{{product.name|lower}}-search.component.html',
  styleUrls: ['./{{product.name|lower}}-search.component.css'],
  interpolation: ["{a", "a}"]
})
export class {{product.name|capitalize}}SearchComponent implements OnInit {
  displayedColumns = [
  {% for property in product.properties %}
    {% if property.name != 'id' %}
    '{{ property.name }}'{{ "," if not loop.last }}
    {% endif %}
  {% endfor %}];
  searchQuery: {{product.name|capitalize}}Search = new {{product.name|capitalize}}Search();

  products: {{product.name|capitalize}}[] = [];
  dataSource: MatTableDataSource<{{product.name|capitalize}}>;
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
    this.{{product.name|lower}}Service.get{{product.name|capitalize}}sSearch(seachQuery).subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  constructor(private {{product.name|lower}}Service: {{product.name|capitalize}}Service) {}

  ngOnInit() {
    this.{{product.name|lower}}Service.get{{product.name|capitalize}}s().subscribe(products => {
      this.products = products;
      this.dataSource = new MatTableDataSource(this.products);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
