package kr.min.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.min.domain.Brand;
import kr.min.domain.Criteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BrandMapperTest {
    
	@Setter(onMethod_= {@Autowired})
	private BrandMapper mapper;
	
	/*@Test
	public void getListTest() {
		
		mapper.getList().forEach(brand -> log.info(brand));
	}*/
	@Test
	public void getPagingTest() {
		
		Criteria cri = new Criteria(3,10);
		List<Brand> list = mapper.getListWithPaging(cri);
		list.forEach(brand -> log.info(brand));
	}
}
