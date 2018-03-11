import {Component, OnInit} from '@angular/core';
import {OrderService} from '../shared/order.service';
import {OrderItemService} from '../shared/order-item.service';
import {ProductService} from '../../product/shared/product.service';
import {OrderItemProduct} from '../shared/order-item-product.model';
import { MatTableDataSource } from '@angular/material';
import {Order} from '../shared/order.model';

@Component({
  selector: 'app-shopping-history',
  templateUrl: './shopping-history.component.html',
  styleUrls: ['./shopping-history.component.css']
})
export class ShoppingHistoryComponent implements OnInit {

  orders: [Order];
  dataSources: [MatTableDataSource<OrderItemProduct>];

  displayedColumns = [
    'name',
    'manufacturer',
    'description',
    'quantity',
    'price',
    'total'
  ];

  constructor(
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private productService: ProductService
  ) {}

  ngOnInit() {

    this.orderService.get().subscribe(orders => {
      for (const order of orders) {
        order.orderItemProducts = [];
        this.orderItemService.get(order.id).subscribe(orderItems => {
          for (const orderItem of orderItems) {
            this.productService.getProduct(orderItem.productId).subscribe(product => {
              const orderItemProduct = new OrderItemProduct();
              orderItemProduct.orderItem = orderItem;
              orderItemProduct.product = product;
              order.orderItemProducts.push(orderItemProduct);
            });
          }
        });
      }
      this.orders = orders;
      setTimeout(() => {
        let sources: [MatTableDataSource<OrderItemProduct>] = [];
        for (const order of this.orders) {
          sources.push(new MatTableDataSource<OrderItemProduct>(order.orderItemProducts));
        }
        this.dataSources = sources;
      }, 500);
    });
  }
}
