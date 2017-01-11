package com.milo.amz.review.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.openqa.selenium.By;
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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.milo.amz.review.GeneralUtils;
import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.milo.amz.review.batch.listener.ReviewItemWriterListener;
import com.milo.amz.review.service.ProductService;
import com.milo.amz.review.service.ReviewService;
import com.milo.amz.review.service.dto.ProductDTO;
import com.milo.amz.review.service.dto.ReviewDTO;
import com.milo.amz.review.service.mapper.OrderMapper;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class ReviewBatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Inject
	private OrderMapper orderMapper;

	private WebDriver driver;

	@Inject
	private ReviewService reviewService;
	
	@Inject
	private ProductService productService;
	
	List<Integer> numberFound=null;
	int pageSize=0;
	int reviewTaked=0;

	@Bean
	@StepScope
	public ItemReader<ReviewDTO> reviewItemReader() throws ElementNotFoundException, ParseException {
		List<ReviewDTO> reviewList=null;
			List<ProductDTO> productList= productService.findAll();
			int totalReviews=0;
			WebDriver driver = com.milo.amz.webdriver.utils.WebDriverFactory.getPhantomeJSDriver();
			for (ProductDTO product:productList)
			{
			   totalReviews=0;
				driver.get(
						"https://www.amazon.com/product-reviews/"+product.getAsin()+"/?showViewpoints=0&sortBy=byRankDescending&pageNumber=1");
				WebElement myDynamicElement = new WebDriverWait(driver, 20).until(
						ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@data-hook,'product-link')]")));
				
				numberFound= GeneralUtils.extractNumbersfromText(driver.findElement(By.xpath(".//div[@id='cm_cr-review_list']/div/span[contains(@class,'a-size-base')][1]")).getText());
				totalReviews=numberFound.size()-1;
				if (reviewService.countReviewByproduct(product.getId())<totalReviews)
				{
				pageSize=numberFound.get(numberFound.size()-1)/10;
				for (int pageNo=1;pageNo<pageSize;pageNo++)
				{
					reviewList=collectReviewForPage(product,pageNo,driver);
				}
				}
			}
		return new ListItemReader<>(reviewList);

	}

	private List<ReviewDTO> collectReviewForPage(ProductDTO product,int pageNo,WebDriver driver) throws ParseException,ElementNotFoundException{
		List<Integer> numberFound=null;
		List<ReviewDTO> reviewList=new ArrayList();
		WebElement tempElement;
		//load the page 
		driver.get(
				"https://www.amazon.com/product-reviews/"+product.getAsin()+"/?showViewpoints=0&sortBy=byRankDescending&pageNumber="+pageNo);
		WebElement myDynamicElement = new WebDriverWait(driver, 20).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@data-hook,'product-link')]")));
		
		List<WebElement> myDynamicElements = driver.findElements(By.xpath(".//div[contains(@data-hook,'review')]"));
		int reviewCount=1;
		for (WebElement webElement : myDynamicElements) {
			ReviewDTO reviewDTO=new ReviewDTO();
			numberFound= GeneralUtils.extractNumbersfromText(webElement.findElement(By.xpath("//i[contains(@data-hook,'review-star-rating')]")).getAttribute("class"));
			reviewDTO.setReviewID(webElement.getAttribute("id"));
			reviewDTO.setRating(numberFound.get(0));
			reviewDTO.setTitle(webElement.findElement(By.xpath(".//a[contains(@data-hook,'review-title')]")).getText());
			reviewDTO.setCustomerName(webElement.findElement(By.xpath(".//a[contains(@data-hook,'review-author')]")).getText());
			

				tempElement = webElement.findElement(By.xpath("//span[contains(@data-hook,'avp-badge')]"));
				if (tempElement != null && "Verified Purchase".equals(tempElement.getText()))
						reviewDTO.setVerifiedPurchase(true);
	
			

			String customerhref=webElement.findElement(By.xpath(".//a[contains(@data-hook,'review-author')]")).getAttribute("href");
			
			String patternString = "(/gp/pdp/profile/)(.+)";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(customerhref);
			while(matcher.find()) {
				// cutomer id;
				reviewDTO.setCustomerID(matcher.group(2));
				//customerName = customer.text();
			}
			
			
			tempElement=webElement.findElement(By.xpath("//span[contains(@class,'review-votes')]"));
			if (tempElement != null )
			{
				numberFound=GeneralUtils.extractNumbersfromText(tempElement.getText());
				if (numberFound!=null)
				reviewDTO.setHelpfulVotes(numberFound.get(0));
			}
				
			
			
			
			String reviewDate=webElement.findElement(By.xpath(".//span[contains(@data-hook,'review-date')]")).getText().substring(3); // remove "On ";
			reviewDTO.setReviewDate(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(reviewDate));
			
			try {
				tempElement = webElement.findElement(By.xpath(".//a[contains(@class,'top-')]"));
				if (tempElement != null)
					reviewDTO.setSpecificNote(tempElement.getText());
			} catch (Exception e) {

			}
			
			reviewDTO.setContent(webElement.findElement(By.xpath(".//span[contains(@data-hook,'review-body')]")).getText());
			try{
				reviewDTO.setProductId(product.getId());
				reviewService.save(reviewDTO);
				reviewList.add(reviewDTO);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return reviewList;
	}
	/*
	  @Bean
	  
	 @StepScope 
	 public ItemProcessor<ProductDTO ,ReviewDTO> reviewProcessor()
	  { 
		  return new ItemProcessor<ProductDTO ,ReviewDTO>(){

			@Override
			public ReviewDTO process(ProductDTO product) throws Exception {
				
				 return new ReviewDTO();
			}
	  };
  
		 
	 }*/
	

	@Bean
	@StepScope
	public ItemWriter<ReviewDTO> reviewItemWriter() {
		return new ItemWriter<ReviewDTO>() {

			@Override
			public void write(List<? extends ReviewDTO> items) throws Exception {

				/*for (ReviewDTO item : items) {
					reviewService.save(item);

				}*/

			}
		};

	}
	

	@Bean
	public Step reviewItemProcess() throws ElementNotFoundException, ParseException {
		// System.out.println("name is "+name);
		return stepBuilderFactory.get("reviewStep").<String, String>chunk(2).listener(new OrderChunkListener())
				.faultTolerant().reader(reviewItemReader())
				// .processor(processor())
				.writer(reviewItemWriter()).listener(new ReviewItemWriterListener())
				.build();
	}

	@Bean
	public Job reviewItemSteps() throws Exception {
		return jobBuilderFactory.get("reviewJob").incrementer(new RunIdIncrementer()).start(reviewItemProcess()).build();
	}
}
