package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item",strict = false)
public class Item {

    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Element(name = "description")
    public String description;
}
