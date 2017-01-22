package com.milo.amz.review.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.milo.amz.review.service.ContactReviewerService;
@Service
public class ContactReviewServiceImpl implements ContactReviewerService {
	private WebDriver driver;
	private void loginInSellerCentral() {
		driver = com.milo.amz.webdriver.utils.WebDriverFactory.getChromDriver();
		driver.get("https://sellercentral.amazon.com");
		WebElement myDynamicElement = new WebDriverWait(driver, 20)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ap_email']")));

		driver.findElement(By.xpath(".//*[@id='ap_email']")).sendKeys("michael.liu01@gmail.com");
		driver.findElement(By.xpath(".//*[@id='ap_password']")).sendKeys("Ajkml@5896");

		driver.findElement(By.xpath(".//*[@id='signInSubmit']")).click();
		
		
	}
	@Override
	public void sendEmail(String orderId, String buyerId, String marketPlaceId,String message) {
		loginInSellerCentral();
		driver.get("https://sellercentral.amazon.com/gp/help/contact/contact.html?orderID="+orderId+"&marketplaceID="+marketPlaceId+"&buyerID="+buyerId);
		WebElement buyerElement = new WebDriverWait(driver, 20).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='commMgrCompositionSubject']")));
		driver.findElement(By.xpath(".//*[@id='commMgrCompositionMessage']")).sendKeys(message);
		
		//driver.findElement(By.xpath(".//*[@id='sendemail']")).click();
		

	}

}
