package kr.min.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
    
	private int replyCnt;
	private List<ReplyVO> list;
	//단순하게 댓글 전체를 보여주는게 아닌 댓글 목록과 전체댓글갯수도 필요함. 그래서 전체댓갯수 replyCny와 댓글 목록 list 정의
}
