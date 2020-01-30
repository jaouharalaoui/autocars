package ma.insea.sb.web.rest;
import ma.insea.sb.domain.AgentDeclarant;
import ma.insea.sb.service.AgentDeclarantService;
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
 * REST controller for managing AgentDeclarant.
 */
@RestController
@RequestMapping("/api")
public class AgentDeclarantResource {

    private final Logger log = LoggerFactory.getLogger(AgentDeclarantResource.class);

    private static final String ENTITY_NAME = "agentDeclarant";

    private final AgentDeclarantService agentDeclarantService;

    public AgentDeclarantResource(AgentDeclarantService agentDeclarantService) {
        this.agentDeclarantService = agentDeclarantService;
    }

    /**
     * POST  /agent-declarants : Create a new agentDeclarant.
     *
     * @param agentDeclarant the agentDeclarant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentDeclarant, or with status 400 (Bad Request) if the agentDeclarant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-declarants")
    public ResponseEntity<AgentDeclarant> createAgentDeclarant(@Valid @RequestBody AgentDeclarant agentDeclarant) throws URISyntaxException {
        log.debug("REST request to save AgentDeclarant : {}", agentDeclarant);
        if (agentDeclarant.getId() != null) {
            throw new BadRequestAlertException("A new agentDeclarant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgentDeclarant result = agentDeclarantService.save(agentDeclarant);
        return ResponseEntity.created(new URI("/api/agent-declarants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agent-declarants : Updates an existing agentDeclarant.
     *
     * @param agentDeclarant the agentDeclarant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentDeclarant,
     * or with status 400 (Bad Request) if the agentDeclarant is not valid,
     * or with status 500 (Internal Server Error) if the agentDeclarant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agent-declarants")
    public ResponseEntity<AgentDeclarant> updateAgentDeclarant(@Valid @RequestBody AgentDeclarant agentDeclarant) throws URISyntaxException {
        log.debug("REST request to update AgentDeclarant : {}", agentDeclarant);
        if (agentDeclarant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgentDeclarant result = agentDeclarantService.save(agentDeclarant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agentDeclarant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agent-declarants : get all the agentDeclarants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agentDeclarants in body
     */
    @GetMapping("/agent-declarants")
    public ResponseEntity<List<AgentDeclarant>> getAllAgentDeclarants(Pageable pageable) {
        log.debug("REST request to get a page of AgentDeclarants");
        Page<AgentDeclarant> page = agentDeclarantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agent-declarants");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /agent-declarants/:id : get the "id" agentDeclarant.
     *
     * @param id the id of the agentDeclarant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentDeclarant, or with status 404 (Not Found)
     */
    @GetMapping("/agent-declarants/{id}")
    public ResponseEntity<AgentDeclarant> getAgentDeclarant(@PathVariable Long id) {
        log.debug("REST request to get AgentDeclarant : {}", id);
        Optional<AgentDeclarant> agentDeclarant = agentDeclarantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agentDeclarant);
    }

    /**
     * DELETE  /agent-declarants/:id : delete the "id" agentDeclarant.
     *
     * @param id the id of the agentDeclarant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agent-declarants/{id}")
    public ResponseEntity<Void> deleteAgentDeclarant(@PathVariable Long id) {
        log.debug("REST request to delete AgentDeclarant : {}", id);
        agentDeclarantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
