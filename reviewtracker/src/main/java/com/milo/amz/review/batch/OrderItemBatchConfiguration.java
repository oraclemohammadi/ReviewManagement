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

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.OrderItem;
import com.milo.amz.review.amazon.client.AmazonOrderServiceConfig;
import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import com.milo.amz.review.service.mapper.OrderMapper;
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class OrderItemBatchConfiguration {
	 @Autowired
	  private JobBuilderFactory jobBuilderFactory;

	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  
	  @Autowired
	  private MarketPlaceService marketPlaceService;
	  MarketplaceWebServiceOrdersAsyncClient client;
	  
	  @Inject
	  private OrderMapper orderMapper;
	  
	  private WebDriver driver;
	  
	  @Inject
	  private PurchaseOrderService purchaseOrderService;
	  
	  @Inject
	  private PurchaseOrderItemService purchaseOrderItemService;
	  
	
	  
      @Bean 
      @StepScope
      public ItemReader<PurchaseOrderItemDTO> orderItemReader(){
    	  int requestCount=0;
    	  List<PurchaseOrderItemDTO> listOrderItem=new ArrayList<>();
    	  PurchaseOrderItemDTO  purchaseOrderItemDTO=null;
    	  ListOrderItemsResponse response=null;
    	  List<MarketPlaceDTO> marketPlaceList= marketPlaceService.findAll();
    	  for (MarketPlaceDTO marketPlace:marketPlaceList)
    	  {
    		  if (marketPlace.getActivated())//is active
    		  {
    			   client =  AmazonOrderServiceConfig.getAsyncClient(marketPlace.getAccessKey(), marketPlace.getSecrectKey(), marketPlace.getServiceUrl(), "review", "1.0");
    			   ListOrderItemsRequest requestItem = new ListOrderItemsRequest();
    		  		requestItem.setSellerId(marketPlace.getSellerId());
    		  		List<PurchaseOrderDTO> purchaseOrderList=purchaseOrderService.findOrdersWithIcompleteItems();
    		  		requestCount=0;
    		  		for (PurchaseOrderDTO purchaseOrder :purchaseOrderList)
    		  		{
    		  		// requestItem.setNextToken(nextToken);
    		  			requestCount++;
    		  			if (requestCount<10)
	    		  			{
	    		  				requestItem.setAmazonOrderId(purchaseOrder.getSellerOrderId());
		    		  			response = client.listOrderItems(requestItem);
		    		  			//purchaseOrderItemDTO=orderMapper.orderItemTOPurchaseOrderItem(item);
		    		  			
		    		  			for (OrderItem item:response.getListOrderItemsResult().getOrderItems())
		    		  			{
		    		  				
		    		  				purchaseOrderItemDTO=orderMapper.orderItemTOPurchaseOrderItem(item);
		    		  				purchaseOrderItemDTO.setPurchaseOrderId(purchaseOrder.getId());
		    		  				listOrderItem.add(purchaseOrderItemDTO);
		    		  			}
	    		  			}
    		  		}
    		  		/*for (OrderItem orderItem : itemResult.getOrderItems()) {
    		  			purchaseOrderItem = orderMapper.orderItemTOPurchaseOrderItem(orderItem);
    		  			purchaseOrderItem.setPurchaseOrder(purchaseOrder);
    		  			orderItemRepository.save(purchaseOrderItem);
    		  		}*/
    		  }
    	  }
    	 
			
    	    return new ListItemReader<>(listOrderItem);
    	        	  
      }
      
      
     /* @Bean 
      @StepScope
      public ItemProcessor<OrderItem ,PurchaseOrderItemDTO> processor(){
    	  return new ItemProcessor<OrderItem ,PurchaseOrderItemDTO>() {

			@Override
			public PurchaseOrderItemDTO process(OrderItem item) throws Exception {
	
					return orderMapper.orderItemTOPurchaseOrderItem(item);
			}
			
			
				
			
		};
    	
      }*/
      
      @Bean 
      @StepScope
      public ItemWriter<PurchaseOrderItemDTO> orderItemWriter(){
    	  return new ItemWriter<PurchaseOrderItemDTO>() {
			
			@Override
			public void write(List<? extends PurchaseOrderItemDTO> items) throws Exception {
				
				for (PurchaseOrderItemDTO item:items)
				{
					purchaseOrderItemService.save(item);
					
				}
				
			}
		};
    	
      }
	 /* @Bean
	  public Step crawlProductReviews() {
	    return stepBuilderFactory.get("step1")
	        .tasklet(new Tasklet() { //single method in one transaction task
	          public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
	        	  System.out.println("*************Batch Executed");
	            return RepeatStatus.FINISHED;
	          }
	        })
	        .build();
	  }
	  
	  @Bean
	  public Step relateReviewsIntoOrder() {
	    return stepBuilderFactory.get("step2")
	        .tasklet((contribution, chunkContext)-> {
	        	  System.out.println("*************Batch Executed Step2");
	            return RepeatStatus.FINISHED;
	        })
	        .build();
	  }*/
	  
      @Bean
	  public Step ordrItemProcess() {
    	  //System.out.println("name is "+name);
	    return stepBuilderFactory.get("orderItemStep")
	        .<String,String>chunk(2).listener(new  OrderChunkListener())
	        .faultTolerant()
	        .reader(orderItemReader())
	        //.processor(processor())
	        .writer(orderItemWriter())
	        .build();
	  }
	  

	  
	  
	  @Bean
	  public Job orderItemSteps() throws Exception {
	    return jobBuilderFactory.get("amazonOrderItemJob")
	        .incrementer(new RunIdIncrementer())
	        .start(ordrItemProcess())  
	        .build();
	  }
}
