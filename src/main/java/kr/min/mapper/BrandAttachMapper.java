package kr.min.mapper;

import java.util.List;

import kr.min.domain.BrandAttachVO;

public interface BrandAttachMapper {
     
	public void insert(BrandAttachVO vo);
	
	public String insertBno();
	
	public void delete(String uuid);
	
	public List<BrandAttachVO> findByBno(Long bno);
	
	public void deleteAll(Long bno);
	
	public List<BrandAttachVO> getOldFiles();
} 
