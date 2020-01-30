package ma.insea.sb.web.rest;

import ma.insea.sb.AutocarsApp;

import ma.insea.sb.domain.FicheIncident;
import ma.insea.sb.repository.FicheIncidentRepository;
import ma.insea.sb.service.FicheIncidentService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ma.insea.sb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FicheIncidentResource REST controller.
 *
 * @see FicheIncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutocarsApp.class)
public class FicheIncidentResourceIntTest {

    private static final LocalDate DEFAULT_DATE_INCIDENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INCIDENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_FICHE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FICHE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INCIDENT_CRITIQUE = false;
    private static final Boolean UPDATED_INCIDENT_CRITIQUE = true;

    private static final String DEFAULT_LIEU_INCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_INCIDENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_VOYAGEUR = 1;
    private static final Integer UPDATED_NOMBRE_VOYAGEUR = 2;

    private static final String DEFAULT_DESCRIPTION_INCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_INCIDENT = "BBBBBBBBBB";

    @Autowired
    private FicheIncidentRepository ficheIncidentRepository;

    @Autowired
    private FicheIncidentService ficheIncidentService;

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

    private MockMvc restFicheIncidentMockMvc;

    private FicheIncident ficheIncident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FicheIncidentResource ficheIncidentResource = new FicheIncidentResource(ficheIncidentService);
        this.restFicheIncidentMockMvc = MockMvcBuilders.standaloneSetup(ficheIncidentResource)
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
    public static FicheIncident createEntity(EntityManager em) {
        FicheIncident ficheIncident = new FicheIncident()
            .dateIncident(DEFAULT_DATE_INCIDENT)
            .numeroFiche(DEFAULT_NUMERO_FICHE)
            .incidentCritique(DEFAULT_INCIDENT_CRITIQUE)
            .lieuIncident(DEFAULT_LIEU_INCIDENT)
            .nombreVoyageur(DEFAULT_NOMBRE_VOYAGEUR)
            .descriptionIncident(DEFAULT_DESCRIPTION_INCIDENT);
        return ficheIncident;
    }

