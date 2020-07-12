package ninja.peplinski.nightcore.controller;

import com.fasterxml.jackson.annotation.JsonView;
import ninja.peplinski.nightcore.model.view.JsonScope;
import ninja.peplinski.nightcore.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(path = "/search")
    @JsonView(JsonScope.Public.class)
    private @ResponseBody Iterable<SearchService.SearchWrapper<?>> search(@RequestParam String q) {

        return searchService.genericSearch(q);
    }

}
