package ninja.peplinski.nightcore.services;

import ninja.peplinski.nightcore.model.Request;
import ninja.peplinski.nightcore.model.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Service
public class RequestService extends Listable<Request> {

    @Autowired
    private RequestRepository repository;

    public long getOpenRequestCount() {
        return repository.countByOpen(true);
    }

    public Page<Request> getAllOpenRequests(Integer pageNo, Integer pageSize, String sortBy) {

        return repository.findAllByOpen(PaginationService.constrainedPaging(pageNo, pageSize, sortBy), true);
    }

    public void closeRequest(Request request) {
        request.setOpen(false);
        repository.save(request);
    }

    public void saveRequest(String url, Date date) throws MalformedURLException {

        @SuppressWarnings("unused") URL _url = new URL(url);

        Request request = new Request();
        request.setUrl(url);
        request.setDate(date);
        request.setOpen(true);

        repository.save(request);
    }
}
