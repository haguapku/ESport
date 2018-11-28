package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name ="workspace",strict = false)
public class Workspace {

    @Element(name = "title")
    public String title;

    @ElementList(inline = true,required = false)
    public List<Collection> collections;

    @Override
    public String toString() {
        return title;
    }
}
