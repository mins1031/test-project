package kr.min.service;

import java.util.List;

import kr.min.domain.Criteria;
import kr.min.domain.ReplyPageDTO;
import kr.min.domain.ReplyVO;

public interface ReplyService {
   
	public int Register(ReplyVO vo);
	
	public ReplyVO get(Long rno);
	
	public int remove(Long rno);
	
	public int modify(ReplyVO vo);
	
	public List<ReplyVO> getList(Criteria cri, Long bno);
	
	public ReplyPageDTO getListPage(Criteria cri,Long bno);
}
