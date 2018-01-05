'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''

import os

from root import root
from execute.execute import execute
from generation.generator import generate


def main(debug=False):
    model = execute(os.path.join(root, "metamodel"), 'scala-angular.tx', 'project.scan', debug)
    for i in range(len(model.products)):
        generate("case_class_template.scala", os.path.join("app", model.project.package.name.replace(".", os.sep), "model"),
                 model.products[i].name+".scala", {"model": model, "product": model.products[i]})


if __name__ == '__main__':
    main(True)