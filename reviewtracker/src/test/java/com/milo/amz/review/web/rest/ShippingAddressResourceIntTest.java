package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.ShippingAddress;
import com.milo.amz.review.repository.ShippingAddressRepository;
import com.milo.amz.review.service.ShippingAddressService;
import com.milo.amz.review.service.dto.ShippingAddressDTO;
import com.milo.amz.review.service.mapper.ShippingAddressMapper;

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
 * Test class for the ShippingAddressResource REST controller.
 *
 * @see ShippingAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class ShippingAddressResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_OR_REGION = "AAAAAAAAAA";
    private static final String UPDATED_STATE_OR_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Inject
    private ShippingAddressRepository shippingAddressRepository;

    @Inject
    private ShippingAddressMapper shippingAddressMapper;

    @Inject
    private ShippingAddressService shippingAddressService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restShippingAddressMockMvc;

    private ShippingAddress shippingAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShippingAddressResource shippingAddressResource = new ShippingAddressResource();
        ReflectionTestUtils.setField(shippingAddressResource, "shippingAddressService", shippingAddressService);
        this.restShippingAddressMockMvc = MockMvcBuilders.standaloneSetup(shippingAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingAddress createEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
                .name(DEFAULT_NAME)
                .addressLine1(DEFAULT_ADDRESS_LINE_1)
                .addressLine2(DEFAULT_ADDRESS_LINE_2)
                .addressLine3(DEFAULT_ADDRESS_LINE_3)
                .city(DEFAULT_CITY)
                .county(DEFAULT_COUNTY)
                .postalCode(DEFAULT_POSTAL_CODE)
                .district(DEFAULT_DISTRICT)
                .stateOrRegion(DEFAULT_STATE_OR_REGION)
                .phone(DEFAULT_PHONE);
        return shippingAddress;
    }

    @Before
    public void initTest() {
        shippingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeCreate + 1);
        ShippingAddress testShippingAddress = shippingAddresses.get(shippingAddresses.size() - 1);
        assertThat(testShippingAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShippingAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testShippingAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testShippingAddress.getAddressLine3()).isEqualTo(DEFAULT_ADDRESS_LINE_3);
        assertThat(testShippingAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShippingAddress.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testShippingAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testShippingAddress.getStateOrRegion()).isEqualTo(DEFAULT_STATE_OR_REGION);
        assertThat(testShippingAddress.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setName(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setAddressLine1(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setCity(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountyIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setCounty(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistrictIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setDistrict(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateOrRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setStateOrRegion(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingAddresses() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddresses
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].addressLine3").value(hasItem(DEFAULT_ADDRESS_LINE_3.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].stateOrRegion").value(hasItem(DEFAULT_STATE_OR_REGION.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.addressLine3").value(DEFAULT_ADDRESS_LINE_3.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.stateOrRegion").value(DEFAULT_STATE_OR_REGION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingAddress() throws Exception {
        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);
        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Update the shippingAddress
        ShippingAddress updatedShippingAddress = shippingAddressRepository.findOne(shippingAddress.getId());
        updatedShippingAddress
                .name(UPDATED_NAME)
                .addressLine1(UPDATED_ADDRESS_LINE_1)
                .addressLine2(UPDATED_ADDRESS_LINE_2)
                .addressLine3(UPDATED_ADDRESS_LINE_3)
                .city(UPDATED_CITY)
                .county(UPDATED_COUNTY)
                .postalCode(UPDATED_POSTAL_CODE)
                .district(UPDATED_DISTRICT)
                .stateOrRegion(UPDATED_STATE_OR_REGION)
                .phone(UPDATED_PHONE);
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(updatedShippingAddress);

        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isOk());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeUpdate);
        ShippingAddress testShippingAddress = shippingAddresses.get(shippingAddresses.size() - 1);
        assertThat(testShippingAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShippingAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testShippingAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testShippingAddress.getAddressLine3()).isEqualTo(UPDATED_ADDRESS_LINE_3);
        assertThat(testShippingAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShippingAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testShippingAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testShippingAddress.getStateOrRegion()).isEqualTo(UPDATED_STATE_OR_REGION);
        assertThat(testShippingAddress.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deleteShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);
        int databaseSizeBeforeDelete = shippingAddressRepository.findAll().size();

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(delete("/api/shipping-addresses/{id}", shippingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        assertThat(shippingAddresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
