package com.example.esport.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "categories",strict = false)
public class Categories {

    @Attribute(name = "scheme")
    public String scheme;

    @Element(name = "category")
    public Category category;

}
