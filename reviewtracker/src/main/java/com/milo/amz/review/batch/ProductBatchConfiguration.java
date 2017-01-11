package com.milo.amz.review.batch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import com.amazonservices.mws.products.model.ASINListType;
import com.amazonservices.mws.products.model.AttributeSetList;
import com.amazonservices.mws.products.model.GetMatchingProductRequest;
import com.amazonservices.mws.products.model.GetMatchingProductResponse;
import com.amazonservices.mws.products.model.GetMatchingProductResult;
import com.milo.amz.review.amazon.client.AmazonOrderServiceConfig;
import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.service.ProductService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.ProductDTO;
import com.milo.amz.review.service.mapper.OrderMapper;
import com.milo.amz.webdriver.utils.XMLNodeUtils;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class ProductBatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MarketPlaceService marketPlaceService;
	private MarketplaceWebServiceProductsAsync client;

	@Inject
	private OrderMapper orderMapper;

	private WebDriver driver;

	@Inject
	private ProductService productService;

	@Bean
	@StepScope
	public ItemReader<ProductDTO> productItemReader() {
		GetMatchingProductRequest request = new GetMatchingProductRequest();
		ProductDTO productDTO = null;
		List<ProductDTO> productList = new ArrayList();
		List<MarketPlaceDTO> marketPlaceList = marketPlaceService.findAll();
		for (MarketPlaceDTO marketPlace : marketPlaceList) {
			if (marketPlace.getActivated())// is active
			{
				client = AmazonOrderServiceConfig.getproductAsyncClient(marketPlace.getAccessKey(),
						marketPlace.getSecrectKey(), marketPlace.getServiceUrl(), "reviewTracker", "1.0");
				request.setSellerId(marketPlace.getSellerId() );
				request.setMarketplaceId(marketPlace.getMarketPlaceId());
				List<String> getAsinList=new ArrayList();
				getAsinList.add("B00ZYCSQKA");
				getAsinList.add("B00GS55KHE");
				request.setASINList(new ASINListType(getAsinList));
				GetMatchingProductResponse matchingProductResponse = client.getMatchingProduct(request);
				for (GetMatchingProductResult product : matchingProductResponse.getGetMatchingProductResult()) {

					productDTO = new ProductDTO();
					productDTO.setAsin(product.getASIN());
					AttributeSetList attributeSetList = product.getProduct().getAttributeSets();
					for (Object obj : attributeSetList.getAny()) {
						Node attribute = (Node) obj;
						productDTO.setTitle(XMLNodeUtils.getNodeValue("ns2:Title", (NodeList) attribute));
						// System.out.println(ProductsUtil.formatXml(attribute));
					}
					productList.add(productDTO);
				}
			}
		}
		return new ListItemReader<>(productList);

	}

	/*
	 * @Bean
	 * 
	 * @StepScope public ItemProcessor<OrderItem ,PurchaseOrderItemDTO>
	 * processor(){ return new ItemProcessor<OrderItem ,PurchaseOrderItemDTO>()
	 * {
	 * 
	 * @Override public PurchaseOrderItemDTO process(OrderItem item) throws
	 * Exception {
	 * 
	 * return orderMapper.orderItemTOPurchaseOrderItem(item); }
	 * 
	 * 
	 * 
	 * 
	 * };
	 * 
	 * }
	 */

	@Bean
	@StepScope
	public ItemWriter<ProductDTO> productItemWriter() {
		return new ItemWriter<ProductDTO>() {

			@Override
			public void write(List<? extends ProductDTO> items) throws Exception {

				for (ProductDTO item : items) {
					productService.save(item);

				}

			}
		};

	}
	/*
	 * @Bean public Step crawlProductReviews() { return
	 * stepBuilderFactory.get("step1") .tasklet(new Tasklet() { //single method
	 * in one transaction task public RepeatStatus execute(StepContribution
	 * contribution, ChunkContext chunkContext) {
	 * System.out.println("*************Batch Executed"); return
	 * RepeatStatus.FINISHED; } }) .build(); }
	 * 
	 * @Bean public Step relateReviewsIntoOrder() { return
	 * stepBuilderFactory.get("step2") .tasklet((contribution, chunkContext)-> {
	 * System.out.println("*************Batch Executed Step2"); return
	 * RepeatStatus.FINISHED; }) .build(); }
	 */

	@Bean
	public Step productItemProcess() {
		// System.out.println("name is "+name);
		return stepBuilderFactory.get("productStep").<String, String>chunk(2).listener(new OrderChunkListener())
				.faultTolerant().reader(productItemReader())
				// .processor(processor())
				.writer(productItemWriter()).build();
	}

	@Bean
	public Job productSteps() throws Exception {
		return jobBuilderFactory.get("productJob").incrementer(new RunIdIncrementer()).start(productItemProcess()).build();
	}
}
