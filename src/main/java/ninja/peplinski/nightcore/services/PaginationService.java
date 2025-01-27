package ninja.peplinski.nightcore.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public static Pageable constrainedPaging(Integer pageNo, Integer pageSize, String sortBy) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        if (pageSize < 5) {
            pageSize = 5;
        }

        if (pageSize > 50) {
            pageSize = 50;
        }

        return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
    }

}
