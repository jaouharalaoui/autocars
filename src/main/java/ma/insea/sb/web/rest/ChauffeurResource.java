package ma.insea.sb.web.rest;
import ma.insea.sb.domain.Chauffeur;
import ma.insea.sb.service.ChauffeurService;
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
 * REST controller for managing Chauffeur.
 */
@RestController
@RequestMapping("/api")
public class ChauffeurResource {

    private final Logger log = LoggerFactory.getLogger(ChauffeurResource.class);

    private static final String ENTITY_NAME = "chauffeur";

    private final ChauffeurService chauffeurService;

    public ChauffeurResource(ChauffeurService chauffeurService) {
        this.chauffeurService = chauffeurService;
    }

    /**
     * POST  /chauffeurs : Create a new chauffeur.
     *
     * @param chauffeur the chauffeur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chauffeur, or with status 400 (Bad Request) if the chauffeur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chauffeurs")
    public ResponseEntity<Chauffeur> createChauffeur(@Valid @RequestBody Chauffeur chauffeur) throws URISyntaxException {
        log.debug("REST request to save Chauffeur : {}", chauffeur);
        if (chauffeur.getId() != null) {
            throw new BadRequestAlertException("A new chauffeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chauffeur result = chauffeurService.save(chauffeur);
        return ResponseEntity.created(new URI("/api/chauffeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chauffeurs : Updates an existing chauffeur.
     *
     * @param chauffeur the chauffeur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chauffeur,
     * or with status 400 (Bad Request) if the chauffeur is not valid,
     * or with status 500 (Internal Server Error) if the chauffeur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chauffeurs")
    public ResponseEntity<Chauffeur> updateChauffeur(@Valid @RequestBody Chauffeur chauffeur) throws URISyntaxException {
        log.debug("REST request to update Chauffeur : {}", chauffeur);
        if (chauffeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chauffeur result = chauffeurService.save(chauffeur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chauffeur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chauffeurs : get all the chauffeurs.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of chauffeurs in body
     */
    @GetMapping("/chauffeurs")
    public ResponseEntity<List<Chauffeur>> getAllChauffeurs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Chauffeurs");
        Page<Chauffeur> page;
        if (eagerload) {
            page = chauffeurService.findAllWithEagerRelationships(pageable);
        } else {
            page = chauffeurService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/chauffeurs?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /chauffeurs/:id : get the "id" chauffeur.
     *
     * @param id the id of the chauffeur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chauffeur, or with status 404 (Not Found)
     */
    @GetMapping("/chauffeurs/{id}")
    public ResponseEntity<Chauffeur> getChauffeur(@PathVariable Long id) {
        log.debug("REST request to get Chauffeur : {}", id);
        Optional<Chauffeur> chauffeur = chauffeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chauffeur);
    }

    /**
     * DELETE  /chauffeurs/:id : delete the "id" chauffeur.
     *
     * @param id the id of the chauffeur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chauffeurs/{id}")
    public ResponseEntity<Void> deleteChauffeur(@PathVariable Long id) {
        log.debug("REST request to delete Chauffeur : {}", id);
        chauffeurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
