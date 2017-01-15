package com.milo.amz.review.batch;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
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
import com.milo.amz.review.service.ProductService;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.dto.ProductDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.mapper.OrderMapper;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class OrderHtmlScrappedBatchConfiguration {
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
	private ProductService productService;

	@Bean
	@StepScope
	public ItemReader<PurchaseOrderDTO> orderReader() {
		ListOrdersResponse response = null;
		List<PurchaseOrderDTO> purchaseOrderList = new ArrayList();
		int pageSize=0;
		PurchaseOrderDTO purchaseOrderDTO = null;
		driver = com.milo.amz.webdriver.utils.WebDriverFactory.getChromDriver();
		driver.get("https://sellercentral.amazon.com");
		WebElement myDynamicElement = new WebDriverWait(driver,20)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ap_email']")));

		driver.findElement(By.xpath(".//*[@id='ap_email']")).sendKeys("michael.liu01@gmail.com");
		driver.findElement(By.xpath(".//*[@id='ap_password']")).sendKeys("Ajkml@5896");
		
		driver.findElement(By.xpath(".//*[@id='signInSubmit']")).click();
		//try {
		    /*TimeUnit.NANOSECONDS.sleep(100);
		    TimeUnit.MICROSECONDS.sleep(100);
		    TimeUnit.MILLISECONDS.sleep(100);
		    TimeUnit.SECONDS.sleep(20);
		    TimeUnit.MINUTES.sleep(100);
		    TimeUnit.HOURS.sleep(100);
		    TimeUnit.DAYS.sleep(100);*/
		//} catch (InterruptedException e) {
		    //Handle exception
		//}
		ProductDTO product = productService.findByASIN("B01LP151LK");
		//ProductDTO product = productList.get(0);
		/*
		 * for (ProductDTO product:productList) {
		 */
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
               try {
				FileUtils.copyFile(scrFile, new File("test.png"), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               driver.get("https://sellercentral.amazon.com/gp/orders-v2/list/ref=ag_myo_dnav_xx_");       
		myDynamicElement = new WebDriverWait(driver,20)
				
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@name='searchType']")));
		myDynamicElement.sendKeys("ASIN");
		myDynamicElement = new WebDriverWait(driver,20)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='searchKeyword']")));
		myDynamicElement.sendKeys("B01LP151LK");

		myDynamicElement = driver.findElement(By.xpath(".//*[@id='_myoLO_preSelectedRangeSelect']"));

		myDynamicElement.sendKeys("Last 365 days");

		driver.findElement(By.xpath(".//*[@id='SearchID']")).click();
		try {
			myDynamicElement = new WebDriverWait(driver,20)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@id,'buyerName')]")));
		} catch (TimeoutException ex) {
			try {
				myDynamicElement = driver.findElement(By.xpath(".//*[@id='myo-message-board-alert-info-label']"));
				if (myDynamicElement != null && "Order Cancelled".equals(myDynamicElement.getText()))
					return null;
			} catch (TimeoutException ex1) {

				myDynamicElement = driver
						.findElement(By.xpath(".//*[@id='_myoV2PageTopMessagePlaceholder']/div/div/ul/li/span"));
				if (myDynamicElement != null && "Order ID Not Found".equals(myDynamicElement.getText()))
					return null;

			}
		}
		myDynamicElement = driver
				.findElement(By.xpath(".//*[@id='myo-table']/table/tbody/tr[1]/td/table/tbody/tr/td[1]/strong[2]"));
		int numberofRecords = Integer.parseInt(myDynamicElement.getText());
		pageSize = numberofRecords / 15+((numberofRecords % 15>0)?1:0);
		
		for (int pageNo = 1; pageNo <=pageSize; pageNo++) {
					System.out.println("******** page size : "+pageNo);
					if (pageNo > 1) {
						try{
						WebElement pageElement = driver
								.findElement(By.xpath("(.//*[@id='_myoSO_GoToPageForm_1']/table/tbody/tr/td[2]/input[1])[1]"));
						pageElement.sendKeys(String.valueOf(pageNo));
						WebElement goToPageElement = driver
								.findElement(By.xpath("(.//*[@id='_myoSO_GoToPageForm_1']/table/tbody/tr/td[3]/input)[1]"));
						goToPageElement.click();
						new WebDriverWait(driver,20);
						}
						catch(Exception e)
						{
							
						}
					}
					
					//List<WebElement> myDynamicElements = driver.findElements(By.xpath(".//*[contains(@id,'buyerName')]"));
					for (int index=1;index<15;index++) {
						try{
										myDynamicElement= driver.findElement(By.xpath("(.//*[contains(@href,'orderID')])["+index+"]"));
										String orderId=myDynamicElement.getText();
										if (purchaseOrderService.findBySellerOrderId(orderId)==null)
										{
										
													
											       WebElement buyerElement=driver.findElement(By.xpath("(.//*[contains(@id,'buyerName')])["+index+"]"));
													buyerElement.click();
									
													buyerElement = new WebDriverWait(driver,20).until(
															ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='commMgrCompositionSubject']")));
									
													purchaseOrderDTO = new PurchaseOrderDTO();
													purchaseOrderDTO.setBuyerId(GeneralUtils.getParameterByName("buyerID", driver.getCurrentUrl()));
													
													
													purchaseOrderDTO.setSellerOrderId(orderId);
													purchaseOrderList.add(purchaseOrderDTO);
													try {
														purchaseOrderService.save(purchaseOrderDTO);
													} catch (Exception e) {
															e.printStackTrace();
													}
									
													driver.navigate().back();
													try {
														myDynamicElement = new WebDriverWait(driver,20)
																.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@id,'buyerName')]")));
													} catch (TimeoutException ex) {
														try {
															myDynamicElement = driver.findElement(By.xpath(".//*[@id='myo-message-board-alert-info-label']"));
															if (myDynamicElement != null && "Order Cancelled".equals(myDynamicElement.getText()))
																return null;
														} catch (TimeoutException ex1) {
									
															myDynamicElement = driver
																	.findElement(By.xpath(".//*[@id='_myoV2PageTopMessagePlaceholder']/div/div/ul/li/span"));
															if (myDynamicElement != null && "Order ID Not Found".equals(myDynamicElement.getText()))
																return null;
									
														}
										}
										}
						}
										catch(Exception ex)
										{
											ex.printStackTrace();
										}
						
					}
		

		 }


		return new ListItemReader<>(purchaseOrderList);

		// return new ListItemReader<>(Arrays.asList("one","three","foure"));

	}
