package com.ezen.spring;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.repository.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
class ApplicationTests {
	@Autowired
	private BoardMapper boardMapper;

	@Test
	void contextLoads() {
		for(int i=1; i<=300; i++){
			BoardVO boardVO = BoardVO.builder()
					.title("Test Title "+i)
					.writer("tester"+((int)(Math.random()*100)+1))
					.content(i+"st Text Content...")
					.build();
			boardMapper.register(boardVO);
		}
	}
}
