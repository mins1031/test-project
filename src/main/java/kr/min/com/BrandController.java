package kr.min.com;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.min.domain.Brand;
import kr.min.domain.BrandAttachVO;
import kr.min.domain.Criteria;
import kr.min.domain.PageDTO;
import kr.min.service.BrandService;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/brand/*")
public class BrandController {
    
	private BrandService service;
	
	@Autowired
	public BrandController(BrandService service) {
		this.service = service;
	}
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("brand 리스트 입니다.");
		model.addAttribute("Blist", service.getList(cri));
		//model.addAttribute("pageMaker", new PageDTO(cri,123));
		
		int total = service.getTotal(cri);
		
		log.info("total:" + total);
		model.addAttribute("pageMaker", new PageDTO(cri,total));
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
	     
		log.info("1건 조회 or 1건 수정");
        model.addAttribute("brand", service.get(bno));	
        
	}
	
	@PostMapping("/register")
	public String register(Brand brand, RedirectAttributes rttr) {
		
		log.info("1건 등록");
		//log.info("bno : " +brand.getB);
		if(brand.getAttachList() != null) {
			brand.getAttachList().forEach(attach -> log.info(attach));
		}
		service.register(brand);
		
		rttr.addFlashAttribute("result", brand.getBno());//���â�� ���� ����ֱ� �����ε�
		return "redirect:/brand/list";
		//RedirectAttributes : servlet의 redirect는 기본적ㅇ로  post/redirect/get방식으로 동작하는데 get으로 전환될때 post의 내용이 날아가 버리기떄문에
		//RedirectAttributes는  리다이렉트 하기전 모든 플레시속서을 세션에 복사후 get이후 다시 모델에 복사해주는 방식을 사용하기에 url에 파라미터가 노출되지 않는다.
	}
	
	@PostMapping("/modify")
	public String modify(Brand brand,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("해당 데이터 수정");
		service.modify(brand);
		
		
		rttr.addFlashAttribute("result", brand.getBno());
		
		rttr.addAttribute("pageNum", cri.getPageNum());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("amount", cri.getAmount());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("type", cri.getType());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("keyword", cri.getKeyword());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		
		return "redirect:/brand/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("1건 삭제.");
		/*if(service.remove(bno)) {
			rttr.addFlashAttribute("result","succes");
		}	
		
		rttr.addAttribute("pageNum", cri.getPageNum());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("amount", cri.getAmount());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("type", cri.getType());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.
		rttr.addAttribute("keyword", cri.getKeyword());//list페이지로 보낼때 직전에 있었던 화면 그대로 출력하기 위함.*/
		
		log.info("remove..." + bno);

		List<BrandAttachVO> attachList = service.getAttachList(bno);

		if (service.remove(bno)) {

			// delete Attach Files
			deleteFiles(attachList);

			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
	
	private void deleteFiles(List<BrandAttachVO> attachList) {
		    
		    if(attachList == null || attachList.size() == 0) {
		      return;
		    }
		    
		    log.info("delete attach files...................");
		    log.info(attachList);
		    
		    attachList.forEach(attach -> {
		      try {        
		        Path file  = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\" + attach.getUuid()+"_"+ attach.getFileName());
		        
		        Files.deleteIfExists(file);
		        
		        if(Files.probeContentType(file).startsWith("image")) {
		        
		          Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\s_" + attach.getUuid()+"_"+ attach.getFileName());
		          
		          Files.delete(thumbNail);
		        }
		
		      }catch(Exception e) {
		        log.error("delete file error" + e.getMessage());
		      }//end catch
		    });//end foreachd
		  }
	
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@GetMapping(value="/getAttachList",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//서버에서 데이터가져오며 json가공하는 부분이 이부분인듯
	 @ResponseBody//컨트롤러에가 @RestController가 아니기 때문에 붙혀줌 이번파트 끝나고 어노테이션 복습해서 정리할 예쩡
	 public ResponseEntity<List<BrandAttachVO>> getAttachList(Long bno){
		
		log.info("get attach list by bno" + bno);
		
	    return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK); 
	}
	
}
