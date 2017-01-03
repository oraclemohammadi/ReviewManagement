package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.PurchaseOrder;
import com.milo.amz.review.repository.PurchaseOrderRepository;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.mapper.PurchaseOrderMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.milo.amz.review.domain.enumeration.PaymentMethodEnum;
/**
 * Test class for the PurchaseOrderResource REST controller.
 *
 * @see PurchaseOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class PurchaseOrderResourceIntTest {

    private static final String DEFAULT_SELLER_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_ORDER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATE_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FULFILLMENT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_FULFILLMENT_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_SERVICE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_SERVICE_LEVEL = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_TOTAL = 1L;
    private static final Long UPDATED_ORDER_TOTAL = 2L;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_NUMBER_OF_ITEMS_SHIPPED = 1L;
    private static final Long UPDATED_NUMBER_OF_ITEMS_SHIPPED = 2L;

    private static final Long DEFAULT_NUMBER_OF_ITEMS_UNSHIPPED = 1L;
    private static final Long UPDATED_NUMBER_OF_ITEMS_UNSHIPPED = 2L;

    private static final PaymentMethodEnum DEFAULT_PAYMENT_METHOD = PaymentMethodEnum.COD;
    private static final PaymentMethodEnum UPDATED_PAYMENT_METHOD = PaymentMethodEnum.CVS;

    private static final String DEFAULT_MARKETPLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARKETPLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_SERVICE_LEVEL_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_SERVICE_LEVEL_CATEGORY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHIPPED_BY_AMAZON_TFM = false;
    private static final Boolean UPDATED_SHIPPED_BY_AMAZON_TFM = true;

    private static final String DEFAULT_TFM_SHIPMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TFM_SHIPMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CBA_DISPLAYABLE_SHIPPING_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_CBA_DISPLAYABLE_SHIPPING_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EARLIEST_SHIP_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EARLIEST_SHIP_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LATEST_SHIP_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST_SHIP_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    private PurchaseOrderMapper purchaseOrderMapper;

    @Inject
    private PurchaseOrderService purchaseOrderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource();
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderService", purchaseOrderService);
        this.restPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
                .sellerOrderId(DEFAULT_SELLER_ORDER_ID)
                .purchaseLocalDate(DEFAULT_PURCHASE_LOCAL_DATE)
                .lastUpdateLocalDate(DEFAULT_LAST_UPDATE_LOCAL_DATE)
                .orderStatus(DEFAULT_ORDER_STATUS)
                .fulfillmentChannel(DEFAULT_FULFILLMENT_CHANNEL)
                .orderChannel(DEFAULT_ORDER_CHANNEL)
                .shipServiceLevel(DEFAULT_SHIP_SERVICE_LEVEL)
                .orderTotal(DEFAULT_ORDER_TOTAL)
                .currencyCode(DEFAULT_CURRENCY_CODE)
                .amount(DEFAULT_AMOUNT)
                .numberOfItemsShipped(DEFAULT_NUMBER_OF_ITEMS_SHIPPED)
                .numberOfItemsUnshipped(DEFAULT_NUMBER_OF_ITEMS_UNSHIPPED)
                .paymentMethod(DEFAULT_PAYMENT_METHOD)
                .marketplaceId(DEFAULT_MARKETPLACE_ID)
                .buyerEmail(DEFAULT_BUYER_EMAIL)
                .buyerName(DEFAULT_BUYER_NAME)
                .shipmentServiceLevelCategory(DEFAULT_SHIPMENT_SERVICE_LEVEL_CATEGORY)
                .shippedByAmazonTFM(DEFAULT_SHIPPED_BY_AMAZON_TFM)
                .tfmShipmentStatus(DEFAULT_TFM_SHIPMENT_STATUS)
                .cbaDisplayableShippingLabel(DEFAULT_CBA_DISPLAYABLE_SHIPPING_LABEL)
                .orderType(DEFAULT_ORDER_TYPE)
                .earliestShipLocalDate(DEFAULT_EARLIEST_SHIP_LOCAL_DATE)
                .latestShipLocalDate(DEFAULT_LATEST_SHIP_LOCAL_DATE);
        return purchaseOrder;
    }

    @Before
    public void initTest() {
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);

        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrders.get(purchaseOrders.size() - 1);
        assertThat(testPurchaseOrder.getSellerOrderId()).isEqualTo(DEFAULT_SELLER_ORDER_ID);
        assertThat(testPurchaseOrder.getPurchaseLocalDate()).isEqualTo(DEFAULT_PURCHASE_LOCAL_DATE);
        assertThat(testPurchaseOrder.getLastUpdateLocalDate()).isEqualTo(DEFAULT_LAST_UPDATE_LOCAL_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testPurchaseOrder.getFulfillmentChannel()).isEqualTo(DEFAULT_FULFILLMENT_CHANNEL);
        assertThat(testPurchaseOrder.getOrderChannel()).isEqualTo(DEFAULT_ORDER_CHANNEL);
        assertThat(testPurchaseOrder.getShipServiceLevel()).isEqualTo(DEFAULT_SHIP_SERVICE_LEVEL);
        assertThat(testPurchaseOrder.getOrderTotal()).isEqualTo(DEFAULT_ORDER_TOTAL);
        assertThat(testPurchaseOrder.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPurchaseOrder.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPurchaseOrder.getNumberOfItemsShipped()).isEqualTo(DEFAULT_NUMBER_OF_ITEMS_SHIPPED);
        assertThat(testPurchaseOrder.getNumberOfItemsUnshipped()).isEqualTo(DEFAULT_NUMBER_OF_ITEMS_UNSHIPPED);
        assertThat(testPurchaseOrder.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testPurchaseOrder.getMarketplaceId()).isEqualTo(DEFAULT_MARKETPLACE_ID);
        assertThat(testPurchaseOrder.getBuyerEmail()).isEqualTo(DEFAULT_BUYER_EMAIL);
        assertThat(testPurchaseOrder.getBuyerName()).isEqualTo(DEFAULT_BUYER_NAME);
        assertThat(testPurchaseOrder.getShipmentServiceLevelCategory()).isEqualTo(DEFAULT_SHIPMENT_SERVICE_LEVEL_CATEGORY);
        assertThat(testPurchaseOrder.isShippedByAmazonTFM()).isEqualTo(DEFAULT_SHIPPED_BY_AMAZON_TFM);
        assertThat(testPurchaseOrder.getTfmShipmentStatus()).isEqualTo(DEFAULT_TFM_SHIPMENT_STATUS);
        assertThat(testPurchaseOrder.getCbaDisplayableShippingLabel()).isEqualTo(DEFAULT_CBA_DISPLAYABLE_SHIPPING_LABEL);
        assertThat(testPurchaseOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testPurchaseOrder.getEarliestShipLocalDate()).isEqualTo(DEFAULT_EARLIEST_SHIP_LOCAL_DATE);
        assertThat(testPurchaseOrder.getLatestShipLocalDate()).isEqualTo(DEFAULT_LATEST_SHIP_LOCAL_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrders
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].sellerOrderId").value(hasItem(DEFAULT_SELLER_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].purchaseLocalDate").value(hasItem(DEFAULT_PURCHASE_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateLocalDate").value(hasItem(DEFAULT_LAST_UPDATE_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fulfillmentChannel").value(hasItem(DEFAULT_FULFILLMENT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].orderChannel").value(hasItem(DEFAULT_ORDER_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].shipServiceLevel").value(hasItem(DEFAULT_SHIP_SERVICE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].orderTotal").value(hasItem(DEFAULT_ORDER_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].numberOfItemsShipped").value(hasItem(DEFAULT_NUMBER_OF_ITEMS_SHIPPED.intValue())))
            .andExpect(jsonPath("$.[*].numberOfItemsUnshipped").value(hasItem(DEFAULT_NUMBER_OF_ITEMS_UNSHIPPED.intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].marketplaceId").value(hasItem(DEFAULT_MARKETPLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].buyerEmail").value(hasItem(DEFAULT_BUYER_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].buyerName").value(hasItem(DEFAULT_BUYER_NAME.toString())))
            .andExpect(jsonPath("$.[*].shipmentServiceLevelCategory").value(hasItem(DEFAULT_SHIPMENT_SERVICE_LEVEL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].shippedByAmazonTFM").value(hasItem(DEFAULT_SHIPPED_BY_AMAZON_TFM.booleanValue())))
            .andExpect(jsonPath("$.[*].tfmShipmentStatus").value(hasItem(DEFAULT_TFM_SHIPMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].cbaDisplayableShippingLabel").value(hasItem(DEFAULT_CBA_DISPLAYABLE_SHIPPING_LABEL.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].earliestShipLocalDate").value(hasItem(DEFAULT_EARLIEST_SHIP_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].latestShipLocalDate").value(hasItem(DEFAULT_LATEST_SHIP_LOCAL_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.sellerOrderId").value(DEFAULT_SELLER_ORDER_ID.toString()))
            .andExpect(jsonPath("$.purchaseLocalDate").value(DEFAULT_PURCHASE_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateLocalDate").value(DEFAULT_LAST_UPDATE_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.fulfillmentChannel").value(DEFAULT_FULFILLMENT_CHANNEL.toString()))
            .andExpect(jsonPath("$.orderChannel").value(DEFAULT_ORDER_CHANNEL.toString()))
            .andExpect(jsonPath("$.shipServiceLevel").value(DEFAULT_SHIP_SERVICE_LEVEL.toString()))
            .andExpect(jsonPath("$.orderTotal").value(DEFAULT_ORDER_TOTAL.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.numberOfItemsShipped").value(DEFAULT_NUMBER_OF_ITEMS_SHIPPED.intValue()))
            .andExpect(jsonPath("$.numberOfItemsUnshipped").value(DEFAULT_NUMBER_OF_ITEMS_UNSHIPPED.intValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.marketplaceId").value(DEFAULT_MARKETPLACE_ID.toString()))
            .andExpect(jsonPath("$.buyerEmail").value(DEFAULT_BUYER_EMAIL.toString()))
            .andExpect(jsonPath("$.buyerName").value(DEFAULT_BUYER_NAME.toString()))
            .andExpect(jsonPath("$.shipmentServiceLevelCategory").value(DEFAULT_SHIPMENT_SERVICE_LEVEL_CATEGORY.toString()))
            .andExpect(jsonPath("$.shippedByAmazonTFM").value(DEFAULT_SHIPPED_BY_AMAZON_TFM.booleanValue()))
            .andExpect(jsonPath("$.tfmShipmentStatus").value(DEFAULT_TFM_SHIPMENT_STATUS.toString()))
            .andExpect(jsonPath("$.cbaDisplayableShippingLabel").value(DEFAULT_CBA_DISPLAYABLE_SHIPPING_LABEL.toString()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.earliestShipLocalDate").value(DEFAULT_EARLIEST_SHIP_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.latestShipLocalDate").value(DEFAULT_LATEST_SHIP_LOCAL_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findOne(purchaseOrder.getId());
        updatedPurchaseOrder
                .sellerOrderId(UPDATED_SELLER_ORDER_ID)
                .purchaseLocalDate(UPDATED_PURCHASE_LOCAL_DATE)
                .lastUpdateLocalDate(UPDATED_LAST_UPDATE_LOCAL_DATE)
                .orderStatus(UPDATED_ORDER_STATUS)
                .fulfillmentChannel(UPDATED_FULFILLMENT_CHANNEL)
                .orderChannel(UPDATED_ORDER_CHANNEL)
                .shipServiceLevel(UPDATED_SHIP_SERVICE_LEVEL)
                .orderTotal(UPDATED_ORDER_TOTAL)
                .currencyCode(UPDATED_CURRENCY_CODE)
                .amount(UPDATED_AMOUNT)
                .numberOfItemsShipped(UPDATED_NUMBER_OF_ITEMS_SHIPPED)
                .numberOfItemsUnshipped(UPDATED_NUMBER_OF_ITEMS_UNSHIPPED)
                .paymentMethod(UPDATED_PAYMENT_METHOD)
                .marketplaceId(UPDATED_MARKETPLACE_ID)
                .buyerEmail(UPDATED_BUYER_EMAIL)
                .buyerName(UPDATED_BUYER_NAME)
                .shipmentServiceLevelCategory(UPDATED_SHIPMENT_SERVICE_LEVEL_CATEGORY)
                .shippedByAmazonTFM(UPDATED_SHIPPED_BY_AMAZON_TFM)
                .tfmShipmentStatus(UPDATED_TFM_SHIPMENT_STATUS)
                .cbaDisplayableShippingLabel(UPDATED_CBA_DISPLAYABLE_SHIPPING_LABEL)
                .orderType(UPDATED_ORDER_TYPE)
                .earliestShipLocalDate(UPDATED_EARLIEST_SHIP_LOCAL_DATE)
                .latestShipLocalDate(UPDATED_LATEST_SHIP_LOCAL_DATE);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(updatedPurchaseOrder);

        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrders.get(purchaseOrders.size() - 1);
        assertThat(testPurchaseOrder.getSellerOrderId()).isEqualTo(UPDATED_SELLER_ORDER_ID);
        assertThat(testPurchaseOrder.getPurchaseLocalDate()).isEqualTo(UPDATED_PURCHASE_LOCAL_DATE);
        assertThat(testPurchaseOrder.getLastUpdateLocalDate()).isEqualTo(UPDATED_LAST_UPDATE_LOCAL_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testPurchaseOrder.getFulfillmentChannel()).isEqualTo(UPDATED_FULFILLMENT_CHANNEL);
        assertThat(testPurchaseOrder.getOrderChannel()).isEqualTo(UPDATED_ORDER_CHANNEL);
        assertThat(testPurchaseOrder.getShipServiceLevel()).isEqualTo(UPDATED_SHIP_SERVICE_LEVEL);
        assertThat(testPurchaseOrder.getOrderTotal()).isEqualTo(UPDATED_ORDER_TOTAL);
        assertThat(testPurchaseOrder.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPurchaseOrder.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPurchaseOrder.getNumberOfItemsShipped()).isEqualTo(UPDATED_NUMBER_OF_ITEMS_SHIPPED);
        assertThat(testPurchaseOrder.getNumberOfItemsUnshipped()).isEqualTo(UPDATED_NUMBER_OF_ITEMS_UNSHIPPED);
        assertThat(testPurchaseOrder.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPurchaseOrder.getMarketplaceId()).isEqualTo(UPDATED_MARKETPLACE_ID);
        assertThat(testPurchaseOrder.getBuyerEmail()).isEqualTo(UPDATED_BUYER_EMAIL);
        assertThat(testPurchaseOrder.getBuyerName()).isEqualTo(UPDATED_BUYER_NAME);
        assertThat(testPurchaseOrder.getShipmentServiceLevelCategory()).isEqualTo(UPDATED_SHIPMENT_SERVICE_LEVEL_CATEGORY);
        assertThat(testPurchaseOrder.isShippedByAmazonTFM()).isEqualTo(UPDATED_SHIPPED_BY_AMAZON_TFM);
        assertThat(testPurchaseOrder.getTfmShipmentStatus()).isEqualTo(UPDATED_TFM_SHIPMENT_STATUS);
        assertThat(testPurchaseOrder.getCbaDisplayableShippingLabel()).isEqualTo(UPDATED_CBA_DISPLAYABLE_SHIPPING_LABEL);
        assertThat(testPurchaseOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testPurchaseOrder.getEarliestShipLocalDate()).isEqualTo(UPDATED_EARLIEST_SHIP_LOCAL_DATE);
        assertThat(testPurchaseOrder.getLatestShipLocalDate()).isEqualTo(UPDATED_LATEST_SHIP_LOCAL_DATE);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        assertThat(purchaseOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
