package ma.insea.sb.service.impl;

import ma.insea.sb.service.ChauffeurService;
import ma.insea.sb.domain.Chauffeur;
import ma.insea.sb.repository.ChauffeurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Chauffeur.
 */
@Service
@Transactional
public class ChauffeurServiceImpl implements ChauffeurService {

    private final Logger log = LoggerFactory.getLogger(ChauffeurServiceImpl.class);

    private final ChauffeurRepository chauffeurRepository;

    public ChauffeurServiceImpl(ChauffeurRepository chauffeurRepository) {
        this.chauffeurRepository = chauffeurRepository;
    }

    /**
     * Save a chauffeur.
     *
     * @param chauffeur the entity to save
     * @return the persisted entity
     */
    @Override
    public Chauffeur save(Chauffeur chauffeur) {
        log.debug("Request to save Chauffeur : {}", chauffeur);
        return chauffeurRepository.save(chauffeur);
    }

    /**
     * Get all the chauffeurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Chauffeur> findAll(Pageable pageable) {
        log.debug("Request to get all Chauffeurs");
        return chauffeurRepository.findAll(pageable);
    }

    /**
     * Get all the Chauffeur with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Chauffeur> findAllWithEagerRelationships(Pageable pageable) {
        return chauffeurRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one chauffeur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chauffeur> findOne(Long id) {
        log.debug("Request to get Chauffeur : {}", id);
        return chauffeurRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the chauffeur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chauffeur : {}", id);
        chauffeurRepository.deleteById(id);
    }
}
