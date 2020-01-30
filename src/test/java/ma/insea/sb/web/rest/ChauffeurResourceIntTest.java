package ma.insea.sb.web.rest;

import ma.insea.sb.AutocarsApp;

import ma.insea.sb.domain.Chauffeur;
import ma.insea.sb.repository.ChauffeurRepository;
import ma.insea.sb.service.ChauffeurService;
import ma.insea.sb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static ma.insea.sb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChauffeurResource REST controller.
 *
 * @see ChauffeurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutocarsApp.class)
public class ChauffeurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @Mock
    private ChauffeurRepository chauffeurRepositoryMock;

    @Mock
    private ChauffeurService chauffeurServiceMock;

    @Autowired
    private ChauffeurService chauffeurService;

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

    private MockMvc restChauffeurMockMvc;

    private Chauffeur chauffeur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChauffeurResource chauffeurResource = new ChauffeurResource(chauffeurService);
        this.restChauffeurMockMvc = MockMvcBuilders.standaloneSetup(chauffeurResource)
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
    public static Chauffeur createEntity(EntityManager em) {
        Chauffeur chauffeur = new Chauffeur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE);
        return chauffeur;
    }

    @Before
    public void initTest() {
        chauffeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createChauffeur() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();

        // Create the Chauffeur
        restChauffeurMockMvc.perform(post("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isCreated());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate + 1);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testChauffeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testChauffeur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createChauffeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();

        // Create the Chauffeur with an existing ID
        chauffeur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChauffeurMockMvc.perform(post("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setNom(null);

        // Create the Chauffeur, which fails.

        restChauffeurMockMvc.perform(post("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setPrenom(null);

        // Create the Chauffeur, which fails.

        restChauffeurMockMvc.perform(post("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setTelephone(null);

        // Create the Chauffeur, which fails.

        restChauffeurMockMvc.perform(post("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChauffeurs() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList
        restChauffeurMockMvc.perform(get("/api/chauffeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chauffeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllChauffeursWithEagerRelationshipsIsEnabled() throws Exception {
        ChauffeurResource chauffeurResource = new ChauffeurResource(chauffeurServiceMock);
        when(chauffeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restChauffeurMockMvc = MockMvcBuilders.standaloneSetup(chauffeurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChauffeurMockMvc.perform(get("/api/chauffeurs?eagerload=true"))
        .andExpect(status().isOk());

        verify(chauffeurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChauffeursWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChauffeurResource chauffeurResource = new ChauffeurResource(chauffeurServiceMock);
            when(chauffeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restChauffeurMockMvc = MockMvcBuilders.standaloneSetup(chauffeurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChauffeurMockMvc.perform(get("/api/chauffeurs?eagerload=true"))
        .andExpect(status().isOk());

            verify(chauffeurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", chauffeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chauffeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChauffeur() throws Exception {
        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChauffeur() throws Exception {
        // Initialize the database
        chauffeurService.save(chauffeur);

        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur
        Chauffeur updatedChauffeur = chauffeurRepository.findById(chauffeur.getId()).get();
        // Disconnect from session so that the updates on updatedChauffeur are not directly saved in db
        em.detach(updatedChauffeur);
        updatedChauffeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE);

        restChauffeurMockMvc.perform(put("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChauffeur)))
            .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testChauffeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testChauffeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Create the Chauffeur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChauffeurMockMvc.perform(put("/api/chauffeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChauffeur() throws Exception {
        // Initialize the database
        chauffeurService.save(chauffeur);

        int databaseSizeBeforeDelete = chauffeurRepository.findAll().size();

        // Delete the chauffeur
        restChauffeurMockMvc.perform(delete("/api/chauffeurs/{id}", chauffeur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chauffeur.class);
        Chauffeur chauffeur1 = new Chauffeur();
        chauffeur1.setId(1L);
        Chauffeur chauffeur2 = new Chauffeur();
        chauffeur2.setId(chauffeur1.getId());
        assertThat(chauffeur1).isEqualTo(chauffeur2);
        chauffeur2.setId(2L);
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
        chauffeur1.setId(null);
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
    }
}
