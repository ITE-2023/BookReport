package com.project.bookreport.model.book;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchDTO {

  @Builder.Default
  private List<Item> items = new ArrayList<>();
  static class Item {
    public String title;
    public String image;
    public String author;
    public String isbn;
    public String publisher;
    public String description;
  }
}
