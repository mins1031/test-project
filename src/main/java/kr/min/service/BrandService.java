package kr.min.service;

import java.util.List;

import kr.min.domain.Brand;
import kr.min.domain.BrandAttachVO;
import kr.min.domain.Criteria;

public interface BrandService {
     
	public List<Brand> getList(Criteria cri);
	
    public Brand get(Long bno);
    
    public boolean remove(Long bno);
    
    public void register(Brand brand);
    
    public boolean modify(Brand brand);
    
    public int getTotal(Criteria cri);
    
    public List<BrandAttachVO> getAttachList(Long bno);
}
