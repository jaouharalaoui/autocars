package ma.insea.sb.web.rest;

import ma.insea.sb.AutocarsApp;

import ma.insea.sb.domain.AgentDeclarant;
import ma.insea.sb.repository.AgentDeclarantRepository;
import ma.insea.sb.service.AgentDeclarantService;
import ma.insea.sb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static ma.insea.sb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgentDeclarantResource REST controller.
 *
 * @see AgentDeclarantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutocarsApp.class)
public class AgentDeclarantResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    @Autowired
    private AgentDeclarantRepository agentDeclarantRepository;

    @Autowired
    private AgentDeclarantService agentDeclarantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAgentDeclarantMockMvc;

    private AgentDeclarant agentDeclarant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentDeclarantResource agentDeclarantResource = new AgentDeclarantResource(agentDeclarantService);
        this.restAgentDeclarantMockMvc = MockMvcBuilders.standaloneSetup(agentDeclarantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgentDeclarant createEntity(EntityManager em) {
        AgentDeclarant agentDeclarant = new AgentDeclarant()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM);
        return agentDeclarant;
    }

    @Before
    public void initTest() {
        agentDeclarant = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgentDeclarant() throws Exception {
        int databaseSizeBeforeCreate = agentDeclarantRepository.findAll().size();

        // Create the AgentDeclarant
        restAgentDeclarantMockMvc.perform(post("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentDeclarant)))
            .andExpect(status().isCreated());

        // Validate the AgentDeclarant in the database
        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeCreate + 1);
        AgentDeclarant testAgentDeclarant = agentDeclarantList.get(agentDeclarantList.size() - 1);
        assertThat(testAgentDeclarant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAgentDeclarant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void createAgentDeclarantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentDeclarantRepository.findAll().size();

        // Create the AgentDeclarant with an existing ID
        agentDeclarant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentDeclarantMockMvc.perform(post("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentDeclarant)))
            .andExpect(status().isBadRequest());

        // Validate the AgentDeclarant in the database
        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentDeclarantRepository.findAll().size();
        // set the field null
        agentDeclarant.setNom(null);

        // Create the AgentDeclarant, which fails.

        restAgentDeclarantMockMvc.perform(post("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentDeclarant)))
            .andExpect(status().isBadRequest());

        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentDeclarantRepository.findAll().size();
        // set the field null
        agentDeclarant.setPrenom(null);

        // Create the AgentDeclarant, which fails.

        restAgentDeclarantMockMvc.perform(post("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentDeclarant)))
            .andExpect(status().isBadRequest());

        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgentDeclarants() throws Exception {
        // Initialize the database
        agentDeclarantRepository.saveAndFlush(agentDeclarant);

        // Get all the agentDeclarantList
        restAgentDeclarantMockMvc.perform(get("/api/agent-declarants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentDeclarant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())));
    }
    
    @Test
    @Transactional
    public void getAgentDeclarant() throws Exception {
        // Initialize the database
        agentDeclarantRepository.saveAndFlush(agentDeclarant);

        // Get the agentDeclarant
        restAgentDeclarantMockMvc.perform(get("/api/agent-declarants/{id}", agentDeclarant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agentDeclarant.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgentDeclarant() throws Exception {
        // Get the agentDeclarant
        restAgentDeclarantMockMvc.perform(get("/api/agent-declarants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgentDeclarant() throws Exception {
        // Initialize the database
        agentDeclarantService.save(agentDeclarant);

        int databaseSizeBeforeUpdate = agentDeclarantRepository.findAll().size();

        // Update the agentDeclarant
        AgentDeclarant updatedAgentDeclarant = agentDeclarantRepository.findById(agentDeclarant.getId()).get();
        // Disconnect from session so that the updates on updatedAgentDeclarant are not directly saved in db
        em.detach(updatedAgentDeclarant);
        updatedAgentDeclarant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);

        restAgentDeclarantMockMvc.perform(put("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgentDeclarant)))
            .andExpect(status().isOk());

        // Validate the AgentDeclarant in the database
        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeUpdate);
        AgentDeclarant testAgentDeclarant = agentDeclarantList.get(agentDeclarantList.size() - 1);
        assertThat(testAgentDeclarant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgentDeclarant.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void updateNonExistingAgentDeclarant() throws Exception {
        int databaseSizeBeforeUpdate = agentDeclarantRepository.findAll().size();

        // Create the AgentDeclarant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentDeclarantMockMvc.perform(put("/api/agent-declarants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentDeclarant)))
            .andExpect(status().isBadRequest());

        // Validate the AgentDeclarant in the database
        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgentDeclarant() throws Exception {
        // Initialize the database
        agentDeclarantService.save(agentDeclarant);

        int databaseSizeBeforeDelete = agentDeclarantRepository.findAll().size();

        // Delete the agentDeclarant
        restAgentDeclarantMockMvc.perform(delete("/api/agent-declarants/{id}", agentDeclarant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgentDeclarant> agentDeclarantList = agentDeclarantRepository.findAll();
        assertThat(agentDeclarantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentDeclarant.class);
        AgentDeclarant agentDeclarant1 = new AgentDeclarant();
        agentDeclarant1.setId(1L);
        AgentDeclarant agentDeclarant2 = new AgentDeclarant();
        agentDeclarant2.setId(agentDeclarant1.getId());
        assertThat(agentDeclarant1).isEqualTo(agentDeclarant2);
        agentDeclarant2.setId(2L);
        assertThat(agentDeclarant1).isNotEqualTo(agentDeclarant2);
        agentDeclarant1.setId(null);
        assertThat(agentDeclarant1).isNotEqualTo(agentDeclarant2);
    }
}
