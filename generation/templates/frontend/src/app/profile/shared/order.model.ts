import {OrderItemProduct} from './order-item-product.model';

export class Order {
  id: number;
  totalPrice: number;
  userId: number;
  orderItemProducts: [OrderItemProduct];
}
