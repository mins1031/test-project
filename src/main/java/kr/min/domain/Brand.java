package kr.min.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Brand {
	
	private Long bno;
    private String name;
    private String style;
    private String content;
    private String price;
    private Date regdate;
    private Date updateDate;
    
    private int replyCnt;
 
    private List<BrandAttachVO> attachList;
}
