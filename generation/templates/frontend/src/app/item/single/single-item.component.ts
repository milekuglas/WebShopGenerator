import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { {{ product.name|capitalize }}Service } from '../shared/{{ product.name|lower }}.service';


@Component({
  selector: 'app-single-{{ product.name|lower }}',
  templateUrl: './single-{{ product.name|lower }}.component.html',
  styleUrls: ['./single-{{ product.name|lower }}.component.css']
})
export class Single{{ product.name|capitalize }}Component implements OnInit {

  {{ product.name|lower }}: any;

  constructor(
    private {{ product.name|lower }}Service: {{ product.name|capitalize }}Service,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.{{ product.name|lower }}Service.get{{ product.name|capitalize }}(+params['id']).subscribe(result => {
        this.{{ product.name|lower }} = result;
        console.log(this.{{ product.name|lower }});
      });
    });
  }

}
