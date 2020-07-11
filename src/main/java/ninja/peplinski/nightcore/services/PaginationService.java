package ninja.peplinski.nightcore.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationService {

    public <T> Iterable<Integer> getNumberedPages(Page<T> pagedResult, int current) {
        List<Integer> pageNumbers = new ArrayList<>();

        int last = pagedResult.getTotalPages();
        int delta = 2;
        int left = current - delta;
        int right = current + delta + 1;

        for (int i = 1; i <= last; i++) {
            if (i == 1 || i == last || i >= left && i < right) {
                pageNumbers.add(i);
            }
        }

        return pageNumbers;
    }

}
