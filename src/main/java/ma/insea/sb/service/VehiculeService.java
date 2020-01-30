package ma.insea.sb.service;

import ma.insea.sb.domain.Vehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Vehicule.
 */
public interface VehiculeService {

    /**
     * Save a vehicule.
     *
     * @param vehicule the entity to save
     * @return the persisted entity
     */
    Vehicule save(Vehicule vehicule);

    /**
     * Get all the vehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Vehicule> findAll(Pageable pageable);


    /**
     * Get the "id" vehicule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Vehicule> findOne(Long id);

    /**
     * Delete the "id" vehicule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
