package ma.insea.sb.service.impl;

import ma.insea.sb.service.VehiculeService;
import ma.insea.sb.domain.Vehicule;
import ma.insea.sb.repository.VehiculeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Vehicule.
 */
@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

    private final Logger log = LoggerFactory.getLogger(VehiculeServiceImpl.class);

    private final VehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Save a vehicule.
     *
     * @param vehicule the entity to save
     * @return the persisted entity
     */
    @Override
    public Vehicule save(Vehicule vehicule) {
        log.debug("Request to save Vehicule : {}", vehicule);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Get all the vehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vehicule> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicules");
        return vehiculeRepository.findAll(pageable);
    }


    /**
     * Get one vehicule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> findOne(Long id) {
        log.debug("Request to get Vehicule : {}", id);
        return vehiculeRepository.findById(id);
    }

    /**
     * Delete the vehicule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicule : {}", id);
        vehiculeRepository.deleteById(id);
    }
}
