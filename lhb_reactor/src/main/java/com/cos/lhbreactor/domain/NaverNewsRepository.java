package com.cos.lhbreactor.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

public interface NaverNewsRepository extends ReactiveMongoRepository<NaverNews, String> {
	
	@Tailable // 몽고DB에 있는 데이터를 다 소비해도 연결을 유지하고 있다가 데이터가 추가되면 자동으로 가져온다
	@Query("{}") // {} -> findAll()이랑 같은 거다
	Flux<NaverNews> mFindAll(); // Flux를 리턴하는 mFindAll()함수 생성
	

}
