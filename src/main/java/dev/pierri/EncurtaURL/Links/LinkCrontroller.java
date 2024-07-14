package dev.pierri.EncurtaURL.Links;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Controller responsável por gerenciar URLs encurtadas.
 * <p>
 * Este controller fornece endpoints para gerar URLs encurtadas e redirecionar URLs encurtadas para suas URLs originais.
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 2024-07-13
 */
@RestController
public class LinkCrontroller {

    private LinkService linkService;

    /**
     * Construtor para inicializar o serviço de links.
     *
     * @param linkService o serviço de links
     */
    public LinkCrontroller(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * Gera uma URL encurtada a partir de uma URL original fornecida.
     * <p>
     * Endpoint para encurtar URLs. Recebe uma URL original e retorna uma URL encurtada.
     * </p>
     *
     * @param request um mapa contendo a URL original com a chave "urlOriginal"
     * @return ResponseEntity contendo a resposta com os detalhes da URL encurtada
     */
    @PostMapping("/encurtar-url")
    public ResponseEntity<LinkResponse> gerarUrlEncurtada(@RequestBody Map<String, String> request) {

        String urlOriginal = request.get("urlOriginal");
        Link link = linkService.encurtarUrl(urlOriginal);

        String gerarUrlDeRedirecionamento = "http://localhost:8080/r/" + link.getUrlEncurtada();

        LinkResponse response = new LinkResponse(
                link.getId(),
                link.getUrlLong(),
                gerarUrlDeRedirecionamento,
                link.getUrlQrCode(),
                link.getUrlCriadaEm()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Redireciona uma URL encurtada para a URL original.
     * <p>
     * Endpoint para redirecionar URLs encurtadas. Recebe uma URL encurtada e redireciona para a URL original.
     * </p>
     *
     * @param urlEncurtada a URL encurtada
     * @param response o HttpServletResponse para enviar o redirecionamento
     * @throws IOException se ocorrer um erro ao redirecionar a URL
     */
    @GetMapping("/r/{urlEncurtada}")
    public void redirecionarLink(@PathVariable String urlEncurtada, HttpServletResponse response) throws IOException {
        Link link = linkService.obterUrlOriginal(urlEncurtada);

        if (link != null) {
            response.sendRedirect(link.getUrlLong());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

