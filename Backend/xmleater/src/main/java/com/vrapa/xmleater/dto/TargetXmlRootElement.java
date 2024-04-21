package com.vrapa.xmleater.dto;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement(name = "BlogsEntry")
public class TargetXmlRootElement {

    private String title;
    private Date publishedDate;
    private String link;
    private String description;

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "__title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    @XmlElement(name = "__publishedDate")
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getLink() {
        return link;
    }

    @XmlElement(name = "__link")
    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "__description")
    public void setDescription(String description) {
        this.description = description;
    }
}
