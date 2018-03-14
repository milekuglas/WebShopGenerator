import os

from generation.generators.generator import Generator
from copy import copy
from ...utils import get_property_titles


class ProductGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_product_module(model)
        self.generate_product_search_component(model)

    def generate_product_module(self, model):
        self.main_generator.generate(
            "frontend/src/app/product/product.module.ts",
            os.path.join("frontend", "src", "app", "product"),
            "product.module.ts",
            {}
        )

        self.main_generator.generate(
            "frontend/src/app/product/shared/product.model.ts",
            os.path.join("frontend", "src", "app", "product", "shared"),
            "product.model.ts",
            {"product": model.base_product}
        )

        self.main_generator.generate(
            "frontend/src/app/product/shared/product.service.ts",
            os.path.join("frontend", "src", "app", "product", "shared"),
            "product.service.ts",
            {"product": model.base_product}
        )

    def generate_product_search_component(self, model):
        product = copy(model.base_product)
        prop_titles = get_property_titles(product.properties)
        product.properties = list(zip(prop_titles, product.properties))
        self.main_generator.generate(
            "frontend/src/app/product/product-search/product-search.component.html",
            os.path.join("frontend", "src", "app", "product", "product-search"),
            "product-search.component.html",
            {"product": product}
        )
        self.main_generator.generate(
            "frontend/src/app/product/product-search/product-search.component.css",
            os.path.join("frontend", "src", "app", "product", "product-search"),
            "product-search.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/product/product-search/product-search.model.ts",
            os.path.join("frontend", "src", "app", "product", "product-search"),
            "product-search.model.ts",
            {"product": model.base_product}
        )
        self.main_generator.generate(
            "frontend/src/app/product/product-search/product-search.component.ts",
            os.path.join("frontend", "src", "app", "product", "product-search"),
            "product-search.component.ts",
            {"product": model.base_product}
        )



