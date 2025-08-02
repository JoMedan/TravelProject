// src/test/java/com/example/travelproject/TravelProjectApplicationTests.java
package com.example.travelproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(
		webEnvironment = WebEnvironment.NONE
		// classes 설정을 아예 생략하면, 기본적으로
		// TravelProjectApplication.class 를 찾아서 로드합니다.
)
class TravelProjectApplicationTests {

	@Test
	void contextLoads() {
		// ApplicationContext가 정상적으로 뜨면 테스트가 성공합니다.
	}

}
