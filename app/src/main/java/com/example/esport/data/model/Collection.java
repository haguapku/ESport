package com.example.esport.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "collection",strict = false)
public class Collection {

    @Attribute(name = "href")
    public String href;

    @Element(name = "id")
    public String id;

    @Element(name = "title")
    public String title;

    @Element(name = "categories")
    public Categories categories;

}
