package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.InvoiceData;
import com.milo.amz.review.repository.InvoiceDataRepository;
import com.milo.amz.review.service.InvoiceDataService;
import com.milo.amz.review.service.dto.InvoiceDataDTO;
import com.milo.amz.review.service.mapper.InvoiceDataMapper;

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
 * Test class for the InvoiceDataResource REST controller.
 *
 * @see InvoiceDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class InvoiceDataResourceIntTest {

    private static final String DEFAULT_INVOICE_REQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_REQUIREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_SELECTED_INVOICE_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_SELECTED_INVOICE_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_INFORMATION = "BBBBBBBBBB";

    @Inject
    private InvoiceDataRepository invoiceDataRepository;

    @Inject
    private InvoiceDataMapper invoiceDataMapper;

    @Inject
    private InvoiceDataService invoiceDataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInvoiceDataMockMvc;

    private InvoiceData invoiceData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceDataResource invoiceDataResource = new InvoiceDataResource();
        ReflectionTestUtils.setField(invoiceDataResource, "invoiceDataService", invoiceDataService);
        this.restInvoiceDataMockMvc = MockMvcBuilders.standaloneSetup(invoiceDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceData createEntity(EntityManager em) {
        InvoiceData invoiceData = new InvoiceData()
                .invoiceRequirement(DEFAULT_INVOICE_REQUIREMENT)
                .buyerSelectedInvoiceCategory(DEFAULT_BUYER_SELECTED_INVOICE_CATEGORY)
                .invoiceTitle(DEFAULT_INVOICE_TITLE)
                .invoiceInformation(DEFAULT_INVOICE_INFORMATION);
        return invoiceData;
    }

    @Before
    public void initTest() {
        invoiceData = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceData() throws Exception {
        int databaseSizeBeforeCreate = invoiceDataRepository.findAll().size();

        // Create the InvoiceData
        InvoiceDataDTO invoiceDataDTO = invoiceDataMapper.invoiceDataToInvoiceDataDTO(invoiceData);

        restInvoiceDataMockMvc.perform(post("/api/invoice-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDataDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceData in the database
        List<InvoiceData> invoiceData = invoiceDataRepository.findAll();
        assertThat(invoiceData).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceData testInvoiceData = invoiceData.get(invoiceData.size() - 1);
        assertThat(testInvoiceData.getInvoiceRequirement()).isEqualTo(DEFAULT_INVOICE_REQUIREMENT);
        assertThat(testInvoiceData.getBuyerSelectedInvoiceCategory()).isEqualTo(DEFAULT_BUYER_SELECTED_INVOICE_CATEGORY);
        assertThat(testInvoiceData.getInvoiceTitle()).isEqualTo(DEFAULT_INVOICE_TITLE);
        assertThat(testInvoiceData.getInvoiceInformation()).isEqualTo(DEFAULT_INVOICE_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllInvoiceData() throws Exception {
        // Initialize the database
        invoiceDataRepository.saveAndFlush(invoiceData);

        // Get all the invoiceData
        restInvoiceDataMockMvc.perform(get("/api/invoice-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceData.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceRequirement").value(hasItem(DEFAULT_INVOICE_REQUIREMENT.toString())))
            .andExpect(jsonPath("$.[*].buyerSelectedInvoiceCategory").value(hasItem(DEFAULT_BUYER_SELECTED_INVOICE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].invoiceTitle").value(hasItem(DEFAULT_INVOICE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].invoiceInformation").value(hasItem(DEFAULT_INVOICE_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void getInvoiceData() throws Exception {
        // Initialize the database
        invoiceDataRepository.saveAndFlush(invoiceData);

        // Get the invoiceData
        restInvoiceDataMockMvc.perform(get("/api/invoice-data/{id}", invoiceData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceData.getId().intValue()))
            .andExpect(jsonPath("$.invoiceRequirement").value(DEFAULT_INVOICE_REQUIREMENT.toString()))
            .andExpect(jsonPath("$.buyerSelectedInvoiceCategory").value(DEFAULT_BUYER_SELECTED_INVOICE_CATEGORY.toString()))
            .andExpect(jsonPath("$.invoiceTitle").value(DEFAULT_INVOICE_TITLE.toString()))
            .andExpect(jsonPath("$.invoiceInformation").value(DEFAULT_INVOICE_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceData() throws Exception {
        // Get the invoiceData
        restInvoiceDataMockMvc.perform(get("/api/invoice-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceData() throws Exception {
        // Initialize the database
        invoiceDataRepository.saveAndFlush(invoiceData);
        int databaseSizeBeforeUpdate = invoiceDataRepository.findAll().size();

        // Update the invoiceData
        InvoiceData updatedInvoiceData = invoiceDataRepository.findOne(invoiceData.getId());
        updatedInvoiceData
                .invoiceRequirement(UPDATED_INVOICE_REQUIREMENT)
                .buyerSelectedInvoiceCategory(UPDATED_BUYER_SELECTED_INVOICE_CATEGORY)
                .invoiceTitle(UPDATED_INVOICE_TITLE)
                .invoiceInformation(UPDATED_INVOICE_INFORMATION);
        InvoiceDataDTO invoiceDataDTO = invoiceDataMapper.invoiceDataToInvoiceDataDTO(updatedInvoiceData);

        restInvoiceDataMockMvc.perform(put("/api/invoice-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDataDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceData in the database
        List<InvoiceData> invoiceData = invoiceDataRepository.findAll();
        assertThat(invoiceData).hasSize(databaseSizeBeforeUpdate);
        InvoiceData testInvoiceData = invoiceData.get(invoiceData.size() - 1);
        assertThat(testInvoiceData.getInvoiceRequirement()).isEqualTo(UPDATED_INVOICE_REQUIREMENT);
        assertThat(testInvoiceData.getBuyerSelectedInvoiceCategory()).isEqualTo(UPDATED_BUYER_SELECTED_INVOICE_CATEGORY);
        assertThat(testInvoiceData.getInvoiceTitle()).isEqualTo(UPDATED_INVOICE_TITLE);
        assertThat(testInvoiceData.getInvoiceInformation()).isEqualTo(UPDATED_INVOICE_INFORMATION);
    }

    @Test
    @Transactional
    public void deleteInvoiceData() throws Exception {
        // Initialize the database
        invoiceDataRepository.saveAndFlush(invoiceData);
        int databaseSizeBeforeDelete = invoiceDataRepository.findAll().size();

        // Get the invoiceData
        restInvoiceDataMockMvc.perform(delete("/api/invoice-data/{id}", invoiceData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceData> invoiceData = invoiceDataRepository.findAll();
        assertThat(invoiceData).hasSize(databaseSizeBeforeDelete - 1);
    }
}
