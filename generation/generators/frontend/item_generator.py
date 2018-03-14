import os

from generation.generators.generator import Generator
import re
from copy import copy
from ...utils import get_property_titles


class ItemGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        base_product_property_titles = get_property_titles(model.base_product.properties)
        for i in range(len(model.products)):
            product_property_titles = get_property_titles(model.products[i].properties)
            self.generate_item_model(model.base_product, model.products[i])
            self.generate_item_service(model.base_product, model.products[i])
            self.generate_item_module(model.base_product, model.products[i])

            self.generate_single_item_component(model.base_product, base_product_property_titles, model.products[i],
                                                product_property_titles)
            self.generate_search_item(model.base_product, base_product_property_titles, model.products[i],
                                      product_property_titles)

    def generate_single_item_component(self, base_product, base_product_property_titles, product,
                                       product_property_titles):
        base_product = copy(base_product)
        base_product.properties = list(zip(base_product_property_titles, base_product.properties))
        product = copy(product)
        product.properties = list(zip(product_property_titles, product.properties))
        self.main_generator.generate(
            "frontend/src/app/item/single/single-item.component.html",
            os.path.join("frontend", "src", "app", product.name.lower(), "single"),
            "single-" + product.name.lower() + ".component.html",
            {"base_product": base_product, "product": product}
        )
        self.main_generator.generate(
            "frontend/src/app/item/single/single-item.component.css",
            os.path.join("frontend", "src", "app", product.name.lower(), "single"),
            "single-" + product.name.lower() + ".component.css",
            {"product": product}
        )
        self.main_generator.generate(
            "frontend/src/app/item/single/single-item.component.ts",
            os.path.join("frontend", "src", "app", product.name.lower(), "single"),
            "single-" + product.name.lower() + ".component.ts",
            {"base_product": base_product, "product": product}
        )

    def generate_item_service(self, base_product, product):
        whole_product = copy(product)
        whole_product.properties = base_product.properties + product.properties
        self.main_generator.generate(
            "frontend/src/app/item/shared/item.service.ts",
            os.path.join("frontend", "src", "app", product.name.lower(), "shared"),
            product.name.lower() + ".service.ts",
            {"product": whole_product}
        )

    def generate_item_model(self, base_product, product):
        self.main_generator.generate(
            "frontend/src/app/item/shared/item.model.ts",
            os.path.join("frontend", "src", "app", product.name.lower(), "shared"),
            product.name.lower() + ".model.ts",
            {"base_product": base_product, "product": product}
        )

    def generate_item_module(self, base_product, product):
        self.main_generator.generate(
            "frontend/src/app/item/item.module.ts",
            os.path.join("frontend", "src", "app", product.name.lower()),
            product.name.lower() + ".module.ts",
            {"base_product": base_product, "product": product}
        )

    def generate_search_item(self, base_product, base_product_property_titles, product, product_property_titles):
        whole_product = copy(product)
        whole_product.properties = base_product.properties + product.properties

        self.main_generator.generate(
            "frontend/src/app/item/search/item-search.component.css",
            os.path.join("frontend", "src", "app", product.name.lower(), "search"),
            product.name.lower() + "-search.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/item/search/item-search.model.ts",
            os.path.join("frontend", "src", "app", product.name.lower(), "search"),
            product.name.lower() + "-search.model.ts",
            {"product": whole_product}
        )
        self.main_generator.generate(
            "frontend/src/app/item/search/item-search.component.ts",
            os.path.join("frontend", "src", "app", product.name.lower(), "search"),
            product.name.lower() + "-search.component.ts",
            {"product": whole_product}
        )

        base_product = copy(base_product)
        base_product.properties = list(zip(base_product_property_titles, base_product.properties))
        product = copy(product)
        product.properties = list(zip(product_property_titles, product.properties))

        self.main_generator.generate(
            "frontend/src/app/item/search/item-search.component.html",
            os.path.join("frontend", "src", "app", product.name.lower(), "search"),
            product.name.lower() + "-search.component.html",
            {"base_product": base_product, "product": product}
        )
