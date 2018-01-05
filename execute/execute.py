'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


from textx.metamodel import metamodel_from_file
from textx.export import metamodel_export, model_export
import os

from generation.simple_type import SimpleType


def execute(path, grammar_file_name, example_file_name, export_dot):
    meta_path = os.path.join(path, grammar_file_name)
    meta_name = os.path.splitext(meta_path)[0]
    type_builtins = {
            'integer': SimpleType(None, 'Int'),
            'string': SimpleType(None, 'String'),
            'long' : SimpleType(None, 'Long'),
            'double' : SimpleType(None, 'Double'),
            'byte' : SimpleType(None, 'Byte'),
            'float' : SimpleType(None, 'Float'),
            'boolean' : SimpleType(None, 'Boolean')
    }
    metamodel = metamodel_from_file(meta_path, classes=[SimpleType], builtins=type_builtins)
    
    if export_dot:
        metamodel_export(metamodel, meta_name + '.dot')
            
    model_path = os.path.join(path, example_file_name)
    model_name = os.path.splitext(model_path)[0]
            
    model = metamodel.model_from_file(model_path)
    
    if export_dot:
        model_export(model, model_name + '.dot')
    
    return model
    
    
    