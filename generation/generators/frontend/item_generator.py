import os

from generation.generators.generator import Generator
import re


class ItemGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_single_item_component(model)
        self.generate_item_service(model)
        self.generate_item_module(model)

    def generate_single_item_component(self, model):
        base_prop_titles = []
        for prop in model.base_product.properties:
            p = re.sub('(?!^)([A-Z][a-z]+)', r' \1', prop.name).split()
            p[0] = p[0].capitalize()
            base_prop_titles.append(' '.join(p))
        model.base_product.properties = zip(base_prop_titles, model.base_product.properties)
        for i in range(len(model.products)):
            prop_titles = []
            for prop in model.products[i].properties:
                p = re.sub('(?!^)([A-Z][a-z]+)', r' \1', prop.name).split()
                p[0] = p[0].capitalize()
                prop_titles.append(' '.join(p))
            model.products[i].properties = zip(prop_titles, model.products[i].properties)
            self.main_generator.generate(
                "frontend/src/app/item/single/single-item.component.html",
                os.path.join("frontend", "src", "app", model.products[i].name.lower(), "single"),
                "single-" + model.products[i].name.lower() + ".component.html",
                {"base_product": model.base_product, "product": model.products[i]}
            )
            self.main_generator.generate(
                "frontend/src/app/item/single/single-item.component.css",
                os.path.join("frontend", "src", "app", model.products[i].name.lower(), "single"),
                "single-" + model.products[i].name.lower() + ".component.css",
                {"product": model.products[i]}
            )
            self.main_generator.generate(
                "frontend/src/app/item/single/single-item.component.ts",
                os.path.join("frontend", "src", "app", model.products[i].name.lower(), "single"),
                "single-" + model.products[i].name.lower() + ".component.ts",
                {"base_product": model.base_product, "product": model.products[i]}
            )

    def generate_item_service(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate(
                "frontend/src/app/item/shared/item.service.ts",
                os.path.join("frontend", "src", "app", model.products[i].name.lower(), "shared"),
                model.products[i].name.lower() + ".service.ts",
                {"product": model.products[i]}
            )

    def generate_item_module(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate(
                "frontend/src/app/item/item.module.ts",
                os.path.join("frontend", "src", "app", model.products[i].name.lower()),
                model.products[i].name.lower() + ".module.ts",
                {"base_product": model.base_product, "product": model.products[i]}
            )
