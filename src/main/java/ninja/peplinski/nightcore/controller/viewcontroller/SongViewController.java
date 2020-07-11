package ninja.peplinski.nightcore.controller.viewcontroller;

import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.errors.NoSuchArtistException;
import ninja.peplinski.nightcore.errors.NoSuchGenreException;
import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.Song;
import ninja.peplinski.nightcore.services.RequestService;
import ninja.peplinski.nightcore.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class SongViewController {

    @Autowired
    SongService songService;
    @Autowired
    RequestService requestService;
    @Autowired
    ViewControllerHelper viewControllerHelper;

    @RequestMapping(path = "/songs")
    ModelAndView getSongs(@RequestParam(defaultValue = "") String q,
                          @RequestParam(defaultValue = "1") Integer p,
                          @RequestParam(defaultValue = "10") Integer l,
                          @RequestParam(defaultValue = "id") String sortBy) {

        ModelAndView modelAndView = new ModelAndView("songsList");
        Page<Song> pagedResult = songService.getAll(p, l, sortBy);

        modelAndView = viewControllerHelper.addPaginationAttributes(modelAndView,
                "songs",
                pagedResult,
                p);

        return modelAndView;
    }

    @RequestMapping(path ="/songs/{id}")
    private ModelAndView getRequestById(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("song");

        Optional<Song> optionalArtist = songService.getById(id);

        if (optionalArtist.isPresent()) {
            modelAndView.addObject("song", optionalArtist.get());
            return modelAndView;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/songs/delete/{id}")
    private String delete(@PathVariable Integer id) {
        songService.deleteById(id);

        return "redirect:/?success";
    }

    @PostMapping(path = "/songs/add")
    private String addNewArtist(@RequestParam String title,
                                @RequestParam Integer artistId,
                                @RequestParam String genre,
                                @RequestParam String ytId,
                                @RequestParam(defaultValue = "") String clicks) {

        return "redirect:/songs?" + saveSong(title, artistId, genre, ytId, clicks).toString();
    }

    @PostMapping(path = "/songs/add/request/{requestId}")
    private String addNewArtistFromRequest(@PathVariable Integer requestId,
                                           @RequestParam String title,
                                           @RequestParam Integer artistId,
                                           @RequestParam String genre,
                                           @RequestParam String ytId,
                                           @RequestParam(defaultValue = "") String clicks) {

        Optional<Request> optionalRequest = requestService.getById(requestId);
        if (!optionalRequest.isPresent() || !optionalRequest.get().getOpen()) return "redirect:/songs?error";

        CreationStatus creationStatus = saveSong(title, artistId, genre, ytId, clicks);
        if (!creationStatus.equals(CreationStatus.SUCCESS)) return "redirect:/songs?" + creationStatus.toString();
        requestService.closeRequest(optionalRequest.get());

        return "redirect:/songs?success";
    }

    private CreationStatus saveSong(String title, Integer artistId, String genre, String ytId, String clicks) {
        try {
            if (clicks.matches("-?(0|[1-9]\\d*)")) {
                songService.saveSong(title, artistId, genre, ytId, Integer.parseInt(clicks));
            } else {
                return CreationStatus.NOTANNUMBER;
            }
        } catch (AlreadyExistsException e) {
            return CreationStatus.ALREADYESXIST;
        } catch (NoSuchArtistException e) {
            return CreationStatus.NOSUCHARTIST;
        } catch (NoSuchGenreException e) {
            return CreationStatus.NOSUCHGENRE;
        }
        return CreationStatus.SUCCESS;
    }

    enum CreationStatus {
        ALREADYESXIST("alreadyExists"),
        NOSUCHARTIST("noSuchArtist"),
        NOSUCHGENRE("noSuchGenre"),
        NOTANNUMBER("notAnNumber"),
        SUCCESS("success");

        private final String value;

        CreationStatus(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }
    }
}
