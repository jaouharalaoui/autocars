package ma.insea.sb.service;

import ma.insea.sb.domain.Chauffeur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Chauffeur.
 */
public interface ChauffeurService {

    /**
     * Save a chauffeur.
     *
     * @param chauffeur the entity to save
     * @return the persisted entity
     */
    Chauffeur save(Chauffeur chauffeur);

    /**
     * Get all the chauffeurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Chauffeur> findAll(Pageable pageable);

    /**
     * Get all the Chauffeur with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Chauffeur> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" chauffeur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Chauffeur> findOne(Long id);

    /**
     * Delete the "id" chauffeur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