    @Before
    public void initTest() {
        ficheIncident = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicheIncident() throws Exception {
        int databaseSizeBeforeCreate = ficheIncidentRepository.findAll().size();

        // Create the FicheIncident
        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isCreated());

        // Validate the FicheIncident in the database
        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeCreate + 1);
        FicheIncident testFicheIncident = ficheIncidentList.get(ficheIncidentList.size() - 1);
        assertThat(testFicheIncident.getDateIncident()).isEqualTo(DEFAULT_DATE_INCIDENT);
        assertThat(testFicheIncident.getNumeroFiche()).isEqualTo(DEFAULT_NUMERO_FICHE);
        assertThat(testFicheIncident.isIncidentCritique()).isEqualTo(DEFAULT_INCIDENT_CRITIQUE);
        assertThat(testFicheIncident.getLieuIncident()).isEqualTo(DEFAULT_LIEU_INCIDENT);
        assertThat(testFicheIncident.getNombreVoyageur()).isEqualTo(DEFAULT_NOMBRE_VOYAGEUR);
        assertThat(testFicheIncident.getDescriptionIncident()).isEqualTo(DEFAULT_DESCRIPTION_INCIDENT);
    }

    @Test
    @Transactional
    public void createFicheIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheIncidentRepository.findAll().size();

        // Create the FicheIncident with an existing ID
        ficheIncident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        // Validate the FicheIncident in the database
        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheIncidentRepository.findAll().size();
        // set the field null
        ficheIncident.setDateIncident(null);

        // Create the FicheIncident, which fails.

        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroFicheIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheIncidentRepository.findAll().size();
        // set the field null
        ficheIncident.setNumeroFiche(null);

        // Create the FicheIncident, which fails.

        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentCritiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheIncidentRepository.findAll().size();
        // set the field null
        ficheIncident.setIncidentCritique(null);

        // Create the FicheIncident, which fails.

        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheIncidentRepository.findAll().size();
        // set the field null
        ficheIncident.setLieuIncident(null);

        // Create the FicheIncident, which fails.

        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreVoyageurIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheIncidentRepository.findAll().size();
        // set the field null
        ficheIncident.setNombreVoyageur(null);

        // Create the FicheIncident, which fails.

        restFicheIncidentMockMvc.perform(post("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFicheIncidents() throws Exception {
        // Initialize the database
        ficheIncidentRepository.saveAndFlush(ficheIncident);

        // Get all the ficheIncidentList
        restFicheIncidentMockMvc.perform(get("/api/fiche-incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateIncident").value(hasItem(DEFAULT_DATE_INCIDENT.toString())))
            .andExpect(jsonPath("$.[*].numeroFiche").value(hasItem(DEFAULT_NUMERO_FICHE.toString())))
            .andExpect(jsonPath("$.[*].incidentCritique").value(hasItem(DEFAULT_INCIDENT_CRITIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].lieuIncident").value(hasItem(DEFAULT_LIEU_INCIDENT.toString())))
            .andExpect(jsonPath("$.[*].nombreVoyageur").value(hasItem(DEFAULT_NOMBRE_VOYAGEUR)))
            .andExpect(jsonPath("$.[*].descriptionIncident").value(hasItem(DEFAULT_DESCRIPTION_INCIDENT.toString())));
    }
    
    @Test
    @Transactional
    public void getFicheIncident() throws Exception {
        // Initialize the database
        ficheIncidentRepository.saveAndFlush(ficheIncident);

        // Get the ficheIncident
        restFicheIncidentMockMvc.perform(get("/api/fiche-incidents/{id}", ficheIncident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheIncident.getId().intValue()))
            .andExpect(jsonPath("$.dateIncident").value(DEFAULT_DATE_INCIDENT.toString()))
            .andExpect(jsonPath("$.numeroFiche").value(DEFAULT_NUMERO_FICHE.toString()))
            .andExpect(jsonPath("$.incidentCritique").value(DEFAULT_INCIDENT_CRITIQUE.booleanValue()))
            .andExpect(jsonPath("$.lieuIncident").value(DEFAULT_LIEU_INCIDENT.toString()))
            .andExpect(jsonPath("$.nombreVoyageur").value(DEFAULT_NOMBRE_VOYAGEUR))
            .andExpect(jsonPath("$.descriptionIncident").value(DEFAULT_DESCRIPTION_INCIDENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFicheIncident() throws Exception {
        // Get the ficheIncident
        restFicheIncidentMockMvc.perform(get("/api/fiche-incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicheIncident() throws Exception {
        // Initialize the database
        ficheIncidentService.save(ficheIncident);

        int databaseSizeBeforeUpdate = ficheIncidentRepository.findAll().size();

        // Update the ficheIncident
        FicheIncident updatedFicheIncident = ficheIncidentRepository.findById(ficheIncident.getId()).get();
        // Disconnect from session so that the updates on updatedFicheIncident are not directly saved in db
        em.detach(updatedFicheIncident);
        updatedFicheIncident
            .dateIncident(UPDATED_DATE_INCIDENT)
            .numeroFiche(UPDATED_NUMERO_FICHE)
            .incidentCritique(UPDATED_INCIDENT_CRITIQUE)
            .lieuIncident(UPDATED_LIEU_INCIDENT)
            .nombreVoyageur(UPDATED_NOMBRE_VOYAGEUR)
            .descriptionIncident(UPDATED_DESCRIPTION_INCIDENT);

        restFicheIncidentMockMvc.perform(put("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheIncident)))
            .andExpect(status().isOk());

        // Validate the FicheIncident in the database
        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeUpdate);
        FicheIncident testFicheIncident = ficheIncidentList.get(ficheIncidentList.size() - 1);
        assertThat(testFicheIncident.getDateIncident()).isEqualTo(UPDATED_DATE_INCIDENT);
        assertThat(testFicheIncident.getNumeroFiche()).isEqualTo(UPDATED_NUMERO_FICHE);
        assertThat(testFicheIncident.isIncidentCritique()).isEqualTo(UPDATED_INCIDENT_CRITIQUE);
        assertThat(testFicheIncident.getLieuIncident()).isEqualTo(UPDATED_LIEU_INCIDENT);
        assertThat(testFicheIncident.getNombreVoyageur()).isEqualTo(UPDATED_NOMBRE_VOYAGEUR);
        assertThat(testFicheIncident.getDescriptionIncident()).isEqualTo(UPDATED_DESCRIPTION_INCIDENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFicheIncident() throws Exception {
        int databaseSizeBeforeUpdate = ficheIncidentRepository.findAll().size();

        // Create the FicheIncident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheIncidentMockMvc.perform(put("/api/fiche-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheIncident)))
            .andExpect(status().isBadRequest());

        // Validate the FicheIncident in the database
        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicheIncident() throws Exception {
        // Initialize the database
        ficheIncidentService.save(ficheIncident);

        int databaseSizeBeforeDelete = ficheIncidentRepository.findAll().size();

        // Delete the ficheIncident
        restFicheIncidentMockMvc.perform(delete("/api/fiche-incidents/{id}", ficheIncident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FicheIncident> ficheIncidentList = ficheIncidentRepository.findAll();
        assertThat(ficheIncidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheIncident.class);
        FicheIncident ficheIncident1 = new FicheIncident();
        ficheIncident1.setId(1L);
        FicheIncident ficheIncident2 = new FicheIncident();
        ficheIncident2.setId(ficheIncident1.getId());
        assertThat(ficheIncident1).isEqualTo(ficheIncident2);
        ficheIncident2.setId(2L);
        assertThat(ficheIncident1).isNotEqualTo(ficheIncident2);
        ficheIncident1.setId(null);
        assertThat(ficheIncident1).isNotEqualTo(ficheIncident2);
    }
}
