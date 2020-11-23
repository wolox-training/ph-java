package wolox.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import wolox.training.models.Book;

@Data
@Builder
public class BookDTO {
    private String isbn;
    @JsonProperty
    private String title;
    @JsonProperty
    private  String subTitle;
    @JsonProperty
    private List<HashMap<String, String>> publishers;
    @JsonProperty(value = "year")
    private String year;
    @JsonProperty(value = "number_pages")
    private int numberPages;
    @JsonProperty
    private List<HashMap<String, String>> authors;

    public Book setBook(){
        Book book= new Book();
        book.setImage("null");
        book.setIsbn(this.isbn);
        book.setTitle(this.title);
        book.setSubtitle(this.subTitle);
        book.setPublisher(this.publishers.get(0).get("name"));
        book.setPages(this.numberPages);
        book.setAuthor(this.authors.get(0).get("name"));
        book.setYear(this.year);
        return book;
    }

    public static BookDTO setBookDto(ObjectNode node, String isbn){
        final String queryIsbn = "ISBN:" + isbn;
        ObjectMapper objectMapper = new ObjectMapper();
        return BookDTO
                .builder()
                .isbn(isbn)
                .title(objectMapper.convertValue(node.get(queryIsbn).get("title"), String.class))
                .title(objectMapper.convertValue(node.get(queryIsbn).get("subTitle"), String.class))
                .title(objectMapper.convertValue(node.get(queryIsbn).get("publishers"), String.class))
                .title(objectMapper.convertValue(node.get(queryIsbn).get("year"), String.class))
                .title(objectMapper.convertValue(node.get(queryIsbn).get("number_pages"), String.class))
                .title(objectMapper.convertValue(node.get(queryIsbn).get("authors"), String.class))
                .build();
    }

}
