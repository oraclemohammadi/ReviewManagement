package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.PaymentExecution;
import com.milo.amz.review.repository.PaymentExecutionRepository;
import com.milo.amz.review.service.PaymentExecutionService;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;
import com.milo.amz.review.service.mapper.PaymentExecutionMapper;

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
 * Test class for the PaymentExecutionResource REST controller.
 *
 * @see PaymentExecutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class PaymentExecutionResourceIntTest {

    private static final Long DEFAULT_PAYMENT_AMOUNT = 1L;
    private static final Long UPDATED_PAYMENT_AMOUNT = 2L;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    @Inject
    private PaymentExecutionRepository paymentExecutionRepository;

    @Inject
    private PaymentExecutionMapper paymentExecutionMapper;

    @Inject
    private PaymentExecutionService paymentExecutionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPaymentExecutionMockMvc;

    private PaymentExecution paymentExecution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentExecutionResource paymentExecutionResource = new PaymentExecutionResource();
        ReflectionTestUtils.setField(paymentExecutionResource, "paymentExecutionService", paymentExecutionService);
        this.restPaymentExecutionMockMvc = MockMvcBuilders.standaloneSetup(paymentExecutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentExecution createEntity(EntityManager em) {
        PaymentExecution paymentExecution = new PaymentExecution()
                .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
                .currencyCode(DEFAULT_CURRENCY_CODE)
                .paymentMethod(DEFAULT_PAYMENT_METHOD);
        return paymentExecution;
    }

    @Before
    public void initTest() {
        paymentExecution = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentExecution() throws Exception {
        int databaseSizeBeforeCreate = paymentExecutionRepository.findAll().size();

        // Create the PaymentExecution
        PaymentExecutionDTO paymentExecutionDTO = paymentExecutionMapper.paymentExecutionToPaymentExecutionDTO(paymentExecution);

        restPaymentExecutionMockMvc.perform(post("/api/payment-executions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentExecutionDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentExecution in the database
        List<PaymentExecution> paymentExecutions = paymentExecutionRepository.findAll();
        assertThat(paymentExecutions).hasSize(databaseSizeBeforeCreate + 1);
        PaymentExecution testPaymentExecution = paymentExecutions.get(paymentExecutions.size() - 1);
        assertThat(testPaymentExecution.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaymentExecution.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPaymentExecution.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllPaymentExecutions() throws Exception {
        // Initialize the database
        paymentExecutionRepository.saveAndFlush(paymentExecution);

        // Get all the paymentExecutions
        restPaymentExecutionMockMvc.perform(get("/api/payment-executions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentExecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())));
    }

    @Test
    @Transactional
    public void getPaymentExecution() throws Exception {
        // Initialize the database
        paymentExecutionRepository.saveAndFlush(paymentExecution);

        // Get the paymentExecution
        restPaymentExecutionMockMvc.perform(get("/api/payment-executions/{id}", paymentExecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentExecution.getId().intValue()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentExecution() throws Exception {
        // Get the paymentExecution
        restPaymentExecutionMockMvc.perform(get("/api/payment-executions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentExecution() throws Exception {
        // Initialize the database
        paymentExecutionRepository.saveAndFlush(paymentExecution);
        int databaseSizeBeforeUpdate = paymentExecutionRepository.findAll().size();

        // Update the paymentExecution
        PaymentExecution updatedPaymentExecution = paymentExecutionRepository.findOne(paymentExecution.getId());
        updatedPaymentExecution
                .paymentAmount(UPDATED_PAYMENT_AMOUNT)
                .currencyCode(UPDATED_CURRENCY_CODE)
                .paymentMethod(UPDATED_PAYMENT_METHOD);
        PaymentExecutionDTO paymentExecutionDTO = paymentExecutionMapper.paymentExecutionToPaymentExecutionDTO(updatedPaymentExecution);

        restPaymentExecutionMockMvc.perform(put("/api/payment-executions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentExecutionDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentExecution in the database
        List<PaymentExecution> paymentExecutions = paymentExecutionRepository.findAll();
        assertThat(paymentExecutions).hasSize(databaseSizeBeforeUpdate);
        PaymentExecution testPaymentExecution = paymentExecutions.get(paymentExecutions.size() - 1);
        assertThat(testPaymentExecution.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentExecution.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPaymentExecution.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void deletePaymentExecution() throws Exception {
        // Initialize the database
        paymentExecutionRepository.saveAndFlush(paymentExecution);
        int databaseSizeBeforeDelete = paymentExecutionRepository.findAll().size();

        // Get the paymentExecution
        restPaymentExecutionMockMvc.perform(delete("/api/payment-executions/{id}", paymentExecution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentExecution> paymentExecutions = paymentExecutionRepository.findAll();
        assertThat(paymentExecutions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
