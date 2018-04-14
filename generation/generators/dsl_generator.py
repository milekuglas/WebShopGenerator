import xml.etree.ElementTree as ET

from jinja2.environment import Environment
from jinja2.loaders import PackageLoader
import os

from generation.generators.generator import Generator

from root import root


class DslGenerator(Generator):

    def generate(self):
        tree = ET.parse(os.path.join('xmi_model', 'model.xml'))
        tree_root = tree.getroot()

        classes = []
        stereotypes_names = []
        stereotypes = []
        enums = []
        package_name = None
        jwt_secret = None

        generated = []

        for c in tree_root:
            if c.tag.endswith("Model"):
                for child in c:
                    if child.get("{http://www.omg.org/spec/XMI/20131001}type") == "uml:Package":
                        package_name = child.get("name")
                        for ch in child:
                            if ch.tag == "packagedElement" and ch.get("name") != "str":
                                if ch.get("{http://www.omg.org/spec/XMI/20131001}type") == "uml:Class":
                                    attributes = []
                                    generalization = {}
                                    for att in ch:
                                        if att.tag == "ownedAttribute":
                                            try:
                                                att_type = att[0][0][0].get("referentPath")
                                                temp = att_type.split(":")
                                                attribute = {"name": att.get("name"), "type": temp[len(temp) - 1]}
                                                attributes.append(attribute)
                                            except IndexError:
                                                if att.get("name") is not None and att.get("type") is not None:
                                                    attribute = {"name": att.get("name"),
                                                                 "type": "enum" + att.get("type")}
                                                    attributes.append(attribute)
                                                else:
                                                    continue

                                        if att.tag == "generalization":
                                            generalization = att.get("general")
                                    classes.append(
                                        {"name": ch.get("name"),
                                         "id": ch.get("{http://www.omg.org/spec/XMI/20131001}id"),
                                         "attributes": attributes, "generalization": generalization})
                                if ch.get("{http://www.omg.org/spec/XMI/20131001}type") == "uml:Enumeration":
                                    attributes = []
                                    for lit in ch:
                                        if lit.tag == "ownedLiteral":
                                            attribute = lit.get("name")
                                            attributes.append(attribute)
                                    enums.append(
                                        {"name": ch.get("name"),
                                         "id": ch.get("{http://www.omg.org/spec/XMI/20131001}id"),
                                         "attributes": attributes})
                    if child.get("{http://www.omg.org/spec/XMI/20131001}type") == "uml:Profile":
                        for ch in child:
                            if ch.get("{http://www.omg.org/spec/XMI/20131001}type") == "uml:Stereotype":
                                stereotypes_names.append(ch.get("name"))

        for child in tree_root:
            for stereotype_name in stereotypes_names:
                if child.tag.endswith(stereotype_name):
                    if child.tag.endswith("WebShopPackage"):
                        jwt_secret = child.get("jwtSecret")
                    else:
                        stereotype = {"name": stereotype_name, "class_id": child.get("base_Class")}
                        stereotypes.append(stereotype)

        for cl in classes:
            attributes = []
            for att in cl["attributes"]:
                if att["type"].startswith("enum"):
                    for enum in enums:
                        if enum["id"] == att["type"][4:]:
                            att["type"] = enum["name"]
                            att["isEnum"] = True
                            attributes.append(att)
                else:
                    att["isEnum"] = False
                    attributes.append(att)
            for stereotype in stereotypes:
                if stereotype["class_id"] == cl["id"]:
                    cl["stereotype"] = stereotype["name"]
                    cl["attributes"] = attributes
                    generated.append(cl)

        base_product = {}
        products = []
        root_categories = []
        mid_categories = []
        product_categories = []

        for obj in generated:
            if obj["stereotype"] == "Base":
                base_product = obj
            elif obj["stereotype"] == "Inherited":
                products.append(obj)
            elif obj["stereotype"] == "Root":
                root_categories.append(obj)
            elif obj["stereotype"] == "Product":
                product_categories.append(obj)

        env = Environment(trim_blocks=True, lstrip_blocks=True, loader=PackageLoader("generation", "templates"))
        template = env.get_template("xmi/model.scan")
        rendered = template.render(
            {"base_product": base_product, "products": products, "root_categories": root_categories,
             "mid_categories": mid_categories, "product_categories": product_categories, "enums": enums,
             "package_name": package_name, "jwt_secret": jwt_secret})
        path = os.path.join(root, "metamodel")
        if not os.path.exists(path):
            os.makedirs(path)
        file_name = os.path.join(path, "model.scan")
        with open(file_name, "w+") as f:
            f.write(rendered)
