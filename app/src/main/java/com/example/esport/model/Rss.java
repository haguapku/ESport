package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "rss",strict = false)
public class Rss {

    @Element(name = "channel")
    public Channel channel;
}
