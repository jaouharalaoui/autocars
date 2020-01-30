package ma.insea.sb.web.rest;
import ma.insea.sb.domain.FicheIncident;
import ma.insea.sb.service.FicheIncidentService;
import ma.insea.sb.web.rest.errors.BadRequestAlertException;
import ma.insea.sb.web.rest.util.HeaderUtil;
import ma.insea.sb.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FicheIncident.
 */
@RestController
@RequestMapping("/api")
public class FicheIncidentResource {

    private final Logger log = LoggerFactory.getLogger(FicheIncidentResource.class);

    private static final String ENTITY_NAME = "ficheIncident";

    private final FicheIncidentService ficheIncidentService;

    public FicheIncidentResource(FicheIncidentService ficheIncidentService) {
        this.ficheIncidentService = ficheIncidentService;
    }

    /**
     * POST  /fiche-incidents : Create a new ficheIncident.
     *
     * @param ficheIncident the ficheIncident to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ficheIncident, or with status 400 (Bad Request) if the ficheIncident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiche-incidents")
    public ResponseEntity<FicheIncident> createFicheIncident(@Valid @RequestBody FicheIncident ficheIncident) throws URISyntaxException {
        log.debug("REST request to save FicheIncident : {}", ficheIncident);
        if (ficheIncident.getId() != null) {
            throw new BadRequestAlertException("A new ficheIncident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FicheIncident result = ficheIncidentService.save(ficheIncident);
        return ResponseEntity.created(new URI("/api/fiche-incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiche-incidents : Updates an existing ficheIncident.
     *
     * @param ficheIncident the ficheIncident to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ficheIncident,
     * or with status 400 (Bad Request) if the ficheIncident is not valid,
     * or with status 500 (Internal Server Error) if the ficheIncident couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiche-incidents")
    public ResponseEntity<FicheIncident> updateFicheIncident(@Valid @RequestBody FicheIncident ficheIncident) throws URISyntaxException {
        log.debug("REST request to update FicheIncident : {}", ficheIncident);
        if (ficheIncident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FicheIncident result = ficheIncidentService.save(ficheIncident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ficheIncident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiche-incidents : get all the ficheIncidents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ficheIncidents in body
     */
    @GetMapping("/fiche-incidents")
    public ResponseEntity<List<FicheIncident>> getAllFicheIncidents(Pageable pageable) {
        log.debug("REST request to get a page of FicheIncidents");
        Page<FicheIncident> page = ficheIncidentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fiche-incidents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fiche-incidents/:id : get the "id" ficheIncident.
     *
     * @param id the id of the ficheIncident to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ficheIncident, or with status 404 (Not Found)
     */
    @GetMapping("/fiche-incidents/{id}")
    public ResponseEntity<FicheIncident> getFicheIncident(@PathVariable Long id) {
        log.debug("REST request to get FicheIncident : {}", id);
        Optional<FicheIncident> ficheIncident = ficheIncidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ficheIncident);
    }

    /**
     * DELETE  /fiche-incidents/:id : delete the "id" ficheIncident.
     *
     * @param id the id of the ficheIncident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiche-incidents/{id}")
    public ResponseEntity<Void> deleteFicheIncident(@PathVariable Long id) {
        log.debug("REST request to delete FicheIncident : {}", id);
        ficheIncidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