/*
	@Bean
	@StepScope
	public ItemProcessor<PurchaseOrderDTO, PurchaseOrderDTO> orderProcessor() {
		return new ItemProcessor<PurchaseOrderDTO, PurchaseOrderDTO>() {

			@Override
			public PurchaseOrderDTO process(PurchaseOrderDTO purchaseOrderDTO) throws Exception {
				return purchaseOrderDTO;

			}

		};

	}*/

	@Bean
	@StepScope
	public ItemWriter<PurchaseOrderDTO> orderWriter() {
		return new ItemWriter<PurchaseOrderDTO>() {

			@Override
			public void write(List<? extends PurchaseOrderDTO> items) throws Exception {

				/*for (PurchaseOrderDTO item : items) {
					try {
						purchaseOrderService.save(item);
					} catch (Exception e) {
							e.printStackTrace();
					}

				}*/

			}
		};

	}

	@Bean
	public Step ordrProcess() {
		// System.out.println("name is "+name);
		return stepBuilderFactory.get("orderHtmlStep").<String, String>chunk(2).listener(new OrderChunkListener())
				.faultTolerant().skip(TimeoutException.class).skip(StaleElementReferenceException.class).reader(orderReader()).writer(orderWriter()).build();
	}

	@Bean
	public Job orderSteps() throws Exception {
		return jobBuilderFactory.get("amazonOrderHtmlScrappedJob").incrementer(new RunIdIncrementer())
				.start(ordrProcess()).build();
	}
}
