package com.milo.amz.review.batch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.milo.amz.review.batch.listener.OrderChunkListener;
import com.mysql.fabric.xmlrpc.base.Array;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class ReviewCollectorBatchConfiguration {
	 @Autowired
	  private JobBuilderFactory jobBuilderFactory;

	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  
      @Bean 
      public ItemReader<String> reader(){
    	 return new ListItemReader<>(Arrays.asList("one","three","foure"));
      }
      
      @Bean 
      public ItemWriter<String> writer(){
    	  return new ItemWriter<String>() {
			
			@Override
			public void write(List<? extends String> items) throws Exception {
				for (String item:items)
				{
					System.out.println(item);
					
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
	    return stepBuilderFactory.get("step1")
	        .<String,String>chunk(2).listener(new  OrderChunkListener())
	        .faultTolerant()
	        .reader(reader())
	        .writer(writer())
	        .build();
	  }
	  

	  
	  
	  @Bean
	  public Job reviewProcessSteps() throws Exception {
	    return jobBuilderFactory.get("collectReviewJob")
	        .incrementer(new RunIdIncrementer())
	        .start(ordrProcess())  
	        .build();
	  }
}
