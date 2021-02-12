package kr.min.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.min.domain.Criteria;
import kr.min.domain.ReplyPageDTO;
import kr.min.domain.ReplyVO;
import kr.min.mapper.BrandMapper;
import kr.min.mapper.ReplyMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{
   
	@Setter(onMethod_= @Autowired)
	private  ReplyMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private  BrandMapper brandMapper;

	@Transactional
	@Override
	public int Register(ReplyVO vo) {
		
		log.info("댓글 등록");
		
		brandMapper.updateReplyCnt(vo.getBno(), 1);
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		
		log.info("댓글 1건 조회");
		return mapper.read(rno);
	}
    
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info(rno +"번 댓글 삭제");
		
		ReplyVO vo = mapper.read(rno);
		
		brandMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO vo) {

		log.info(vo.getRno()+"번 댓글 수정");
		return mapper.update(vo);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		log.info(bno+"번 게시물의 댓글 목록 조회");
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {

		int total = mapper.getCountByBno(bno);
		
		return new ReplyPageDTO(
				mapper.getCountByBno(bno),
				mapper.getListWithPaging(cri, bno)
		);
	}


}
