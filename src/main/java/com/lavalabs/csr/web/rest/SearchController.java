package com.lavalabs.csr.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.service.SearchService;
import com.lavalabs.csr.web.rest.vm.SearchResponseVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchController {

    private final Logger log = LoggerFactory.getLogger(SearchController.class);

    private final SearchService searchService;


    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }



    /**
     * GET  /admin-search : get all the matched results.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matched result in body
     */
    @GetMapping("/api/admin-search")
    @Timed
    public SearchResponseVM adminSearch(@RequestParam("query") String query, @RequestParam(name = "location", required = false) String location) {
        log.debug("REST request for adming search");
        return searchService.search(query, location, true);
    }

    /**
     * GET  /admin-search : get all the matched results.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matched result in body
     */
    @GetMapping("/search")
    @Timed
    public SearchResponseVM search(@RequestParam("query") String query, @RequestParam(name = "location", required = false) String location) {
        log.debug("REST request for adming search");
        return searchService.search(query, location, false);
    }


}
