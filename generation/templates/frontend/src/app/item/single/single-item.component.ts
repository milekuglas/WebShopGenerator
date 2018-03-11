import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { {{ product.name|capitalize }}Service } from '../shared/{{ product.name|lower }}.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';
import { AuthService } from '../../auth/shared/auth.service';
import { MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-single-{{ product.name|lower }}',
  templateUrl: './single-{{ product.name|lower }}.component.html',
  styleUrls: ['./single-{{ product.name|lower }}.component.css']
})
export class Single{{ product.name|capitalize }}Component implements OnInit {

  {{ product.name|lower }}: any;
  scId: number;
  quantity = 1;

  constructor(
    private {{ product.name|lower }}Service: {{ product.name|capitalize }}Service,
    private authService: AuthService,
    private scService: ShoppingCartService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.scService.getCart(this.authService.user.id).subscribe(result => this.scId = Number(result));
    this.route.params.subscribe(params => {
      this.{{ product.name|lower }}Service.get{{ product.name|capitalize }}(+params['id']).subscribe(result => {
        this.{{ product.name|lower }} = result;
        console.log(this.{{ product.name|lower }});
      });
    });
  }

  addToCart() {
    this.{{ product.name|lower }}Service.addToCart({
      quantity: this.quantity,
      price: this.{{ product.name|lower }}.{{ base_product.name|lower }}.price,
      productId: this.{{ product.name|lower }}.{{ base_product.name|lower }}.id,
      shoppingCartId: this.scId,
    }).subscribe(result => {
      console.log(result);
      this.snackBar.open('Item added.', 'OK', {
        duration: 2000
      });
    });
  }

}
