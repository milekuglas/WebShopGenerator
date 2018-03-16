'''
Created on Jan 7, 2018

@author: Milorad Vojnovic
'''
from stringcase import camelcase
from textx import TextXSemanticError
from textx.metamodel import metamodel_from_file
from textx.export import metamodel_export, model_export
import os

from generation.types.simple_type import SimpleType


class Parser(object):

    def check_some_semantics(model, metamodel):
        if model.base_product.type != 'base':
            raise TextXSemanticError('First defined product must be "base" product.')
        for product in model.products:
            if product.type == 'base':
                raise TextXSemanticError('You must declare exactly one "base" product.')

        name_found = False
        for pr in model.base_product.properties:
            if pr.name == 'name':
                name_found = True
        if not name_found:
            raise TextXSemanticError('Property "name" for base product must be declared.')

        categories = model.categories
        product_set = set()
        category_set = set()
        non_product_category_set = set()
        for product in model.products:
            if product.name in product_set:
                raise TextXSemanticError('Product "%s" is defined more than once' %
                                         product.name)
            else:
                product_set.add(product.name)

        for category in categories:
            if category.name not in category_set:
                category_set.add(category.name)
            else:
                raise TextXSemanticError('Category "%s" is defined more than once' %
                                         category.name)
            if category.name in product_set:
                product_set.add(category.name)
            else:
                if category.name not in non_product_category_set:
                    non_product_category_set.add(category.name)
                else:
                    non_product_category_set.add(category.name)

        non_product_category_map = {}
        for cat in non_product_category_set:
            non_product_category_map[cat] = False

        for category in categories:
            if category.name not in product_set and category.subcategory is not None and category.subcategory.name in product_set:
                raise TextXSemanticError('Category "%s" must be on the end of inheritance level.' %
                                         category.subcategory.name)
            if category.subcategory is not None and category.subcategory.name in non_product_category_map:
                non_product_category_map[category.subcategory.name] = True

            if category.name in product_set and category.subcategory.name in product_set:
                raise TextXSemanticError('One product category can not inherit another product category.')

        for category in non_product_category_map:
            if not non_product_category_map[category]:
                raise TextXSemanticError('Category "%s" must not be on the end of inheritance level.' %
                                         category)

        for product in product_set:
            if product not in category_set:
                raise TextXSemanticError('Category "%s" must be declared.' %
                                         product)

    def product_obj_processor(product):
        if product.name[0].islower():
            raise TextXSemanticError('Name "%s" must starts with upper character' %
                                     product.name)
        property_names = set()
        for property in product.properties:
            if property.name in property_names:
                raise TextXSemanticError('Two properties "%s" is not allowed for the same product.' %
                                         property.name)
            else:
                property_names.add(property.name)

    def category_obj_processor(category):
        if category.name[0].islower():
            raise TextXSemanticError('Name "%s" must starts with upper character' %
                                     category.name)

    def property_obj_processor(property):
        if property.name == 'id':
                    raise TextXSemanticError('Property name id is not allowd')
        if property.name != camelcase(property.name):
                    raise TextXSemanticError('Property name "%s" must be in camel case notation.' %
                                         property.name)

    def parse(self, path, grammar_file_name, model_file_name, export_dot):
        meta_path = os.path.join(path, grammar_file_name)
        meta_name = os.path.splitext(meta_path)[0]
        type_builtins = {
                'int': SimpleType(None, 'Int'),
                'string': SimpleType(None, 'String'),
                'long': SimpleType(None, 'Long'),
                'double': SimpleType(None, 'Double'),
                'byte': SimpleType(None, 'Byte'),
                'float': SimpleType(None, 'Float'),
                'boolean': SimpleType(None, 'Boolean')
        }
        metamodel = metamodel_from_file(meta_path, classes=[SimpleType], builtins=type_builtins)
        obj_processors = {
            'Product': Parser.product_obj_processor,
            'Category': Parser.category_obj_processor,
            'Property': Parser.property_obj_processor
        }
        metamodel.register_obj_processors(obj_processors)
        metamodel.register_model_processor(Parser.check_some_semantics)

        if export_dot:
            metamodel_export(metamodel, meta_name + '.dot')

        model_path = os.path.join(path, model_file_name)
        model_name = os.path.splitext(model_path)[0]

        model = metamodel.model_from_file(model_path)

        if export_dot:
            model_export(model, model_name + '.dot')

        return model
