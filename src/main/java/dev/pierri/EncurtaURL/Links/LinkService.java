package dev.pierri.EncurtaURL.Links;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private LinkRepository linkRepository;

    public linkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    public String gerarUrlAleatoria() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

    public Link encurtarUrl(String urlOriginal) {
        Link link = new Link();
    }
}
