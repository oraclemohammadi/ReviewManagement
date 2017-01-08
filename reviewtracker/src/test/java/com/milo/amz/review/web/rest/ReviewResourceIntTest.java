package com.milo.amz.review.web.rest;

import com.milo.amz.review.ReviewtrackerApp;

import com.milo.amz.review.domain.Review;
import com.milo.amz.review.repository.ReviewRepository;
import com.milo.amz.review.service.ReviewService;
import com.milo.amz.review.service.dto.ReviewDTO;
import com.milo.amz.review.service.mapper.ReviewMapper;

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

/**
 * Test class for the ReviewResource REST controller.
 *
 * @see ReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReviewtrackerApp.class)
public class ReviewResourceIntTest {

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEW_ID = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final Long DEFAULT_FULL_RATING = 1L;
    private static final Long UPDATED_FULL_RATING = 2L;

    private static final Long DEFAULT_HELPFUL_VOTES = 1L;
    private static final Long UPDATED_HELPFUL_VOTES = 2L;

    private static final Long DEFAULT_TOTAL_VOTES = 1L;
    private static final Long UPDATED_TOTAL_VOTES = 2L;

    private static final Boolean DEFAULT_VERIFIED_PURCHASE = false;
    private static final Boolean UPDATED_VERIFIED_PURCHASE = true;

    private static final String DEFAULT_REAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REAL_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFIC_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFIC_NOTE = "BBBBBBBBBB";

    @Inject
    private ReviewRepository reviewRepository;

    @Inject
    private ReviewMapper reviewMapper;

    @Inject
    private ReviewService reviewService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReviewMockMvc;

    private Review review;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReviewResource reviewResource = new ReviewResource();
        ReflectionTestUtils.setField(reviewResource, "reviewService", reviewService);
        this.restReviewMockMvc = MockMvcBuilders.standaloneSetup(reviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createEntity(EntityManager em) {
        Review review = new Review()
                .itemID(DEFAULT_ITEM_ID)
                .reviewID(DEFAULT_REVIEW_ID)
                .customerName(DEFAULT_CUSTOMER_NAME)
                .customerID(DEFAULT_CUSTOMER_ID)
                .title(DEFAULT_TITLE)
                .rating(DEFAULT_RATING)
                .fullRating(DEFAULT_FULL_RATING)
                .helpfulVotes(DEFAULT_HELPFUL_VOTES)
                .totalVotes(DEFAULT_TOTAL_VOTES)
                .verifiedPurchase(DEFAULT_VERIFIED_PURCHASE)
                .realName(DEFAULT_REAL_NAME)
                .reviewDate(DEFAULT_REVIEW_DATE)
                .content(DEFAULT_CONTENT)
                .specificNote(DEFAULT_SPECIFIC_NOTE);
        return review;
    }

    @Before
    public void initTest() {
        review = createEntity(em);
    }

    @Test
    @Transactional
    public void createReview() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.reviewToReviewDTO(review);

        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isCreated());

        // Validate the Review in the database
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(databaseSizeBeforeCreate + 1);
        Review testReview = reviews.get(reviews.size() - 1);
        assertThat(testReview.getItemID()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testReview.getReviewID()).isEqualTo(DEFAULT_REVIEW_ID);
        assertThat(testReview.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testReview.getCustomerID()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testReview.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReview.getFullRating()).isEqualTo(DEFAULT_FULL_RATING);
        assertThat(testReview.getHelpfulVotes()).isEqualTo(DEFAULT_HELPFUL_VOTES);
        assertThat(testReview.getTotalVotes()).isEqualTo(DEFAULT_TOTAL_VOTES);
        assertThat(testReview.isVerifiedPurchase()).isEqualTo(DEFAULT_VERIFIED_PURCHASE);
        assertThat(testReview.getRealName()).isEqualTo(DEFAULT_REAL_NAME);
        assertThat(testReview.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testReview.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testReview.getSpecificNote()).isEqualTo(DEFAULT_SPECIFIC_NOTE);
    }

    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviews
        restReviewMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemID").value(hasItem(DEFAULT_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].reviewID").value(hasItem(DEFAULT_REVIEW_ID.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
            .andExpect(jsonPath("$.[*].fullRating").value(hasItem(DEFAULT_FULL_RATING.intValue())))
            .andExpect(jsonPath("$.[*].helpfulVotes").value(hasItem(DEFAULT_HELPFUL_VOTES.intValue())))
            .andExpect(jsonPath("$.[*].totalVotes").value(hasItem(DEFAULT_TOTAL_VOTES.intValue())))
            .andExpect(jsonPath("$.[*].verifiedPurchase").value(hasItem(DEFAULT_VERIFIED_PURCHASE.booleanValue())))
            .andExpect(jsonPath("$.[*].realName").value(hasItem(DEFAULT_REAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].specificNote").value(hasItem(DEFAULT_SPECIFIC_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(review.getId().intValue()))
            .andExpect(jsonPath("$.itemID").value(DEFAULT_ITEM_ID.toString()))
            .andExpect(jsonPath("$.reviewID").value(DEFAULT_REVIEW_ID.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.fullRating").value(DEFAULT_FULL_RATING.intValue()))
            .andExpect(jsonPath("$.helpfulVotes").value(DEFAULT_HELPFUL_VOTES.intValue()))
            .andExpect(jsonPath("$.totalVotes").value(DEFAULT_TOTAL_VOTES.intValue()))
            .andExpect(jsonPath("$.verifiedPurchase").value(DEFAULT_VERIFIED_PURCHASE.booleanValue()))
            .andExpect(jsonPath("$.realName").value(DEFAULT_REAL_NAME.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.specificNote").value(DEFAULT_SPECIFIC_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review
        Review updatedReview = reviewRepository.findOne(review.getId());
        updatedReview
                .itemID(UPDATED_ITEM_ID)
                .reviewID(UPDATED_REVIEW_ID)
                .customerName(UPDATED_CUSTOMER_NAME)
                .customerID(UPDATED_CUSTOMER_ID)
                .title(UPDATED_TITLE)
                .rating(UPDATED_RATING)
                .fullRating(UPDATED_FULL_RATING)
                .helpfulVotes(UPDATED_HELPFUL_VOTES)
                .totalVotes(UPDATED_TOTAL_VOTES)
                .verifiedPurchase(UPDATED_VERIFIED_PURCHASE)
                .realName(UPDATED_REAL_NAME)
                .reviewDate(UPDATED_REVIEW_DATE)
                .content(UPDATED_CONTENT)
                .specificNote(UPDATED_SPECIFIC_NOTE);
        ReviewDTO reviewDTO = reviewMapper.reviewToReviewDTO(updatedReview);

        restReviewMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviews.get(reviews.size() - 1);
        assertThat(testReview.getItemID()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testReview.getReviewID()).isEqualTo(UPDATED_REVIEW_ID);
        assertThat(testReview.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testReview.getCustomerID()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testReview.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReview.getFullRating()).isEqualTo(UPDATED_FULL_RATING);
        assertThat(testReview.getHelpfulVotes()).isEqualTo(UPDATED_HELPFUL_VOTES);
        assertThat(testReview.getTotalVotes()).isEqualTo(UPDATED_TOTAL_VOTES);
        assertThat(testReview.isVerifiedPurchase()).isEqualTo(UPDATED_VERIFIED_PURCHASE);
        assertThat(testReview.getRealName()).isEqualTo(UPDATED_REAL_NAME);
        assertThat(testReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testReview.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testReview.getSpecificNote()).isEqualTo(UPDATED_SPECIFIC_NOTE);
    }

    @Test
    @Transactional
    public void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);
        int databaseSizeBeforeDelete = reviewRepository.findAll().size();

        // Get the review
        restReviewMockMvc.perform(delete("/api/reviews/{id}", review.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
