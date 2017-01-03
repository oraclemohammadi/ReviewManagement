package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.Promotions;
import com.milo.amz.review.repository.PromotionsRepository;
import com.milo.amz.review.service.PromotionsService;
import com.milo.amz.review.service.dto.PromotionsDTO;
import com.milo.amz.review.service.mapper.PromotionsMapper;

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
 * Test class for the PromotionsResource REST controller.
 *
 * @see PromotionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class PromotionsResourceIntTest {

    private static final String DEFAULT_PROMOTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTION_ID = "BBBBBBBBBB";

    @Inject
    private PromotionsRepository promotionsRepository;

    @Inject
    private PromotionsMapper promotionsMapper;

    @Inject
    private PromotionsService promotionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPromotionsMockMvc;

    private Promotions promotions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromotionsResource promotionsResource = new PromotionsResource();
        ReflectionTestUtils.setField(promotionsResource, "promotionsService", promotionsService);
        this.restPromotionsMockMvc = MockMvcBuilders.standaloneSetup(promotionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotions createEntity(EntityManager em) {
        Promotions promotions = new Promotions()
                .promotionId(DEFAULT_PROMOTION_ID);
        return promotions;
    }

    @Before
    public void initTest() {
        promotions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromotions() throws Exception {
        int databaseSizeBeforeCreate = promotionsRepository.findAll().size();

        // Create the Promotions
        PromotionsDTO promotionsDTO = promotionsMapper.promotionsToPromotionsDTO(promotions);

        restPromotionsMockMvc.perform(post("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Promotions in the database
        List<Promotions> promotions = promotionsRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeCreate + 1);
        Promotions testPromotions = promotions.get(promotions.size() - 1);
        assertThat(testPromotions.getPromotionId()).isEqualTo(DEFAULT_PROMOTION_ID);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionsRepository.saveAndFlush(promotions);

        // Get all the promotions
        restPromotionsMockMvc.perform(get("/api/promotions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotions.getId().intValue())))
            .andExpect(jsonPath("$.[*].promotionId").value(hasItem(DEFAULT_PROMOTION_ID.toString())));
    }

    @Test
    @Transactional
    public void getPromotions() throws Exception {
        // Initialize the database
        promotionsRepository.saveAndFlush(promotions);

        // Get the promotions
        restPromotionsMockMvc.perform(get("/api/promotions/{id}", promotions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(promotions.getId().intValue()))
            .andExpect(jsonPath("$.promotionId").value(DEFAULT_PROMOTION_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromotions() throws Exception {
        // Get the promotions
        restPromotionsMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotions() throws Exception {
        // Initialize the database
        promotionsRepository.saveAndFlush(promotions);
        int databaseSizeBeforeUpdate = promotionsRepository.findAll().size();

        // Update the promotions
        Promotions updatedPromotions = promotionsRepository.findOne(promotions.getId());
        updatedPromotions
                .promotionId(UPDATED_PROMOTION_ID);
        PromotionsDTO promotionsDTO = promotionsMapper.promotionsToPromotionsDTO(updatedPromotions);

        restPromotionsMockMvc.perform(put("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionsDTO)))
            .andExpect(status().isOk());

        // Validate the Promotions in the database
        List<Promotions> promotions = promotionsRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeUpdate);
        Promotions testPromotions = promotions.get(promotions.size() - 1);
        assertThat(testPromotions.getPromotionId()).isEqualTo(UPDATED_PROMOTION_ID);
    }

    @Test
    @Transactional
    public void deletePromotions() throws Exception {
        // Initialize the database
        promotionsRepository.saveAndFlush(promotions);
        int databaseSizeBeforeDelete = promotionsRepository.findAll().size();

        // Get the promotions
        restPromotionsMockMvc.perform(delete("/api/promotions/{id}", promotions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Promotions> promotions = promotionsRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
