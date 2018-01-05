'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


from jinja2.environment import Environment
from jinja2.loaders import PackageLoader
from root import root
import os

def generate(template_name, directory, output_name, render_vars):
    env = Environment(trim_blocks=True, lstrip_blocks=True, loader=PackageLoader("generation", "templates"))
    template = env.get_template(template_name)
    print(render_vars)
    rendered = template.render(render_vars)
    # i pisemo u fajl
    path = os.path.join(root, "output", directory)
    if not os.path.exists(path):
        os.makedirs(path)
    file_name = os.path.join(path, output_name)
    print(file_name)
    with open(file_name, "w+") as f:
        f.write(rendered)
