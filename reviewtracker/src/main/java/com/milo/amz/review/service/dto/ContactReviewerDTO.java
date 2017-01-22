package com.milo.amz.review.service.dto;

import lombok.Getter;
import lombok.Setter;

public class ContactReviewerDTO {
	@Getter @Setter public String orderId;
	@Getter @Setter public String buyerId;
	@Getter @Setter public String marketPlaceId;
	@Getter @Setter public String message;
}
