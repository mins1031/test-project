package kr.min.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.min.domain.Brand;

public interface TimeMapper {
	
    @Select("SELECT * FROM NOW()")
    public String getTime();
    
    public int getCount();
}
