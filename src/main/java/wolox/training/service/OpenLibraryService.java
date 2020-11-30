package wolox.training.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.constants.ErrorConstants;
import wolox.training.model.dto.BookDTO;
import wolox.training.exceptions.BookNotFoundException;

@Service
public class OpenLibraryService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${URL}")
    private String urlExternalOpenLibrary;

    private static final String QUERY_ISBN = "ISBN:";

    public BookDTO bookInfo(String isbn){
        String queryIsbn = QUERY_ISBN +isbn;
        URI uri = UriComponentsBuilder
                .fromHttpUrl(urlExternalOpenLibrary)
                .path("books")
                .queryParam("bibkeys", queryIsbn)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .build()
                .toUri();
        ObjectNode  objectNode = restTemplate.getForObject(uri, ObjectNode.class);
        if(!objectNode.isEmpty()){
            return BookDTO.setBookDto(objectNode, isbn);
        }
        throw new BookNotFoundException(ErrorConstants.NOT_EXIST_ID);
    }

}
