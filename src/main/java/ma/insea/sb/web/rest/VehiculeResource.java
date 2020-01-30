package ma.insea.sb.web.rest;
import ma.insea.sb.domain.Vehicule;
import ma.insea.sb.service.VehiculeService;
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
 * REST controller for managing Vehicule.
 */
@RestController
@RequestMapping("/api")
public class VehiculeResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeResource.class);

    private static final String ENTITY_NAME = "vehicule";

    private final VehiculeService vehiculeService;

    public VehiculeResource(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    /**
     * POST  /vehicules : Create a new vehicule.
     *
     * @param vehicule the vehicule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicule, or with status 400 (Bad Request) if the vehicule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicules")
    public ResponseEntity<Vehicule> createVehicule(@Valid @RequestBody Vehicule vehicule) throws URISyntaxException {
        log.debug("REST request to save Vehicule : {}", vehicule);
        if (vehicule.getId() != null) {
            throw new BadRequestAlertException("A new vehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vehicule result = vehiculeService.save(vehicule);
        return ResponseEntity.created(new URI("/api/vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicules : Updates an existing vehicule.
     *
     * @param vehicule the vehicule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicule,
     * or with status 400 (Bad Request) if the vehicule is not valid,
     * or with status 500 (Internal Server Error) if the vehicule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicules")
    public ResponseEntity<Vehicule> updateVehicule(@Valid @RequestBody Vehicule vehicule) throws URISyntaxException {
        log.debug("REST request to update Vehicule : {}", vehicule);
        if (vehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vehicule result = vehiculeService.save(vehicule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicules : get all the vehicules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehicules in body
     */
    @GetMapping("/vehicules")
    public ResponseEntity<List<Vehicule>> getAllVehicules(Pageable pageable) {
        log.debug("REST request to get a page of Vehicules");
        Page<Vehicule> page = vehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vehicules/:id : get the "id" vehicule.
     *
     * @param id the id of the vehicule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicule, or with status 404 (Not Found)
     */
    @GetMapping("/vehicules/{id}")
    public ResponseEntity<Vehicule> getVehicule(@PathVariable Long id) {
        log.debug("REST request to get Vehicule : {}", id);
        Optional<Vehicule> vehicule = vehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicule);
    }

    /**
     * DELETE  /vehicules/:id : delete the "id" vehicule.
     *
     * @param id the id of the vehicule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicules/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        log.debug("REST request to delete Vehicule : {}", id);
        vehiculeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
