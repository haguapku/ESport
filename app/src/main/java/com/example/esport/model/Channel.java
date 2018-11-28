package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel",strict = false)
public class Channel {

    @Element(name = "title")
    public String title;

    @ElementList(inline = true)
    public List<Item> items;

}
