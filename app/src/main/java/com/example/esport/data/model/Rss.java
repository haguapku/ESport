package com.example.esport.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss",strict = false)
public class Rss {

    @Element(name = "channel")
    public Channel channel;
}
