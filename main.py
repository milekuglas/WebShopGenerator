'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''

import os
import shutil

from generation.generators.backend.enum_generator import EnumGenerator
from generation.generators.backend.order_generator import OrderGenerator
from generation.generators.backend.order_item_generator import OrderItemGenerator
from generation.generators.backend.shopping_cart_generator import ShoppingCartGenerator
from generation.generators.backend.user_generator import UserGenerator
from generation.generators.dsl_generator import DslGenerator
from parsers.parser import Parser
from root import root
from generation.generators.backend.conf_generator import ConfGenerator
from generation.generators.backend.controller_generator import ControllerGenerator
from generation.generators.backend.dto_generator import DTOGenerator
from generation.generators.backend.module_generator import ModuleGenerator
from generation.generators.backend.sbt_generator import SbtGenerator
from generation.generators.backend.service_generator import ServiceGenerator
from generation.generators.main_generator import MainGenerator
from generation.generators.backend.model_generator import ModelGenerator
from generation.generators.backend.repository_generator import RepositoryGenerator
from generation.generators.backend.table_generator import TableGenerator
from generation.generators.backend.category_generator import CategoryGenerator
from generation.generators.backend.jwt_generator import JWTGenerator
from generation.generators.backend.exception_generator import ExceptionGenerator
from generation.generators.frontend.base_app_generator import BaseAppGenerator
from generation.generators.frontend.user_generator import UserGenerator as UserModuleGenerator
from generation.generators.frontend.shopping_cart_generator import ShoppingCartGenerator as SCGenerator
from generation.generators.frontend.auth_generator import AuthGenerator
from generation.generators.frontend.home_generator import HomeGenerator
from generation.generators.frontend.starter_generator import StarterGenerator
from generation.generators.frontend.profile_generator import ProfileGenerator
from generation.generators.frontend.product_generator import ProductGenerator
from generation.generators.frontend.item_generator import ItemGenerator
from generation.generators.frontend.category_generator import CategoryGenerator as CategoryGeneratorFront


def delete_files(path):
    try:
        for (dirpath, dirnames, filenames) in os.walk(path):
            dirnames[:] = [d for d in dirnames if not d.startswith('.idea')]
            for dir in dirnames:
                path = os.path.join(dirpath, dir)
                delete_files(path)
            for filename in filenames:
                if '.manual.' not in filename:
                    os.remove(os.path.join(dirpath, filename))
    except Exception:
        pass


if __name__ == '__main__':
    path = './output'
    try:
        shutil.rmtree(path + '/frontend')
    except Exception:
        pass

    delete_files(path)

    dsl_generator = DslGenerator()
    dsl_generator.generate()
    parser = Parser()
    model = parser.parse(os.path.join(root, "metamodel"), 'scala-angular.tx', 'model.scan', True)
    main_generator = MainGenerator()
    model_generator = ModelGenerator(main_generator)
    table_generator = TableGenerator(main_generator)
    repository_generator = RepositoryGenerator(main_generator)
    service_generator = ServiceGenerator(main_generator)
    controller_generator = ControllerGenerator(main_generator)
    dto_generator = DTOGenerator(main_generator)
    jwt_generator = JWTGenerator(main_generator)
    module_generator = ModuleGenerator(main_generator)
    conf_generator = ConfGenerator(main_generator)
    sbt_generator = SbtGenerator(main_generator)
    category_generator = CategoryGenerator(main_generator)
    order_generator = OrderGenerator(main_generator)
    order_item_generator = OrderItemGenerator(main_generator)
    shopping_cart_generator = ShoppingCartGenerator(main_generator)
    user_generator = UserGenerator(main_generator)
    exception_generator = ExceptionGenerator(main_generator)
    enum_generator = EnumGenerator(main_generator)

    main_generator.add_generator(model_generator)
    main_generator.add_generator(repository_generator)
    main_generator.add_generator(service_generator)
    main_generator.add_generator(controller_generator)
    main_generator.add_generator(dto_generator)
    main_generator.add_generator(jwt_generator)
    main_generator.add_generator(module_generator)
    main_generator.add_generator(conf_generator)
    main_generator.add_generator(sbt_generator)
    main_generator.add_generator(category_generator)
    main_generator.add_generator(table_generator)
    main_generator.add_generator(order_generator)
    main_generator.add_generator(order_item_generator)
    main_generator.add_generator(shopping_cart_generator)
    main_generator.add_generator(user_generator)
    main_generator.add_generator(exception_generator)
    main_generator.add_generator(enum_generator)

    # Frontend

    base_app_generator = BaseAppGenerator(main_generator)
    auth_generator = AuthGenerator(main_generator)
    user_generator = UserModuleGenerator(main_generator)
    home_generator = HomeGenerator(main_generator)
    starter_generator = StarterGenerator(main_generator)
    profile_generator = ProfileGenerator(main_generator)
    product_generator = ProductGenerator(main_generator)
    item_generator = ItemGenerator(main_generator)
    sc_generator = SCGenerator(main_generator)
    category_generator = CategoryGeneratorFront(main_generator)

    main_generator.add_generator(base_app_generator)
    main_generator.add_generator(auth_generator)
    main_generator.add_generator(user_generator)
    main_generator.add_generator(home_generator)
    main_generator.add_generator(starter_generator)
    main_generator.add_generator(profile_generator)
    main_generator.add_generator(product_generator)
    main_generator.add_generator(item_generator)
    main_generator.add_generator(sc_generator)
    main_generator.add_generator(category_generator)

    main_generator.generate_all(model)
