package com.cos.lhbreactor.domain;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "naver_realtime_success")
public class NaverNews {
	@Id
	private String _id;
	
	private String title;
	private String company;
//	private Timestamp createAt; 
	private Date createAt;
}   
