package dev.pierri.EncurtaURL.Links;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Classe responsável por fornecer métodos do serviço de encurtamento de URL.
 *
 * @since 2024-07-13
 * @autor Pierri Alexander Vidmar
 */
@Service
public class LinkService {

    private LinkRepository linkRepository;

    /**
     * Construtor para a classe LinkService.
     *
     * @param linkRepository o repositório de links a ser usado
     * @since 2024-07-13
     */
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * Gera uma URL aleatória.
     *
     * @return uma string contendo a URL aleatória gerada com alfanuméricos de 5 a 10 caracteres
     * @since 2024-07-13
     */
    public String gerarUrlAleatoria() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

    /**
     * Encurta uma URL original.
     *
     * @param urlOriginal a URL original a ser encurtada
     * @return o objeto Link contendo a URL original, a URL encurtada, a data de criação e o QR code
     * @since 2024-07-13
     */
    public Link encurtarUrl(String urlOriginal) {
        Link link = new Link();
        link.setUrlLong(urlOriginal);
        link.setUrlEncurtada(gerarUrlAleatoria());
        link.setUrlCriadaEm(LocalDateTime.now());
        link.setUrlQrCode("QR CODE INDISPONIVEL");

        return linkRepository.save(link);
    }

    /**
     * Obtém a URL original a partir de uma URL encurtada.
     *
     * @param urlEncurtada a URL encurtada para buscar a URL original
     * @return o objeto Link contendo a URL original correspondente
     * @throws RuntimeException se a URL encurtada não existir na base de dados
     * @since 2024-07-13
     */
    public Link obterUrlOriginal(String urlEncurtada) {
        try {
            return linkRepository.findByUrlLong(urlEncurtada);
        } catch (Exception erro) {
            throw new RuntimeException("URL não existe na base", erro);
        }
    }
}
