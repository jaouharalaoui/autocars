package ma.insea.sb.web.rest;

import ma.insea.sb.AutocarsApp;

import ma.insea.sb.domain.Vehicule;
import ma.insea.sb.repository.VehiculeRepository;
import ma.insea.sb.service.VehiculeService;
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
 * Test class for the VehiculeResource REST controller.
 *
 * @see VehiculeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutocarsApp.class)
public class VehiculeResourceIntTest {

    private static final String DEFAULT_CODE_INTERN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_INTERN = "BBBBBBBBBB";

    private static final String DEFAULT_IMMATRICULATION = "AAAAAAAAAA";
    private static final String UPDATED_IMMATRICULATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_MISE_EN_CIRCULATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_MISE_EN_CIRCULATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private VehiculeService vehiculeService;

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

    private MockMvc restVehiculeMockMvc;

    private Vehicule vehicule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehiculeResource vehiculeResource = new VehiculeResource(vehiculeService);
        this.restVehiculeMockMvc = MockMvcBuilders.standaloneSetup(vehiculeResource)
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
    public static Vehicule createEntity(EntityManager em) {
        Vehicule vehicule = new Vehicule()
            .codeIntern(DEFAULT_CODE_INTERN)
            .immatriculation(DEFAULT_IMMATRICULATION)
            .dateDeMiseEnCirculation(DEFAULT_DATE_DE_MISE_EN_CIRCULATION);
        return vehicule;
    }

    @Before
    public void initTest() {
        vehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicule() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isCreated());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getCodeIntern()).isEqualTo(DEFAULT_CODE_INTERN);
        assertThat(testVehicule.getImmatriculation()).isEqualTo(DEFAULT_IMMATRICULATION);
        assertThat(testVehicule.getDateDeMiseEnCirculation()).isEqualTo(DEFAULT_DATE_DE_MISE_EN_CIRCULATION);
    }

    @Test
    @Transactional
    public void createVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule with an existing ID
        vehicule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeInternIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculeRepository.findAll().size();
        // set the field null
        vehicule.setCodeIntern(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImmatriculationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculeRepository.findAll().size();
        // set the field null
        vehicule.setImmatriculation(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDeMiseEnCirculationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculeRepository.findAll().size();
        // set the field null
        vehicule.setDateDeMiseEnCirculation(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicules() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList
        restVehiculeMockMvc.perform(get("/api/vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeIntern").value(hasItem(DEFAULT_CODE_INTERN.toString())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
            .andExpect(jsonPath("$.[*].dateDeMiseEnCirculation").value(hasItem(DEFAULT_DATE_DE_MISE_EN_CIRCULATION.toString())));
    }
    
    @Test
    @Transactional
    public void getVehicule() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", vehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicule.getId().intValue()))
            .andExpect(jsonPath("$.codeIntern").value(DEFAULT_CODE_INTERN.toString()))
            .andExpect(jsonPath("$.immatriculation").value(DEFAULT_IMMATRICULATION.toString()))
            .andExpect(jsonPath("$.dateDeMiseEnCirculation").value(DEFAULT_DATE_DE_MISE_EN_CIRCULATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicule() throws Exception {
        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicule() throws Exception {
        // Initialize the database
        vehiculeService.save(vehicule);

        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Update the vehicule
        Vehicule updatedVehicule = vehiculeRepository.findById(vehicule.getId()).get();
        // Disconnect from session so that the updates on updatedVehicule are not directly saved in db
        em.detach(updatedVehicule);
        updatedVehicule
            .codeIntern(UPDATED_CODE_INTERN)
            .immatriculation(UPDATED_IMMATRICULATION)
            .dateDeMiseEnCirculation(UPDATED_DATE_DE_MISE_EN_CIRCULATION);

        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicule)))
            .andExpect(status().isOk());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getCodeIntern()).isEqualTo(UPDATED_CODE_INTERN);
        assertThat(testVehicule.getImmatriculation()).isEqualTo(UPDATED_IMMATRICULATION);
        assertThat(testVehicule.getDateDeMiseEnCirculation()).isEqualTo(UPDATED_DATE_DE_MISE_EN_CIRCULATION);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicule() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Create the Vehicule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicule() throws Exception {
        // Initialize the database
        vehiculeService.save(vehicule);

        int databaseSizeBeforeDelete = vehiculeRepository.findAll().size();

        // Delete the vehicule
        restVehiculeMockMvc.perform(delete("/api/vehicules/{id}", vehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicule.class);
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setId(1L);
        Vehicule vehicule2 = new Vehicule();
        vehicule2.setId(vehicule1.getId());
        assertThat(vehicule1).isEqualTo(vehicule2);
        vehicule2.setId(2L);
        assertThat(vehicule1).isNotEqualTo(vehicule2);
        vehicule1.setId(null);
        assertThat(vehicule1).isNotEqualTo(vehicule2);
    }
}
