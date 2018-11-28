package com.example.esport.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class Feed {

    @Element(name = "id")
    public String id;

    @ElementList(inline = true,required = false)
    public List<Entry> entries;
}
