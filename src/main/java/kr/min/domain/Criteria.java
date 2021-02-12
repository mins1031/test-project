package kr.min.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
   /*criteria는 list를 받아올때 특정한 제약을 주는 역할임. 그냥 모든 리스트 받으면 select * from tbl-brand하면 되지만
	그렇게하면 안되기에 페이징처리나 검색처리를 위해 제약을 걸어주는 것임 페이징 처리를 생각해보면 결국 사용자가 '한페이지당 얼마의
	내용을 볼것이다' 라는 내용을 전달하는 기능이있고 이 내용을 담아 주는 택배상자같은 기능임. 검색처리역시 리스트들 사이에서 
	해당 키워드의 내용을 찾아야 하기에 criteria에 담김. */
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null ? new String[] {}: type.split("");
	}
	
	public String getListLink() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
		//UriComponentsBuilder은 브라우저에서 get방식등의 파라미터 전송에 사용되는 쿼리스트링을 손쉽게 처리할수 있는 클래스임.
		//게시물삭제후에 url에 페이지번호나,검색조건등 쿼리스트링을 달고다니면서 유지하는게 영 까다로워서 여기 객체 형태로 저장해놓음.
	}
}
