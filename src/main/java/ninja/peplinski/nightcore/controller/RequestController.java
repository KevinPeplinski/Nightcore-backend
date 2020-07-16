package ninja.peplinski.nightcore.controller;

import ninja.peplinski.nightcore.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Date;

@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping(path = "/api/request/add")
    private @ResponseBody ResponseEntity<String> addNewSong(@RequestParam String url, @RequestParam("date") @DateTimeFormat(pattern="dd-MM-yyyy") Date date) {

        try {
            requestService.saveRequest(url, date);
            return ResponseEntity.ok("Request Created");
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("url needs to be a valid url");
        }
    }
}
