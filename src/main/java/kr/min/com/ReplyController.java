package kr.min.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.min.domain.Criteria;
import kr.min.domain.ReplyPageDTO;
import kr.min.domain.ReplyVO;
import kr.min.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/replies/*")
@Log4j
@RequiredArgsConstructor
public class ReplyController {
      
	@Autowired
	private final ReplyService service;
	
	@PostMapping(value = "/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		
		log.info("ReplyVo : " +vo);
		
		int insertCount = service.Register(vo);
		
		log.info("Reply Insert Count : " +insertCount);
		
		return insertCount == 1 ? 
			new ResponseEntity<>("success", HttpStatus.OK) 
		  : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_JSON_UTF8_VALUE })
	 public ResponseEntity<ReplyPageDTO> getList(
			 @PathVariable("page") int page,
			 @PathVariable("bno") Long bno){
		
		log.info("getList..........");
		 Criteria cri;
		 
		if(page < 0)
		  cri = new Criteria(1,10);
		else 
		  cri = new Criteria(page,10);
		log.info(cri);
		
		return new ResponseEntity<>(service.getListPage(cri, bno),
				HttpStatus.OK);
	}
	
	@GetMapping(value="/{rno}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	 public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		
		log.info(rno+"번 조회");
		return new ResponseEntity<>(service.get(rno),
				HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{rno}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> delete(@PathVariable("rno") Long rno){
		
		log.info(rno+"번 삭제");
		
		return service.remove(rno) == 1 ? 
				new ResponseEntity<>("success", HttpStatus.OK)
			  : new	ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method= {RequestMethod.PUT,RequestMethod.PATCH},
			value="/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> update(
			@PathVariable("rno") Long rno,
			@RequestBody ReplyVO vo){
		
		vo.setRno(rno);
		
		log.info(rno+"번 수정");
		
		log.info("modify: "+ vo);
		return service.modify(vo) == 1 ? 
				new ResponseEntity<>("success", HttpStatus.OK)
			  : new	ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
