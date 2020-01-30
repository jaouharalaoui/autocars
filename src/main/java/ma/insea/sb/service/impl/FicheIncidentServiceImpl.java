package ma.insea.sb.service.impl;

import ma.insea.sb.service.FicheIncidentService;
import ma.insea.sb.domain.FicheIncident;
import ma.insea.sb.repository.FicheIncidentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing FicheIncident.
 */
@Service
@Transactional
public class FicheIncidentServiceImpl implements FicheIncidentService {

    private final Logger log = LoggerFactory.getLogger(FicheIncidentServiceImpl.class);

    private final FicheIncidentRepository ficheIncidentRepository;

    public FicheIncidentServiceImpl(FicheIncidentRepository ficheIncidentRepository) {
        this.ficheIncidentRepository = ficheIncidentRepository;
    }

    /**
     * Save a ficheIncident.
     *
     * @param ficheIncident the entity to save
     * @return the persisted entity
     */
    @Override
    public FicheIncident save(FicheIncident ficheIncident) {
        log.debug("Request to save FicheIncident : {}", ficheIncident);
        return ficheIncidentRepository.save(ficheIncident);
    }

    /**
     * Get all the ficheIncidents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FicheIncident> findAll(Pageable pageable) {
        log.debug("Request to get all FicheIncidents");
        return ficheIncidentRepository.findAll(pageable);
    }


    /**
     * Get one ficheIncident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FicheIncident> findOne(Long id) {
        log.debug("Request to get FicheIncident : {}", id);
        return ficheIncidentRepository.findById(id);
    }

    /**
     * Delete the ficheIncident by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FicheIncident : {}", id);
        ficheIncidentRepository.deleteById(id);
    }
}
