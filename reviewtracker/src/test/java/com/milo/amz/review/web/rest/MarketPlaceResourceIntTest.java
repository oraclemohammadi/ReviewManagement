package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.MarketPlace;
import com.milo.amz.review.repository.MarketPlaceRepository;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.mapper.MarketPlaceMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketPlaceResource REST controller.
 *
 * @see MarketPlaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class MarketPlaceResourceIntTest {

    private static final String DEFAULT_ACCESS_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SECRECT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SECRECT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_PLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Inject
    private MarketPlaceRepository marketPlaceRepository;

    @Inject
    private MarketPlaceMapper marketPlaceMapper;

    @Inject
    private MarketPlaceService marketPlaceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMarketPlaceMockMvc;

    private MarketPlace marketPlace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketPlaceResource marketPlaceResource = new MarketPlaceResource();
        ReflectionTestUtils.setField(marketPlaceResource, "marketPlaceService", marketPlaceService);
        this.restMarketPlaceMockMvc = MockMvcBuilders.standaloneSetup(marketPlaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketPlace createEntity(EntityManager em) {
        MarketPlace marketPlace = new MarketPlace()
                .accessKey(DEFAULT_ACCESS_KEY)
                .secrectKey(DEFAULT_SECRECT_KEY)
                .sellerId(DEFAULT_SELLER_ID)
                .marketPlaceId(DEFAULT_MARKET_PLACE_ID)
                .serviceUrl(DEFAULT_SERVICE_URL)
                .activated(DEFAULT_ACTIVATED);
        return marketPlace;
    }

    @Before
    public void initTest() {
        marketPlace = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketPlace() throws Exception {
        int databaseSizeBeforeCreate = marketPlaceRepository.findAll().size();

        // Create the MarketPlace
        MarketPlaceDTO marketPlaceDTO = marketPlaceMapper.marketPlaceToMarketPlaceDTO(marketPlace);

        restMarketPlaceMockMvc.perform(post("/api/market-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketPlaceDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketPlace in the database
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        assertThat(marketPlaces).hasSize(databaseSizeBeforeCreate + 1);
        MarketPlace testMarketPlace = marketPlaces.get(marketPlaces.size() - 1);
        assertThat(testMarketPlace.getAccessKey()).isEqualTo(DEFAULT_ACCESS_KEY);
        assertThat(testMarketPlace.getSecrectKey()).isEqualTo(DEFAULT_SECRECT_KEY);
        assertThat(testMarketPlace.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testMarketPlace.getMarketPlaceId()).isEqualTo(DEFAULT_MARKET_PLACE_ID);
        assertThat(testMarketPlace.getServiceUrl()).isEqualTo(DEFAULT_SERVICE_URL);
        assertThat(testMarketPlace.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllMarketPlaces() throws Exception {
        // Initialize the database
        marketPlaceRepository.saveAndFlush(marketPlace);

        // Get all the marketPlaces
        restMarketPlaceMockMvc.perform(get("/api/market-places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].accessKey").value(hasItem(DEFAULT_ACCESS_KEY.toString())))
            .andExpect(jsonPath("$.[*].secrectKey").value(hasItem(DEFAULT_SECRECT_KEY.toString())))
            .andExpect(jsonPath("$.[*].sellerId").value(hasItem(DEFAULT_SELLER_ID.toString())))
            .andExpect(jsonPath("$.[*].marketPlaceId").value(hasItem(DEFAULT_MARKET_PLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].serviceUrl").value(hasItem(DEFAULT_SERVICE_URL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMarketPlace() throws Exception {
        // Initialize the database
        marketPlaceRepository.saveAndFlush(marketPlace);

        // Get the marketPlace
        restMarketPlaceMockMvc.perform(get("/api/market-places/{id}", marketPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketPlace.getId().intValue()))
            .andExpect(jsonPath("$.accessKey").value(DEFAULT_ACCESS_KEY.toString()))
            .andExpect(jsonPath("$.secrectKey").value(DEFAULT_SECRECT_KEY.toString()))
            .andExpect(jsonPath("$.sellerId").value(DEFAULT_SELLER_ID.toString()))
            .andExpect(jsonPath("$.marketPlaceId").value(DEFAULT_MARKET_PLACE_ID.toString()))
            .andExpect(jsonPath("$.serviceUrl").value(DEFAULT_SERVICE_URL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketPlace() throws Exception {
        // Get the marketPlace
        restMarketPlaceMockMvc.perform(get("/api/market-places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketPlace() throws Exception {
        // Initialize the database
        marketPlaceRepository.saveAndFlush(marketPlace);
        int databaseSizeBeforeUpdate = marketPlaceRepository.findAll().size();

        // Update the marketPlace
        MarketPlace updatedMarketPlace = marketPlaceRepository.findOne(marketPlace.getId());
        updatedMarketPlace
                .accessKey(UPDATED_ACCESS_KEY)
                .secrectKey(UPDATED_SECRECT_KEY)
                .sellerId(UPDATED_SELLER_ID)
                .marketPlaceId(UPDATED_MARKET_PLACE_ID)
                .serviceUrl(UPDATED_SERVICE_URL)
                .activated(UPDATED_ACTIVATED);
        MarketPlaceDTO marketPlaceDTO = marketPlaceMapper.marketPlaceToMarketPlaceDTO(updatedMarketPlace);

        restMarketPlaceMockMvc.perform(put("/api/market-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketPlaceDTO)))
            .andExpect(status().isOk());

        // Validate the MarketPlace in the database
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        assertThat(marketPlaces).hasSize(databaseSizeBeforeUpdate);
        MarketPlace testMarketPlace = marketPlaces.get(marketPlaces.size() - 1);
        assertThat(testMarketPlace.getAccessKey()).isEqualTo(UPDATED_ACCESS_KEY);
        assertThat(testMarketPlace.getSecrectKey()).isEqualTo(UPDATED_SECRECT_KEY);
        assertThat(testMarketPlace.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testMarketPlace.getMarketPlaceId()).isEqualTo(UPDATED_MARKET_PLACE_ID);
        assertThat(testMarketPlace.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testMarketPlace.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void deleteMarketPlace() throws Exception {
        // Initialize the database
        marketPlaceRepository.saveAndFlush(marketPlace);
        int databaseSizeBeforeDelete = marketPlaceRepository.findAll().size();

        // Get the marketPlace
        restMarketPlaceMockMvc.perform(delete("/api/market-places/{id}", marketPlace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        assertThat(marketPlaces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
