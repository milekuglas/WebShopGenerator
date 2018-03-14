import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../product/shared/product.service';
import {OrderItemService} from '../shared/order-item.service';
import {OrderItemProduct} from '../shared/order-item-product.model';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  dataSource: MatTableDataSource<OrderItemProduct>;

  displayedColumns = [
    'name',
    'manufacturer',
    'description',
    'quantity',
    'price',
    'total'
  ];

  constructor(
    private orderItemService: OrderItemService,
    private productService: ProductService
  ) {}

  ngOnInit() {

    const orderItemProducts = [];
    this.orderItemService.getShoppingCartItems().subscribe(orderItems => {
      for (const orderItem of orderItems) {
        this.productService.getProduct(orderItem.productId).subscribe(product => {
          const orderItemProduct = new OrderItemProduct();
          orderItemProduct.orderItem = orderItem;
          orderItemProduct.product = product;
          orderItemProducts.push(orderItemProduct);
        });
      }
    });
    setTimeout(() => {
      this.dataSource = new MatTableDataSource<OrderItemProduct>(orderItemProducts);
    }, 500);
  }
}
