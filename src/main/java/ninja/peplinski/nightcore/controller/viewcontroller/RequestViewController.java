package ninja.peplinski.nightcore.controller.viewcontroller;

import ninja.peplinski.nightcore.errors.AlreadyExistsException;
import ninja.peplinski.nightcore.errors.NoSuchArtistException;
import ninja.peplinski.nightcore.errors.NoSuchGenreException;
import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.repositories.RequestRepository;
import ninja.peplinski.nightcore.services.RequestService;
import ninja.peplinski.nightcore.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class RequestViewController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private ViewControllerHelper viewControllerHelper;

    @RequestMapping(path = "/requests/open", method = RequestMethod.GET)
    private ModelAndView getAllOpenRequests(@RequestParam(defaultValue = "1") Integer p,
                                            @RequestParam(defaultValue = "10") Integer l,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        ModelAndView modelAndView = new ModelAndView("openRequestList");

        Page<Request> pagedResult = requestService.getAllOpenRequests(p, l, sortBy);

        modelAndView = viewControllerHelper.addPaginationAttributes(modelAndView,
                "requests",
                pagedResult,
                p);

        return modelAndView;
    }

    @RequestMapping(path = "/requests/all", method = RequestMethod.GET)
    private ModelAndView getAllRequests(@RequestParam(defaultValue = "1") Integer p,
                                            @RequestParam(defaultValue = "10") Integer l,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        ModelAndView modelAndView = new ModelAndView("requestsList");
        Page<Request> pagedResult = requestService.getAll(p, l, sortBy);

        modelAndView = viewControllerHelper.addPaginationAttributes(modelAndView,
                "requests",
                pagedResult,
                p);

        return modelAndView;
    }

    @RequestMapping(path ="/requests/{id}")
    private ModelAndView getRequestById(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("request");

        Optional<Request> optionalArtist = requestService.getById(id);

        if (optionalArtist.isPresent()) {
            modelAndView.addObject("req", optionalArtist.get());
            return modelAndView;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/requests/delete/{id}")
    private String delete(@PathVariable Integer id) {
        requestService.deleteById(id);

        return "redirect:/?success";
    }

}
