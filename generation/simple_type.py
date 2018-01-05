'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


class SimpleType(object):
    def __init__(self, parent, name):
        self.parent = parent
        self.name = name

    def __str__(self):
        return self.name