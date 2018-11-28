package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "source",strict = false)
public class Source {

    @Element(name = "id")
    public String id;

    @Element(name = "title")
    public String title;
}
