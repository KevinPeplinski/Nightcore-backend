package ninja.peplinski.nightcore.controller.viewcontroller;

import ninja.peplinski.nightcore.services.ArtistService;
import ninja.peplinski.nightcore.services.RequestService;
import ninja.peplinski.nightcore.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardViewController {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private SongService songService;
    @Autowired
    private RequestService requestService;

    @RequestMapping(path = {"/admin", "/"})
    private ModelAndView getDashboard() {
        ModelAndView modelAndView = new ModelAndView("admin");

        modelAndView.addObject("artistCount", artistService.getCount());
        modelAndView.addObject("songCount", songService.getCount());
        modelAndView.addObject("openRequestCount", requestService.getOpenRequestCount());
        modelAndView.addObject("requestCount", requestService.getCount());

        return modelAndView;
    }
}
