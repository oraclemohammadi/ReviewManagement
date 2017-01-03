package com.milo.amz.review.batch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class OrderChunkListener {
	@BeforeChunk
   public void beforeChunk(ChunkContext chunkContext){
	   System.out.println("before Chunk");
   }
	@AfterChunk
   public void afterChunck(ChunkContext chunkContext){
		 System.out.println("after Chunk"); 
   }
}
