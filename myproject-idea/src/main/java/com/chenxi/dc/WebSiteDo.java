package com.chenxi.dc;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebSiteDo {
	private Integer id;
    private String name;
    private String url;
    private String alexa;
    private String country;
    //¹¹Ôìº¯Êý
    public WebSiteDo() {
    }
    
    public WebSiteDo(int id, String name, String url, String alexa, String country) {
        super();
        this.id = id;
        this.name = name;
        this.url = url;
        this.alexa = alexa;
        this.country = country;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAlexa() {
        return alexa;
    }
    public void setAlexa(String alexa) {
        this.alexa = alexa;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
