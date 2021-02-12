package kr.min.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
   
	private int startPage;
	private int endPage;
    private boolean prev, next;
    
    private int total;
    private Criteria cri;
    
    public PageDTO(Criteria cri, int total) {
    	
    	this.cri = cri;
    	this.total = total;
    	
    	this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
    	//페이지 넘버 정의 위해 math.ceil을 사용해 소수점 올림으로 (1.5->2로처리, 0.7 ->1.0으로 처리)해주는데 페이지를 실수로 할 수는 없기에 넘버 받아서 실수로 바꾸고
    	//다시 10을 곱해 그에 맞는 엔드 넘버를 받음 그래서 0.1~0.9까지는 1.0이 나오고 1.0~1.9 까지는 2.0이나옴 여기다 10 곱해서 endPage를 구해줌. 
    	this.startPage = endPage -9 ;
    	
    	int realEnd = (int) (Math.ceil( (total*1.0) / cri.getAmount()));
    	//토탈 데이터 갯수를 페이징처리를 위해 소수로 만들고 그것을  반올림하여 한페이지 양만큼 나누면 페이지갯수가 나옴. 그것을 readEnd에 담아놓음
    	if(realEnd < this.endPage) {
    		this.endPage = realEnd;
    	}//이제 엔드 페이지는 데이터 갯수와 상관없이 해당 페이지의 내용을 전달받아 출력하므로 이 내용이 없으면 데이터가 없는데도 페이지를 생성할 수 도 있음.
    	//데이터가 없는 페이지는 생성 안시키기 위해 데이터가 마지막으로 남아있는 페이지인 realEnd의 값보다 endPage가 크면 realEnd를 endPage로 설정해주는 것임.
    	this.prev = this.startPage > 1;
    	this.next = this.endPage < realEnd ; 
    }
}
