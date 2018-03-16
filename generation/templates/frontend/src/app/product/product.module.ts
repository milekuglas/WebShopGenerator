import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductSearchComponent } from './product-search/product-search.component';
import { SharedModule } from '../shared/shared.module';
import { ProductService } from './shared/product.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [CommonModule, SharedModule, HttpClientModule, FormsModule, RouterModule],
  exports: [ProductSearchComponent],
  declarations: [ProductSearchComponent],
  providers: [ProductService]
})
export class ProductModule {}
