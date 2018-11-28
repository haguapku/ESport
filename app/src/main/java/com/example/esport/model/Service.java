package com.example.esport.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "service",strict = false)
public class Service {

    @Element
    public Workspace workspace;
}
