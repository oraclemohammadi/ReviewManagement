/*package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.PurchaseOrderItem;
import com.milo.amz.review.repository.PurchaseOrderItemRepository;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import com.milo.amz.review.service.mapper.PurchaseOrderItemMapper;

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

*//**
 * Test class for the PurchaseOrderItemResource REST controller.
 *
 * @see PurchaseOrderItemResource
 *//*
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ReviewtrackerApp.class)
public class PurchaseOrderItemResourceIntTest {

    private static final String DEFAULT_ASIN = "AAAAAAAAAA";
    private static final String UPDATED_ASIN = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY_ORDERED = 1L;
    private static final Long UPDATED_QUANTITY_ORDERED = 2L;

    private static final Long DEFAULT_QUANTITY_SHIPPED = 1L;
    private static final Long UPDATED_QUANTITY_SHIPPED = 2L;

    private static final Long DEFAULT_ITEM_PRICE = 1L;
    private static final Long UPDATED_ITEM_PRICE = 2L;

    private static final Long DEFAULT_SHIPPING_PRICE = 1L;
    private static final Long UPDATED_SHIPPING_PRICE = 2L;

    private static final Long DEFAULT_GIFT_WRAP_PRICE = 1L;
    private static final Long UPDATED_GIFT_WRAP_PRICE = 2L;

    private static final Long DEFAULT_ITEM_TAX = 1L;
    private static final Long UPDATED_ITEM_TAX = 2L;

    private static final Long DEFAULT_SHIPPING_TAX = 1L;
    private static final Long UPDATED_SHIPPING_TAX = 2L;

    private static final Long DEFAULT_GIFT_WRAP_TAX = 1L;
    private static final Long UPDATED_GIFT_WRAP_TAX = 2L;

    private static final Long DEFAULT_SHIPPING_DISCOUNT = 1L;
    private static final Long UPDATED_SHIPPING_DISCOUNT = 2L;

    private static final Long DEFAULT_PROMOTION_DISCOUNT = 1L;
    private static final Long UPDATED_PROMOTION_DISCOUNT = 2L;

    private static final Long DEFAULT_COD_FEE = 1L;
    private static final Long UPDATED_COD_FEE = 2L;

    private static final Long DEFAULT_COD_FEE_DISCOUNT = 1L;
    private static final Long UPDATED_COD_FEE_DISCOUNT = 2L;

    private static final String DEFAULT_GIFT_MESSAGE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_MESSAGE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_GIFT_WRAP_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_WRAP_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION_SUBTYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION_SUBTYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEDULED_DELIVERY_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULED_DELIVERY_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEDULED_DELIVERY_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULED_DELIVERY_END_DATE = "BBBBBBBBBB";

    @Inject
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Inject
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    @Inject
    private PurchaseOrderItemService purchaseOrderItemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPurchaseOrderItemMockMvc;

    private PurchaseOrderItem purchaseOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderItemResource purchaseOrderItemResource = new PurchaseOrderItemResource();
        ReflectionTestUtils.setField(purchaseOrderItemResource, "purchaseOrderItemService", purchaseOrderItemService);
        this.restPurchaseOrderItemMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static PurchaseOrderItem createEntity(EntityManager em) {
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem()
                .asin(DEFAULT_ASIN)
                .sellerSKU(DEFAULT_SELLER_SKU)
                .orderItemId(DEFAULT_ORDER_ITEM_ID)
                .title(DEFAULT_TITLE)
                .quantityOrdered(DEFAULT_QUANTITY_ORDERED)
                .quantityShipped(DEFAULT_QUANTITY_SHIPPED)
                .itemPrice(DEFAULT_ITEM_PRICE)
                .shippingPrice(DEFAULT_SHIPPING_PRICE)
                .giftWrapPrice(DEFAULT_GIFT_WRAP_PRICE)
                .itemTax(DEFAULT_ITEM_TAX)
                .shippingTax(DEFAULT_SHIPPING_TAX)
                .giftWrapTax(DEFAULT_GIFT_WRAP_TAX)
                .shippingDiscount(DEFAULT_SHIPPING_DISCOUNT)
                .promotionDiscount(DEFAULT_PROMOTION_DISCOUNT)
                .codFee(DEFAULT_COD_FEE)
                .codFeeDiscount(DEFAULT_COD_FEE_DISCOUNT)
                .giftMessageText(DEFAULT_GIFT_MESSAGE_TEXT)
                .giftWrapLevel(DEFAULT_GIFT_WRAP_LEVEL)
                .conditionNote(DEFAULT_CONDITION_NOTE)
                .conditionId(DEFAULT_CONDITION_ID)
                .conditionSubtypeId(DEFAULT_CONDITION_SUBTYPE_ID)
                .scheduledDeliveryStartDate(DEFAULT_SCHEDULED_DELIVERY_START_DATE)
                .scheduledDeliveryEndDate(DEFAULT_SCHEDULED_DELIVERY_END_DATE);
        return purchaseOrderItem;
    }

    @Before
    public void initTest() {
        purchaseOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderItemRepository.findAll().size();

        // Create the PurchaseOrderItem
        PurchaseOrderItemDTO purchaseOrderItemDTO = purchaseOrderItemMapper.purchaseOrderItemToPurchaseOrderItemDTO(purchaseOrderItem);

        restPurchaseOrderItemMockMvc.perform(post("/api/purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderItem in the database
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAll();
        assertThat(purchaseOrderItems).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderItem testPurchaseOrderItem = purchaseOrderItems.get(purchaseOrderItems.size() - 1);
        assertThat(testPurchaseOrderItem.getASIN()).isEqualTo(DEFAULT_ASIN);
        assertThat(testPurchaseOrderItem.getSellerSKU()).isEqualTo(DEFAULT_SELLER_SKU);
        assertThat(testPurchaseOrderItem.getOrderItemId()).isEqualTo(DEFAULT_ORDER_ITEM_ID);
        assertThat(testPurchaseOrderItem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPurchaseOrderItem.getQuantityOrdered()).isEqualTo(DEFAULT_QUANTITY_ORDERED);
        assertThat(testPurchaseOrderItem.getQuantityShipped()).isEqualTo(DEFAULT_QUANTITY_SHIPPED);
        assertThat(testPurchaseOrderItem.getItemPrice()).isEqualTo(DEFAULT_ITEM_PRICE);
        assertThat(testPurchaseOrderItem.getShippingPrice()).isEqualTo(DEFAULT_SHIPPING_PRICE);
        assertThat(testPurchaseOrderItem.getGiftWrapPrice()).isEqualTo(DEFAULT_GIFT_WRAP_PRICE);
        assertThat(testPurchaseOrderItem.getItemTax()).isEqualTo(DEFAULT_ITEM_TAX);
        assertThat(testPurchaseOrderItem.getShippingTax()).isEqualTo(DEFAULT_SHIPPING_TAX);
        assertThat(testPurchaseOrderItem.getGiftWrapTax()).isEqualTo(DEFAULT_GIFT_WRAP_TAX);
        assertThat(testPurchaseOrderItem.getShippingDiscount()).isEqualTo(DEFAULT_SHIPPING_DISCOUNT);
        assertThat(testPurchaseOrderItem.getPromotionDiscount()).isEqualTo(DEFAULT_PROMOTION_DISCOUNT);
        assertThat(testPurchaseOrderItem.getCodFee()).isEqualTo(DEFAULT_COD_FEE);
        assertThat(testPurchaseOrderItem.getCodFeeDiscount()).isEqualTo(DEFAULT_COD_FEE_DISCOUNT);
        assertThat(testPurchaseOrderItem.getGiftMessageText()).isEqualTo(DEFAULT_GIFT_MESSAGE_TEXT);
        assertThat(testPurchaseOrderItem.getGiftWrapLevel()).isEqualTo(DEFAULT_GIFT_WRAP_LEVEL);
        assertThat(testPurchaseOrderItem.getConditionNote()).isEqualTo(DEFAULT_CONDITION_NOTE);
        assertThat(testPurchaseOrderItem.getConditionId()).isEqualTo(DEFAULT_CONDITION_ID);
        assertThat(testPurchaseOrderItem.getConditionSubtypeId()).isEqualTo(DEFAULT_CONDITION_SUBTYPE_ID);
        assertThat(testPurchaseOrderItem.getScheduledDeliveryStartDate()).isEqualTo(DEFAULT_SCHEDULED_DELIVERY_START_DATE);
        assertThat(testPurchaseOrderItem.getScheduledDeliveryEndDate()).isEqualTo(DEFAULT_SCHEDULED_DELIVERY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderItems() throws Exception {
        // Initialize the database
        purchaseOrderItemRepository.saveAndFlush(purchaseOrderItem);

        // Get all the purchaseOrderItems
        restPurchaseOrderItemMockMvc.perform(get("/api/purchase-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].asin").value(hasItem(DEFAULT_ASIN.toString())))
            .andExpect(jsonPath("$.[*].sellerSKU").value(hasItem(DEFAULT_SELLER_SKU.toString())))
            .andExpect(jsonPath("$.[*].orderItemId").value(hasItem(DEFAULT_ORDER_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].quantityOrdered").value(hasItem(DEFAULT_QUANTITY_ORDERED.intValue())))
            .andExpect(jsonPath("$.[*].quantityShipped").value(hasItem(DEFAULT_QUANTITY_SHIPPED.intValue())))
            .andExpect(jsonPath("$.[*].itemPrice").value(hasItem(DEFAULT_ITEM_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].shippingPrice").value(hasItem(DEFAULT_SHIPPING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].giftWrapPrice").value(hasItem(DEFAULT_GIFT_WRAP_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].itemTax").value(hasItem(DEFAULT_ITEM_TAX.intValue())))
            .andExpect(jsonPath("$.[*].shippingTax").value(hasItem(DEFAULT_SHIPPING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].giftWrapTax").value(hasItem(DEFAULT_GIFT_WRAP_TAX.intValue())))
            .andExpect(jsonPath("$.[*].shippingDiscount").value(hasItem(DEFAULT_SHIPPING_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].promotionDiscount").value(hasItem(DEFAULT_PROMOTION_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].codFee").value(hasItem(DEFAULT_COD_FEE.intValue())))
            .andExpect(jsonPath("$.[*].codFeeDiscount").value(hasItem(DEFAULT_COD_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].giftMessageText").value(hasItem(DEFAULT_GIFT_MESSAGE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].giftWrapLevel").value(hasItem(DEFAULT_GIFT_WRAP_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].conditionNote").value(hasItem(DEFAULT_CONDITION_NOTE.toString())))
            .andExpect(jsonPath("$.[*].conditionId").value(hasItem(DEFAULT_CONDITION_ID.toString())))
            .andExpect(jsonPath("$.[*].conditionSubtypeId").value(hasItem(DEFAULT_CONDITION_SUBTYPE_ID.toString())))
            .andExpect(jsonPath("$.[*].scheduledDeliveryStartDate").value(hasItem(DEFAULT_SCHEDULED_DELIVERY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].scheduledDeliveryEndDate").value(hasItem(DEFAULT_SCHEDULED_DELIVERY_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPurchaseOrderItem() throws Exception {
        // Initialize the database
        purchaseOrderItemRepository.saveAndFlush(purchaseOrderItem);

        // Get the purchaseOrderItem
        restPurchaseOrderItemMockMvc.perform(get("/api/purchase-order-items/{id}", purchaseOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.asin").value(DEFAULT_ASIN.toString()))
            .andExpect(jsonPath("$.sellerSKU").value(DEFAULT_SELLER_SKU.toString()))
            .andExpect(jsonPath("$.orderItemId").value(DEFAULT_ORDER_ITEM_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.quantityOrdered").value(DEFAULT_QUANTITY_ORDERED.intValue()))
            .andExpect(jsonPath("$.quantityShipped").value(DEFAULT_QUANTITY_SHIPPED.intValue()))
            .andExpect(jsonPath("$.itemPrice").value(DEFAULT_ITEM_PRICE.intValue()))
            .andExpect(jsonPath("$.shippingPrice").value(DEFAULT_SHIPPING_PRICE.intValue()))
            .andExpect(jsonPath("$.giftWrapPrice").value(DEFAULT_GIFT_WRAP_PRICE.intValue()))
            .andExpect(jsonPath("$.itemTax").value(DEFAULT_ITEM_TAX.intValue()))
            .andExpect(jsonPath("$.shippingTax").value(DEFAULT_SHIPPING_TAX.intValue()))
            .andExpect(jsonPath("$.giftWrapTax").value(DEFAULT_GIFT_WRAP_TAX.intValue()))
            .andExpect(jsonPath("$.shippingDiscount").value(DEFAULT_SHIPPING_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.promotionDiscount").value(DEFAULT_PROMOTION_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.codFee").value(DEFAULT_COD_FEE.intValue()))
            .andExpect(jsonPath("$.codFeeDiscount").value(DEFAULT_COD_FEE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.giftMessageText").value(DEFAULT_GIFT_MESSAGE_TEXT.toString()))
            .andExpect(jsonPath("$.giftWrapLevel").value(DEFAULT_GIFT_WRAP_LEVEL.toString()))
            .andExpect(jsonPath("$.conditionNote").value(DEFAULT_CONDITION_NOTE.toString()))
            .andExpect(jsonPath("$.conditionId").value(DEFAULT_CONDITION_ID.toString()))
            .andExpect(jsonPath("$.conditionSubtypeId").value(DEFAULT_CONDITION_SUBTYPE_ID.toString()))
            .andExpect(jsonPath("$.scheduledDeliveryStartDate").value(DEFAULT_SCHEDULED_DELIVERY_START_DATE.toString()))
            .andExpect(jsonPath("$.scheduledDeliveryEndDate").value(DEFAULT_SCHEDULED_DELIVERY_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrderItem() throws Exception {
        // Get the purchaseOrderItem
        restPurchaseOrderItemMockMvc.perform(get("/api/purchase-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderItem() throws Exception {
        // Initialize the database
        purchaseOrderItemRepository.saveAndFlush(purchaseOrderItem);
        int databaseSizeBeforeUpdate = purchaseOrderItemRepository.findAll().size();

        // Update the purchaseOrderItem
        PurchaseOrderItem updatedPurchaseOrderItem = purchaseOrderItemRepository.findOne(purchaseOrderItem.getId());
        updatedPurchaseOrderItem
                .asin(UPDATED_ASIN)
                .sellerSKU(UPDATED_SELLER_SKU)
                .orderItemId(UPDATED_ORDER_ITEM_ID)
                .title(UPDATED_TITLE)
                .quantityOrdered(UPDATED_QUANTITY_ORDERED)
                .quantityShipped(UPDATED_QUANTITY_SHIPPED)
                .itemPrice(UPDATED_ITEM_PRICE)
                .shippingPrice(UPDATED_SHIPPING_PRICE)
                .giftWrapPrice(UPDATED_GIFT_WRAP_PRICE)
                .itemTax(UPDATED_ITEM_TAX)
                .shippingTax(UPDATED_SHIPPING_TAX)
                .giftWrapTax(UPDATED_GIFT_WRAP_TAX)
                .shippingDiscount(UPDATED_SHIPPING_DISCOUNT)
                .promotionDiscount(UPDATED_PROMOTION_DISCOUNT)
                .codFee(UPDATED_COD_FEE)
                .codFeeDiscount(UPDATED_COD_FEE_DISCOUNT)
                .giftMessageText(UPDATED_GIFT_MESSAGE_TEXT)
                .giftWrapLevel(UPDATED_GIFT_WRAP_LEVEL)
                .conditionNote(UPDATED_CONDITION_NOTE)
                .conditionId(UPDATED_CONDITION_ID)
                .conditionSubtypeId(UPDATED_CONDITION_SUBTYPE_ID)
                .scheduledDeliveryStartDate(UPDATED_SCHEDULED_DELIVERY_START_DATE)
                .scheduledDeliveryEndDate(UPDATED_SCHEDULED_DELIVERY_END_DATE);
        PurchaseOrderItemDTO purchaseOrderItemDTO = purchaseOrderItemMapper.purchaseOrderItemToPurchaseOrderItemDTO(updatedPurchaseOrderItem);

        restPurchaseOrderItemMockMvc.perform(put("/api/purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderItemDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderItem in the database
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAll();
        assertThat(purchaseOrderItems).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderItem testPurchaseOrderItem = purchaseOrderItems.get(purchaseOrderItems.size() - 1);
        assertThat(testPurchaseOrderItem.getASIN()).isEqualTo(UPDATED_ASIN);
        assertThat(testPurchaseOrderItem.getSellerSKU()).isEqualTo(UPDATED_SELLER_SKU);
        assertThat(testPurchaseOrderItem.getOrderItemId()).isEqualTo(UPDATED_ORDER_ITEM_ID);
        assertThat(testPurchaseOrderItem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPurchaseOrderItem.getQuantityOrdered()).isEqualTo(UPDATED_QUANTITY_ORDERED);
        assertThat(testPurchaseOrderItem.getQuantityShipped()).isEqualTo(UPDATED_QUANTITY_SHIPPED);
        assertThat(testPurchaseOrderItem.getItemPrice()).isEqualTo(UPDATED_ITEM_PRICE);
        assertThat(testPurchaseOrderItem.getShippingPrice()).isEqualTo(UPDATED_SHIPPING_PRICE);
        assertThat(testPurchaseOrderItem.getGiftWrapPrice()).isEqualTo(UPDATED_GIFT_WRAP_PRICE);
        assertThat(testPurchaseOrderItem.getItemTax()).isEqualTo(UPDATED_ITEM_TAX);
        assertThat(testPurchaseOrderItem.getShippingTax()).isEqualTo(UPDATED_SHIPPING_TAX);
        assertThat(testPurchaseOrderItem.getGiftWrapTax()).isEqualTo(UPDATED_GIFT_WRAP_TAX);
        assertThat(testPurchaseOrderItem.getShippingDiscount()).isEqualTo(UPDATED_SHIPPING_DISCOUNT);
        assertThat(testPurchaseOrderItem.getPromotionDiscount()).isEqualTo(UPDATED_PROMOTION_DISCOUNT);
        assertThat(testPurchaseOrderItem.getCodFee()).isEqualTo(UPDATED_COD_FEE);
        assertThat(testPurchaseOrderItem.getCodFeeDiscount()).isEqualTo(UPDATED_COD_FEE_DISCOUNT);
        assertThat(testPurchaseOrderItem.getGiftMessageText()).isEqualTo(UPDATED_GIFT_MESSAGE_TEXT);
        assertThat(testPurchaseOrderItem.getGiftWrapLevel()).isEqualTo(UPDATED_GIFT_WRAP_LEVEL);
        assertThat(testPurchaseOrderItem.getConditionNote()).isEqualTo(UPDATED_CONDITION_NOTE);
        assertThat(testPurchaseOrderItem.getConditionId()).isEqualTo(UPDATED_CONDITION_ID);
        assertThat(testPurchaseOrderItem.getConditionSubtypeId()).isEqualTo(UPDATED_CONDITION_SUBTYPE_ID);
        assertThat(testPurchaseOrderItem.getScheduledDeliveryStartDate()).isEqualTo(UPDATED_SCHEDULED_DELIVERY_START_DATE);
        assertThat(testPurchaseOrderItem.getScheduledDeliveryEndDate()).isEqualTo(UPDATED_SCHEDULED_DELIVERY_END_DATE);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderItem() throws Exception {
        // Initialize the database
        purchaseOrderItemRepository.saveAndFlush(purchaseOrderItem);
        int databaseSizeBeforeDelete = purchaseOrderItemRepository.findAll().size();

        // Get the purchaseOrderItem
        restPurchaseOrderItemMockMvc.perform(delete("/api/purchase-order-items/{id}", purchaseOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAll();
        assertThat(purchaseOrderItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/