package ma.insea.sb.service.impl;

import ma.insea.sb.service.AgentDeclarantService;
import ma.insea.sb.domain.AgentDeclarant;
import ma.insea.sb.repository.AgentDeclarantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AgentDeclarant.
 */
@Service
@Transactional
public class AgentDeclarantServiceImpl implements AgentDeclarantService {

    private final Logger log = LoggerFactory.getLogger(AgentDeclarantServiceImpl.class);

    private final AgentDeclarantRepository agentDeclarantRepository;

    public AgentDeclarantServiceImpl(AgentDeclarantRepository agentDeclarantRepository) {
        this.agentDeclarantRepository = agentDeclarantRepository;
    }

    /**
     * Save a agentDeclarant.
     *
     * @param agentDeclarant the entity to save
     * @return the persisted entity
     */
    @Override
    public AgentDeclarant save(AgentDeclarant agentDeclarant) {
        log.debug("Request to save AgentDeclarant : {}", agentDeclarant);
        return agentDeclarantRepository.save(agentDeclarant);
    }

    /**
     * Get all the agentDeclarants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgentDeclarant> findAll(Pageable pageable) {
        log.debug("Request to get all AgentDeclarants");
        return agentDeclarantRepository.findAll(pageable);
    }


    /**
     * Get one agentDeclarant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgentDeclarant> findOne(Long id) {
        log.debug("Request to get AgentDeclarant : {}", id);
        return agentDeclarantRepository.findById(id);
    }

    /**
     * Delete the agentDeclarant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgentDeclarant : {}", id);
        agentDeclarantRepository.deleteById(id);
    }
}
