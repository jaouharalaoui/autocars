package ma.insea.sb.service;

import ma.insea.sb.domain.AgentDeclarant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AgentDeclarant.
 */
public interface AgentDeclarantService {

    /**
     * Save a agentDeclarant.
     *
     * @param agentDeclarant the entity to save
     * @return the persisted entity
     */
    AgentDeclarant save(AgentDeclarant agentDeclarant);

    /**
     * Get all the agentDeclarants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgentDeclarant> findAll(Pageable pageable);


    /**
     * Get the "id" agentDeclarant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AgentDeclarant> findOne(Long id);

    /**
     * Delete the "id" agentDeclarant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
