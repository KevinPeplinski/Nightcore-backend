package ninja.peplinski.nightcore.controller.viewcontroller;

import ninja.peplinski.nightcore.model.Artist;
import ninja.peplinski.nightcore.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Service
public class ViewControllerHelper {

    @Autowired
    PaginationService paginationService;

    public <T> ModelAndView addPaginationAttributes(ModelAndView mav,
                                                     String contentAttributeName,
                                                     Page<T> pagedResult,
                                                     Integer currentPage) {

        if (pagedResult.hasContent()) {
            mav.addObject(contentAttributeName, pagedResult.getContent());
        } else {
            mav.addObject(contentAttributeName, new ArrayList<Artist>());
        }

        mav.addObject("pages", paginationService.getNumberedPages(pagedResult, currentPage));
        mav.addObject("currentPage", currentPage);

        return mav;
    }
}
