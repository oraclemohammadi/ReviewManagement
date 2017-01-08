package com.milo.amz.review.batch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.Order;
import com.amazonservices.mws.orders._2013_09_01.model.OrderItem;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.milo.amz.review.GeneralUtils;
import com.milo.amz.review.amazon.client.AmazonOrderServiceConfig;
import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import com.milo.amz.review.service.mapper.OrderMapper;
import com.milo.amz.review.service.mapper.PurchaseOrderMapper;



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
      public ItemReader<OrderItem> reader(){
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
    		  		for (PurchaseOrderDTO purchaseOrder :purchaseOrderList)
    		  		{
    		  		// requestItem.setNextToken(nextToken);
    		  			requestItem.setAmazonOrderId(purchaseOrder.getSellerOrderId());
    		  			response = client.listOrderItems(requestItem);
    		  			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
    		  			ListOrderItemsResult itemResult = response.getListOrderItemsResult();
    		  		}
    		  		/*for (OrderItem orderItem : itemResult.getOrderItems()) {
    		  			purchaseOrderItem = orderMapper.orderItemTOPurchaseOrderItem(orderItem);
    		  			purchaseOrderItem.setPurchaseOrder(purchaseOrder);
    		  			orderItemRepository.save(purchaseOrderItem);
    		  		}*/
    		  }
    	  }
    	 
			
    	    return new ListItemReader<>(response.getListOrderItemsResult().getOrderItems());
    	        	  
      }
      
      
      @Bean 
      @StepScope
      public ItemProcessor<OrderItem ,PurchaseOrderItemDTO> processor(){
    	  return new ItemProcessor<OrderItem ,PurchaseOrderItemDTO>() {

			@Override
			public PurchaseOrderItemDTO process(OrderItem item) throws Exception {
					
					return null;
			}
			
			
				
			
		};
    	
      }
      
      @Bean 
      @StepScope
      public ItemWriter<PurchaseOrderItemDTO> writer(){
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
	  public Step ordrProcess() {
    	  //System.out.println("name is "+name);
	    return stepBuilderFactory.get("orderItemStep")
	        .<String,String>chunk(2).listener(new  OrderChunkListener())
	        .faultTolerant()
	        .reader(reader())
	        .processor(processor())
	        .writer(writer())
	        .build();
	  }
	  

	  
	  
	  @Bean
	  public Job reviewProcessSteps() throws Exception {
	    return jobBuilderFactory.get("amazonOrderItemJob")
	        .incrementer(new RunIdIncrementer())
	        .start(ordrProcess())  
	        .build();
	  }
}
