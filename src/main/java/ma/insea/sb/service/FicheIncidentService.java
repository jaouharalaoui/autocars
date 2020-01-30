package ma.insea.sb.service;

import ma.insea.sb.domain.FicheIncident;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FicheIncident.
 */
public interface FicheIncidentService {

    /**
     * Save a ficheIncident.
     *
     * @param ficheIncident the entity to save
     * @return the persisted entity
     */
    FicheIncident save(FicheIncident ficheIncident);

    /**
     * Get all the ficheIncidents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FicheIncident> findAll(Pageable pageable);


    /**
     * Get the "id" ficheIncident.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FicheIncident> findOne(Long id);

    /**
     * Delete the "id" ficheIncident.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
