package com.milo.amz.review.batch.listener;

import java.util.List;

import javax.batch.api.chunk.listener.ItemWriteListener;

public class ReviewItemWriterListener<ReviewDTO> implements ItemWriteListener {

	@Override
	public void beforeWrite(List<Object> items) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterWrite(List<Object> items) throws Exception {
		System.out.println("test"+items.size());
		
	}

	@Override
	public void onWriteError(List<Object> items, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
