package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "entry",strict = false)
public class Entry {

    @Element(name = "id")
    public String id;

    @Element(name = "title")
    public String title;

    @Element(name = "updated")
    public String updated;

    @Element(name = "summary")
    public String summary;

    @Element(name = "source")
    public Source source;

}
