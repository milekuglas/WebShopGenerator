'''
Created on Feb 9, 2018

@author: Milorad Vojnovic
'''

import os

from generation.generators.generator import Generator


class OrderItemGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_case_class_model(model)
        self.generate_table_model(model)
        self.generate_repository_model(model)
        self.generate_service_model(model)
        self.generate_dto_model(model)
        self.generate_controller_model(model)

    def generate_case_class_model(self, model):
        self.main_generator.generate("backend/app/model/case_class_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "model"),
                                     "OrderItem.scala",
                                     {"package": model.project.package, "base_product": model.base_product})

    def generate_table_model(self, model):
        self.main_generator.generate("backend/app/repository/table/repository_table_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository", "table"),
                                     "OrderItemTable.scala",
                                     {"package": model.project.package, "base_product": model.base_product})

    def generate_repository_model(self, model):
        self.main_generator.generate("backend/app/repository/repository_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository"),
                                     "OrderItemRepository.scala",
                                     {"package": model.project.package})

    def generate_service_model(self, model):
        self.main_generator.generate("backend/app/service/service_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "service"),
                                     "OrderItemService.scala",
                                     {"package": model.project.package})

    def generate_dto_model(self, model):
        self.main_generator.generate("backend/app/dto/dto_get_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "dto"),
                                     "GetOrderItem.scala",
                                     {"package": model.project.package, "base_product": model.base_product})
        self.main_generator.generate("backend/app/dto/dto_post_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "dto"),
                                     "PostOrderItem.scala",
                                     {"package": model.project.package, "base_product": model.base_product})

    def generate_controller_model(self, model):
        self.main_generator.generate("backend/app/controller/controller_order_item_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "controller"),
                                     "OrderItemController.scala",
                                     {"package": model.project.package})
