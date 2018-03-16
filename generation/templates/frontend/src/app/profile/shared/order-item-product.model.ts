import {Product} from '../../product/shared/product.model';
import {OrderItem} from './order-item.model';

export class OrderItemProduct {
  product: Product;
  orderItem: OrderItem;

  constructor() {}
}
