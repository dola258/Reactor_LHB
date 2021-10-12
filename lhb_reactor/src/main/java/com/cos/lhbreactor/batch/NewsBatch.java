package com.cos.lhbreactor.batch;


import java.time.Duration;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.lhbreactor.domain.NaverNews;
import com.cos.lhbreactor.domain.NaverNewsRepository;
import com.cos.lhbreactor.util.NaverCraw;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class NewsBatch {
	
	private final NaverNewsRepository naverNewsRepository;
	private final NaverCraw naverCraw;

	// 초, 분, 시, 일, 월, 주
//	@Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
	@Scheduled(cron = "0 38 19 * * *", zone = "Asia/Seoul")
	public void newsCrawAndSave() throws Exception {

		List<NaverNews> newsList =	 naverCraw.collect();
		
		Flux.fromIterable(newsList)
		.delayElements(Duration.ofSeconds(1))
		.flatMap(this.naverNewsRepository::save)
		.subscribe();
	}
}