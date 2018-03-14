import re


def get_property_titles(properties):
    prop_titles=[]
    for prop in properties:
        p = re.sub('(?!^)([A-Z][a-z]+)', r' \1', prop.name).split()
        p[0] = p[0].capitalize()
        prop_titles.append(' '.join(p))
    return prop_titles
