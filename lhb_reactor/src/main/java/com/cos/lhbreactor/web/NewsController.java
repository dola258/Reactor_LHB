package com.cos.lhbreactor.web;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.lhbreactor.util.DataStart;
import com.cos.lhbreactor.util.NaverCraw;
import com.cos.lhbreactor.domain.NaverNews;
import com.cos.lhbreactor.domain.NaverNewsRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@RequiredArgsConstructor
@RestController
public class NewsController {

	private final NaverNewsRepository naverNewsRepository;
	private final NaverCraw naverCraw;
	
	@CrossOrigin // 서버는 다른 도메인의 자바스크립트 요청을 거부한다(허용해주는 어노테이션)
	@GetMapping(value = "/news", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<NaverNews> findAll() {
		return naverNewsRepository.mFindAll()
				.subscribeOn(Schedulers.boundedElastic()); // SSE 연결
 

	}



}
