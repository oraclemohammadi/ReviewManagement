package com.milo.amz.review.batch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.batch.operations.JobOperator;
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
import org.springframework.batch.core.launch.support.SimpleJobOperator;
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
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.mapper.OrderMapper;
import com.milo.amz.review.service.mapper.PurchaseOrderMapper;




@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class OrderBatchConfiguration {
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
	  
	  
	  
      @Bean 
      @StepScope
      public ItemReader<Order> reader(){
    	  ListOrdersResponse response=null;
    	  List<MarketPlaceDTO> marketPlaceList= marketPlaceService.findAll();
    	  for (MarketPlaceDTO marketPlace:marketPlaceList)
    	  {
    		  if (marketPlace.getActivated())//is active
    		  {
    			   client =  AmazonOrderServiceConfig.getAsyncClient(marketPlace.getAccessKey(), marketPlace.getSecrectKey(), marketPlace.getServiceUrl(), "review", "1.0");
    			   ListOrdersRequest request = new ListOrdersRequest();
    				request.setSellerId(marketPlace.getSellerId()); //A1K11818W2YCX6
    				List<String> marketplaceId = new ArrayList<String>();
    				marketplaceId.add(marketPlace.getMarketPlaceId());
    				request.setMarketplaceId(marketplaceId);

    				String date = "20161222";
    				Date dob = null;
    				DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
    				try {
    					dob = df1.parse(date);
    				} catch (ParseException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				GregorianCalendar cal = new GregorianCalendar();
    				cal.setTime(dob);
    				XMLGregorianCalendar xmlDate = null;
    				try {
    					xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    				} catch (DatatypeConfigurationException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

    				XMLGregorianCalendar createdAfter = xmlDate;

    				request.setCreatedAfter(xmlDate);
    			    response =client.listOrders(request);
    		  }
    	  }
    	  if (response!=null)
    	  {
    		  driver = com.milo.amz.webdriver.utils.WebDriverFactory.getChromDriver();
			driver.get("https://sellercentral.amazon.com");
			WebElement myDynamicElement = new WebDriverWait(driver, 20).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ap_email']")));

			driver.findElement(By.xpath(".//*[@id='ap_email']")).sendKeys("michael.liu01@gmail.com");
			driver.findElement(By.xpath(".//*[@id='ap_password']")).sendKeys("Ajkml@5896");
			driver.findElement(By.xpath(".//*[@id='signInSubmit']")).click();
    	  }
			
    	    return new ListItemReader<>(response.getListOrdersResult().getOrders());
    	    
    	 //return new ListItemReader<>(Arrays.asList("one","three","foure"));
    	  
      }
      
     
      
      
      @Bean 
      @StepScope
      public ItemProcessor<Order ,PurchaseOrderDTO> processor(){
    	  return new ItemProcessor<Order ,PurchaseOrderDTO>() {

			@Override
			public PurchaseOrderDTO process(Order item) throws Exception {
					String contactBuyer="";
					driver.get("https://sellercentral.amazon.com/gp/orders-v2/list/ref=ag_myo_dnav_xx_");
				    WebElement myDynamicElement = new WebDriverWait(driver, 20).until(
							ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@name='searchType']")));
					myDynamicElement.sendKeys("Order ID");
					myDynamicElement = new WebDriverWait(driver, 20).until(
							ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='searchKeyword']")));
					myDynamicElement.sendKeys(item.getAmazonOrderId());
					driver.findElement(By.xpath(".//*[@id='SearchID']")).click();
					try{
					myDynamicElement = new WebDriverWait(driver, 20).until(
							ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='contact_buyer_link']")));
					}
					catch(TimeoutException ex)
					{
						try{
						myDynamicElement = driver.findElement(By.xpath(".//*[@id='myo-message-board-alert-info-label']"));
						if (myDynamicElement!=null && "Order Cancelled".equals(myDynamicElement.getText())) 
							return null;
						}
						catch(TimeoutException ex1)
						{
							
							
								myDynamicElement = driver.findElement(By.xpath(".//*[@id='_myoV2PageTopMessagePlaceholder']/div/div/ul/li/span"));
								if (myDynamicElement!=null && "Order ID Not Found".equals(myDynamicElement.getText())) 
									return null;
							
						}
					}
					if (myDynamicElement!=null)
					{
						contactBuyer=GeneralUtils.getParameterByName("buyerID", myDynamicElement.getAttribute("href"));  
					}
					PurchaseOrderDTO purchaseOrderDTO= orderMapper.orderDTOTOPurchaseOrder(item);
					purchaseOrderDTO.setBuyerId(contactBuyer);
									
					return purchaseOrderDTO;
			}
			
			
				
			
		};
    	
      }
      
      @Bean 
      @StepScope
      public ItemWriter<PurchaseOrderDTO> writer(){
    	  return new ItemWriter<PurchaseOrderDTO>() {
			
			@Override
			public void write(List<? extends PurchaseOrderDTO> items) throws Exception {
				
				for (PurchaseOrderDTO item:items)
				{
					purchaseOrderService.save(item);
					
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
	    return stepBuilderFactory.get("step1")
	        .<String,String>chunk(2).listener(new  OrderChunkListener())
	        .faultTolerant()
	        .reader(reader())
	        .processor(processor())
	        .writer(writer())
	        .build();
	  }
	  

	  
	  
	  @Bean
	  public Job reviewProcessSteps() throws Exception {
	    return jobBuilderFactory.get("amazonOrderJob")
	         .start(ordrProcess())  
	        .build();
	  }
}