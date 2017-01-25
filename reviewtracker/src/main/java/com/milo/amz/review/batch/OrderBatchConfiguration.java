package com.milo.amz.review.batch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.Order;
import com.milo.amz.review.GeneralUtils;
import com.milo.amz.review.amazon.client.AmazonOrderServiceConfig;
import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.mapper.OrderMapper;




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
      public ItemReader<Order> orderReader(){
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

    				String date = "20160125";
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
      public ItemProcessor<Order ,PurchaseOrderDTO> orderProcessor(){
    	  return new ItemProcessor<Order ,PurchaseOrderDTO>() {

			@Override
			public PurchaseOrderDTO process(Order item) throws Exception {
				long id=-1;
				PurchaseOrderDTO purchaseOrderDTO=purchaseOrderService.findBySellerOrderId(item.getAmazonOrderId());
					if ((purchaseOrderDTO!=null && purchaseOrderDTO.getBuyerId()==null )||purchaseOrderDTO==null&&!"Canceled".equals(item.getOrderStatus()))
					{
						if (purchaseOrderDTO!=null)  id=purchaseOrderDTO.getId();
						purchaseOrderDTO=orderMapper.orderDTOTOPurchaseOrder(item);
						if (id!=-1) purchaseOrderDTO.setId(id);
						String contactBuyer="";
						driver.get("https://sellercentral.amazon.com/gp/orders-v2/list/ref=ag_myo_dnav_xx_");
					    WebElement myDynamicElement = new WebDriverWait(driver, 20).until(
								ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@name='searchType']")));
						myDynamicElement.sendKeys("Buyer Email");
						myDynamicElement = new WebDriverWait(driver, 20).until(
								ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='searchKeyword']")));
						myDynamicElement.sendKeys(item.getBuyerEmail());
						
						myDynamicElement = driver.findElement(By.xpath(".//*[@id='_myoLO_preSelectedRangeSelect']"));
						
						myDynamicElement.sendKeys("Last 365 days");
						
						driver.findElement(By.xpath(".//*[@id='SearchID']")).click();
						
					   try{
						WebElement notFoundElement = new WebDriverWait(driver, 7).until(
									ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='_myoV2PageTopMessagePlaceholder']/div/div/ul/li/span")));
					   }
					   catch(Exception e)
					   {
						   WebElement buyerElement = driver
			  						.findElement(By.xpath("(.//*[contains(@id,'buyerName')])[1]"));
			  				String parentWindowHandle=driver.getWindowHandle();
			  				buyerElement.click();
		
			  				buyerElement = new WebDriverWait(driver, 20).until(
			  						ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='commMgrCompositionSubject']")));
		
			  				
			  				purchaseOrderDTO.setBuyerId(GeneralUtils.getParameterByName("buyerID", driver.getCurrentUrl()));
			  				driver.navigate().back();
					   }
						
						
						
	
		  				
		  				
		  				/*try {
		  					purchaseOrderService.save(purchaseOrderDTO);
		  				} catch (Exception e) {
		  					e.printStackTrace();
		  				}*/
	
		  				

					}
					return purchaseOrderDTO;	
			}
			
		};
	  
      }
      
      @Bean 
      @StepScope
      public ItemWriter<PurchaseOrderDTO> orderWriter(){
    	  return new ItemWriter<PurchaseOrderDTO>() {
			
			@Override
			public void write(List<? extends PurchaseOrderDTO> items) throws Exception {
				
				for (PurchaseOrderDTO item:items)
				{
					try{
					purchaseOrderService.save(item);
					}
					catch(Exception e)
					{
						
					}
					
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
	    return stepBuilderFactory.get("orderStep")
	        .<String,String>chunk(2).listener(new  OrderChunkListener())
	        .faultTolerant().skip(TimeoutException.class).skip(StaleElementReferenceException.class)
	        .reader(orderReader())
	        .processor(orderProcessor())
	        .writer(orderWriter())
	        .build();
	  }
	  

	  
	  
	  @Bean
	  public Job orderSteps() throws Exception {
	    return jobBuilderFactory.get("amazonOrderJob")
	    		.incrementer(new RunIdIncrementer())
	         .start(ordrProcess())  
	        .build();
	  }
}
