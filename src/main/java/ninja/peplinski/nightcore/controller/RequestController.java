package ninja.peplinski.nightcore.controller;

import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

@RestController
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

    @PostMapping(path = "/api/request/add")
    private @ResponseBody ResponseEntity<Request> addNewSong(@RequestParam String url, @RequestParam("date") @DateTimeFormat(pattern="dd-MM-yyyy") Date date) {
        try {
            @SuppressWarnings("unused") URL _url = new URL(url);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Request request = new Request();
        request.setUrl(url);
        request.setDate(date);
        request.setOpen(true);

        requestRepository.save(request);
        return ResponseEntity.ok(request);
    }
}
