package com.cos.lhbreactor.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.lhbreactor.domain.NaverNews;

@Component
public class NaverCraw {
	String startDay = DataStart.standardDay();
	String endDay = DataStart.endDay();
	
	long urlNum = DataStart.urlStart;

	public List<NaverNews> collect() {

		RestTemplate rt = new RestTemplate();
		List<NaverNews> newsList = new ArrayList<>();
		
		int successCount = 0;
		int errorCount = 0;
		int crawCount = 0;

		while (true) {
			String strnum = String.format("%010d", urlNum);
	 
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + strnum;

			try {
				
				String html = rt.getForObject(url, String.class);
				
				Document doc = Jsoup.parse(html);
				
				// title
				Element titleElement = doc.selectFirst("#articleTitle");
				String title = titleElement.text();
				System.out.println(title);
				
				// company
				Element companyElement = doc.selectFirst(".press_logo a img");
				String companyAttr = companyElement.attr("alt");
				String company = companyAttr;
				System.out.println(company);
				
				// createAt
				Element createAtElement = doc.selectFirst(".t11");
				String createAtText = createAtElement.text() + ":00"; // yyyy.MM.dd. 오전/오후 hh:mm:ss
				
				String date = createAtText.substring(0, 4) + "-" + createAtText.substring(5, 7) + "-"
						+ createAtText.substring(8, 10) + " "; // yyyy-MM-dd
				String dateTime = "";
				
				// 오후, hh:mm:ss가 7자리면 앞에 0을 붙이고 앞 2글자 12더해주기
				if (createAtText.substring(12, 14).equals("오후")) {
					String time = createAtText.substring(15);
					if (time.length() != 8) {
						time = "0" + time;
					}
					
					int beforeHour = Integer.parseInt(time.substring(0, 2));
					int plus = 12;
					
					// 오후 12:13분일 경우 12를 하면 24:13분이됨
					if (beforeHour == 12) {
						dateTime = date + beforeHour + time.substring(2);
					} else {
						int afterHour = beforeHour + plus;
						dateTime = date + afterHour + time.substring(2);
					}
				}
				
				// 오전, hh:mm:ss
				if (createAtText.substring(12, 14).equals("오전")) {
					String time = createAtText.substring(15);
					if (time.length() != 8) {
						time = "0" + time;
					}
					dateTime = date + time;
				}
				
				System.out.println("기사일 : " + dateTime);
				System.out.println("기준시작일 : " + startDay);
				System.out.println("기준마감일 : " + endDay);
				
				if(startDay.equals(dateTime)) {
					break;
				}
				
				if(endDay.equals(dateTime)) {
					SimpleDateFormat sdFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					Date createAt = sdFmt.parse(dateTime);
					
					// Timestamp createAt = Timestamp.valueOf(dateTime);
					
					// 오브젝트로 저장
					NaverNews news = NaverNews.builder().title(title).company(company).createAt(createAt).build();
					
					newsList.add(news);
					crawCount++;
				}
				successCount++;
				
			} catch (Exception e) {
				System.out.println("통신오류");
				errorCount++;
			}
			urlNum++;
		}
		System.out.println("배치프로그램 종료=========");
		System.out.println("성공횟수 :" + successCount);
		System.out.println("실패횟수 :" + errorCount);
		System.out.println("크롤링횟수 :" + crawCount);
		System.out.println("마지막 aid : " + urlNum);
		System.out.println("컬렉션에 담은 크기" + newsList.size() );
		

		return newsList;
	}

}
