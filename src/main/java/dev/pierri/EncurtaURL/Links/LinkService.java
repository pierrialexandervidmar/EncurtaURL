package dev.pierri.EncurtaURL.Links;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Classe responsável por fornecer métodos do serviço de encurtamento de URL.
 */
@Service
public class LinkService {

    private LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * Gera uma URL aleatória.
     * @return uma string contendo a URL aleatória gerada com alfanuméricos de 5 a 10 caracteres
     */
    public String gerarUrlAleatoria() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

    /**
     * Encurta uma URL original.
     * @param urlOriginal a URL original a ser encurtada
     * @return o objeto Link contendo a URL original, a URL encurtada, a data de criação e o QR code
     */
    public Link encurtarUrl(String urlOriginal) {
        Link link = new Link();
        link.setUrlLong(urlOriginal);
        link.setUrlEncurtada(gerarUrlAleatoria());
        link.setUrlCriadaEm(LocalDateTime.now());
        link.setUrlQrCode("QR CODE INDISPONIVEL");

        return linkRepository.save(link);
    }

    public Link obterUrlOriginal(String urlEncurtada) {
        try {
            return linkRepository.findByUrlOriginal(urlEncurtada);
        } catch (Exception erro) {
            throw new RuntimeException("Url não existe na base", erro);
        }
    }
}
