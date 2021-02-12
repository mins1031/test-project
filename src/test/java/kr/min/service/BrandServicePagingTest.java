package kr.min.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.min.domain.Criteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BrandServicePagingTest {
     
	@Setter(onMethod_= {@Autowired})
	private BrandService service;
	
	@Test
	public void testPaging() {
		
		
		service.getList(new Criteria(2,10)).forEach(brand -> log.info(brand));
	}
}
