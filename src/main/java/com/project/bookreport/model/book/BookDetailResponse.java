package com.project.bookreport.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "rss")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDetailResponse {
  @JacksonXmlProperty(localName = "channel")
  private Channel channel;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Channel {
    @JacksonXmlElementWrapper(useWrapping = false) // Remove wrapper element
    @JacksonXmlProperty(localName = "item") // Map 'item' element
    private List<Item> items = new ArrayList<>();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Item {
    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlProperty(localName = "link")
    private String link;

    @JacksonXmlProperty(localName = "image")
    private String image;

    @JacksonXmlProperty(localName = "author")
    private String author;

    @JacksonXmlProperty(localName = "discount")
    private String discount;

    @JacksonXmlProperty(localName = "publisher")
    private String publisher;

    @JacksonXmlProperty(localName = "pubdate")
    private String pubdate;

    @JacksonXmlProperty(localName = "isbn")
    private String isbn;

    @JacksonXmlProperty(localName = "description")
    private String description;
  }
}
