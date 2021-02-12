package kr.min.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.min.domain.Brand;
import kr.min.mapper.TimeMapper;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class TestServiceImpl implements TestService{
     
	@Autowired
	private TimeMapper mapper;

	@Override
	public List<Brand> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
