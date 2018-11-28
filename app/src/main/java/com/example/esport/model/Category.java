package com.example.esport.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category")
public class Category {

    @Attribute(name = "term")
    public String term;
}
