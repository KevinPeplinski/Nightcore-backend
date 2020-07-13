package ninja.peplinski.nightcore.controller.viewcontroller;

import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static ninja.peplinski.nightcore.model.specifications.ArtistSpecifications.artistWithName;

@Controller
public class ArtistViewController {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private ViewControllerHelper viewControllerHelper;

    @RequestMapping(path = "/artists", method = RequestMethod.GET)
    private ModelAndView showArtists(@RequestParam(defaultValue = "") String q,
                                     @RequestParam(defaultValue = "1") Integer p,
                                     @RequestParam(defaultValue = "10") Integer l,
                                     @RequestParam(defaultValue = "id") String sortBy) {

        ModelAndView modelAndView = new ModelAndView("artistList");

        Page<Artist> pagedResult;
        if (!q.isEmpty()) {
            pagedResult = artistService.getAllSearched(artistWithName(q), p, l, sortBy);
        } else {
            pagedResult = artistService.getAll(p, l, sortBy);
        }

        modelAndView = viewControllerHelper.addPaginationAttributes(modelAndView,
                "artists",
                pagedResult,
                p,
                q);

        return modelAndView;
    }

    @GetMapping(path = "/artists/{id}")
    private ModelAndView showSingleArtist(@PathVariable Integer id) {

        ModelAndView modelAndView = new ModelAndView("artist");

        Optional<Artist> optionalArtist = artistService.getById(id);

        if (optionalArtist.isPresent()) {
            modelAndView.addObject("artist", optionalArtist.get());
            return modelAndView;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/artists/delete/{id}")
    private String delete(@PathVariable Integer id) {
        artistService.deleteById(id);

        return "redirect:/?success";
    }

    @PostMapping(path = "/artists/add")
    private String addNewArtist(@RequestParam String name) {
        try {
            artistService.saveArtist(name);
        } catch (AlreadyExistsException e) {
            return "redirect:/artists?alreadyExists";
        }

        return "redirect:/artists?success";
    }
}
