package kr.min.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.min.domain.Brand;
import kr.min.domain.BrandAttachVO;
import kr.min.domain.Criteria;
import kr.min.mapper.BrandAttachMapper;
import kr.min.mapper.BrandMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BrandServiceImpl implements BrandService{
    
	@Setter(onMethod_=@Autowired)
	private BrandMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private BrandAttachMapper attachMapper;
	
	
	/*@Autowired
	public BrandServiceImpl(BrandMapper mapper) {
		this.mapper = mapper;
	}
	
	@Autowired
	public BrandServiceImpl(BrandAttachMapper attachMapper) {
		this.attachMapper = attachMapper;
	}*/

	
	@Override
	public List<Brand> getList(Criteria cri) {
		
        log.info("get list with criteria : " +cri);
        
		return mapper.getListWithPaging(cri);
	}

	@Override
	public Brand get(Long bno) {
		
		log.info( bno + "���� �÷� ��ȸ");
		
		return mapper.read(bno);
	}
	
	@Transactional
	@Override
	public boolean remove(Long bno) {
        
		log.info(bno+"�� ����");
		
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) ==1 ;
	}

	@Transactional
	@Override
	public void register(Brand brand) {
		
		log.info("브랜드" +brand.getName());
		mapper.insertSelectKey(brand);
		
		if(brand.getAttachList() == null || brand.getAttachList().size() <= 0) {
			return;
		}
		brand.getAttachList().forEach(attach -> {
			
			long lastBno = Long.parseLong(attachMapper.insertBno());
			attach.setBno(lastBno);
			attachMapper.insert(attach);;
		});
		
	}

	@Transactional
	@Override
	public boolean modify(Brand brand) {
		
		log.info("����");
		
		attachMapper.deleteAll(brand.getBno());
		//해당 게시물의 첨부파일을 그냥 다 삭제해버림.;
		boolean modifyResult = mapper.update(brand) ==1;
		
		if(modifyResult && brand.getAttachList() != null && brand.getAttachList().size() > 0) {
			brand.getAttachList().forEach(attach -> {
				
				long lastBno = Long.parseLong(attachMapper.insertBno());
				attach.setBno(lastBno);
				attachMapper.insert(attach);;				
			});
		}
		return modifyResult;
	}


	@Override
	public int getTotal(Criteria cri) {
		
		log.info("data Total count");
		
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BrandAttachVO> getAttachList(Long bno) {
		
		//이건 조회할때 해당 bno에 맞는 첨부파일 가져오는 로직임.
		log.info("get Attach list by bno" + bno);
		
		return attachMapper.findByBno(bno);
	}
	
	
}
