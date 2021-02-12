package kr.min.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.min.domain.Brand;
import kr.min.domain.Criteria;

public interface BrandMapper {
     
	public List<Brand> getList();
	
	public List<Brand> getListWithPaging(Criteria criteria); 
	
	public Brand read(Long bno);
	//1건 조회는 아이디값을 받아서 그 아이디의 내용들을 다시 반환해 줘야하기 때문에 인자는 bno 리턴타입은 Brand객체임.
	public int delete(Long bno); 
	//삭제는 어떤것을 삭제할 것인지만 db에 전달하면 되기 때문에 bno값을 받고 성공실패여부를 boolean으로 전송하기에 일단 리턴은int.      
	public int update(Brand brand);

	public void insert(Brand brand);
	//1건 추가인데 이부분은 추가하는 내용을 담아서 brand객체가 오기에 담겨져있는 내용을 쿼리문에 적용시켜 db작업을 수행함.
	public void insertSelectKey(Brand brand);
	
	public int getTotalCount(Criteria cri);
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	//mybatis는 2개이상의 데이터를 디비로 보낼때 1) 별도의 객체로 구성하거나 2) map으로 구성하거나 3) @Param으로 구성하는방법이 있음.
}
