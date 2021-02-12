package kr.min.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.min.domain.Criteria;
import kr.min.domain.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
    
	private Long bnoArr[] = {156L,155L,154L,153L,152L};
	
	@Setter(onMethod_= {@Autowired})
	private ReplyMapper mapper;
	
	/*@Test
	public void testInsert() {
		
		for(int i =0; i<10;i++) {
			ReplyVO vo = new ReplyVO();
			vo.setBno(155L);
			vo.setReply("페이징"+i);
			vo.setReplyer("미뇽"+i);
			mapper.insert(vo);
		}
	}
	@Test
	public void testRead() {
		
		Long num = 25L;
		
		log.info(mapper.read(num));
	}
	@Test
	public void testDelete() {
		int num = 26;
		
		log.info(mapper.delete(num));
	}
	@Test
	public void testUpdate() {
		
		ReplyVO vo = new ReplyVO();
		vo.setRno(30L);
		vo.setBno(156L);
		vo.setReply("댓글 업데이트");
	    vo.setReplyer("scoot");
	    
	    mapper.update(vo);
	}
	@Test
	public void testUpdate() {
		
		Criteria cri = new Criteria();
		List<ReplyVO> list = mapper.getListWithPaging(cri,155L);
		
		list.forEach(reply -> log.info(reply));
	}*/ 	
	@Test
	public void testList2() {
		
		Criteria cri = new Criteria(2,10);
		List<ReplyVO> list = mapper.getListWithPaging(cri,155L);
		
		list.forEach(reply -> log.info(reply));
	}
}
