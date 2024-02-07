package com.example.demo.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class OpenAPITest {
	
	//encoder 인증키
	String serviceKey = "63Y%2BPBTMe2b6d6o8mNvTBVp%2FZTXGPkBLSThxnPEoEUKrlw4klI1aAFDbtiOhPtGlsxv51feudvvBEM4vJEvaKw%3D%3D";
	
	String dataType = "JSON";
	
	String code = "11B20201";
	
	
	public String getWeather() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode(code , "UTF-8")); /*11A00101(백령도), 11B10101 (서울), 11B20201(인천) 등... 별첨 엑셀자료 참조(‘육상’ 구분 값 참고)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
   
        
        return sb.toString();
	}
	
	
	//json문자열을 클래스로 변환하는 메소드 만들기
	@Test
	public void jsonToDto() throws IOException{
		
		//json 문자열을 클래스로 변환해주는 매퍼 클래스 생성
		ObjectMapper mapper = new ObjectMapper();
		
		//분석할 수 없는 구문을 무시하는 옵션 설정
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		//날씨 데이터 가져오기
		String weather = getWeather();
		
		//json 문자열을 클래스로 변환
		Root root = null;
		root = mapper.readValue(weather, Root.class);
		
		//결과 코드만 추출
		System.out.println(root.response.header.resultCode);
		
		//결과 메세지만 추출
		System.out.println(root.response.header.resultMsg);
		
		
		System.out.println(root.response.body.items.item.get(0).wfCd);
	}
}
