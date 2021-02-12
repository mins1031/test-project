package kr.min.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.min.domain.AttachFileDTO;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j

public class UploadController {
          
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload form");
	}
	
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:\\spring_file_upload";
		
		for(MultipartFile mul : uploadFile) {
			
			log.info("------------------------------");
			log.info("Upload File Name: " +mul.getOriginalFilename());
			log.info("Upload File Size: "+mul.getSize());
			
			File saveFile = new File(uploadFolder, mul.getOriginalFilename());
			
			try {
				mul.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("upload Ajax");
	}
	
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 날짜 형식을 string으로 혹은 string형식을 날짜 형식으로 변환하기 위한 포맷 형식임.
		
		Date date = new Date();
		
		String str = sdf.format(Calendar.getInstance().getTime());
		//format의 인자에 위의 date를 넣어도됨 java기술을 참고해ㅑ 하지만 날짜형식을 문자열로 포맷하는 두가지 방법임
		return str.replace("-", File.separator);
		//str의 내용중 "-"의 내용울 File.separator로 바꿔즈는 역할을 하는 replace메서드
 //String path = File.separator+"fileName"+File.separator+"sample.jpg" = /fileName/sample,.jpg
 // 즉 OS에 상관없이 파일 경로 구분자를 생성해주는게 separator 메서드임. 주로 파일다룰때만 많이 사용할듯.	    
	}
	
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			//파일의 확장자를 확인하여 마임타입 확인.=확장자 확인한다는 뜻인듯
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE) 
	//REST방식으로 POST요청하기에 이러한 단순 url형태가 아닌 개체에대한 설정값을 지정해줘야하는듯
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
				
		log.info("update Ajax post......");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\spring_file_upload";
		
		String uploadFolderPath = getFolder();
		//날짜 형식으로 파일 을 만들어내기위한 메서드로 날짜 파일 형태의 문자열로 리턴값이 날아옴
	    File uploadPath = new File(uploadFolder, uploadFolderPath);  
	    //서버의 파일 저장경로와 getFolder메서드의 리턴값을 인자인 현재날짜 년/월/일로 줌 
	    log.info("uploadPath: " + uploadPath);
	    
	    if(uploadPath.exists() == false) {
	    	uploadPath.mkdirs();
	    }
	    //해당 파일이 있으면 if문실행안되고 없다면 직접생성해주는 그런코드 mkdir()보다 처음에 구성할떈 나은듯..꼼꼼히 하자
	    
		for (MultipartFile multipartFile : uploadFile) {
			
			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			AttachFileDTO attacDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +1);
			log.info("only file name: " + uploadFileName);
			attacDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
		    uploadFileName = uuid.toString() + "_" + uploadFileName;
		    //랜덤으로 만들어진 uuid값과 파일의 원래이름을 _로 이어서 저장함.
		    
			//File saveFile = new File(uploadFolder ,uploadFileName);
			//File saveFile = new File(uploadPath ,uploadFileName);
			/* 1) multipartFile로 파일을 배열형태인자로 받음.
			   2) 파일이 업로드되 파일 위치를 String 변수에 저장해줌 = uploadFile
			   3) multipartFile 타입인 파일 배열 인자를 foreach문으로 하나하나 가공해 저장함
			   4) 중간과정들은  IE브라우저 특성의 \\빼주는 과정을 제외하고 필수는 아니지막 File 클래스의 saveFile은 서버에 저장할 파일을 만들어주는 역할이기에 필요함
			   5) 서버와 데이터 주고 받는 과정이기에 try catch문으로 감싸 예외처리 해주고 그 안에 transferTo의 인자로 저장파일을 주어 저장함
			   + 4번의 경우 코딩을 어떻게 하냐에 따라 달라질수 있다.*/
			try {
				File saveFile = new File(uploadPath ,uploadFileName);//서버에 저장할 파일을 파일이 올라갈 경로와 파일의 이름을 인자로 만들어냄
				multipartFile.transferTo(saveFile);//서버로 전송=저장
				
				attacDTO.setUuid(uuid.toString());//위에서 만든 첨부파일 저장 객체의 값을 하나하나 넣어줌=무슨용돈지 뒤에 나올듯
				attacDTO.setUploadPath(uploadFolderPath);
				
				if(checkImageType(saveFile)) {
					
					attacDTO.setImage(true);//데이터 베이스로 갈 내용 여기서 true를 넣어줌
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					//만약 saveFile이 이미지파일이면(확장자로 판단) 파일앞에 s_를 붙히는 출력스트림을 생성해줌
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
					//이제 섬네일 이미지를 생성하기 위해 해당되는 파일의 인풋 스트림과 출력스트림 그리고 너비와 높이를 설정하여 생성해줌 
					//그러니까 입력스트림으로 해당되는 파일 읽어서 위의 출력스트림으로 파일형태로 내보내 주는것임.ㅇㅋ?
					thumbnail.close(); //출력스트림 닫아야함.
				}
				list.add(attacDTO);
			} catch (Exception e){
				log.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);//업로드된 파일들을 attacDTO형태 리스트로 출력해줌.
	}
	
	@GetMapping("/display")
	  @ResponseBody
	  public ResponseEntity<byte[]> getFile(String fileName) {
		  
		  log.info("fileName: " +fileName);
		  
		  File file = new File("C:\\spring_file_upload\\" + fileName);
		  
		  log.info("file:" + file);
		  
		  ResponseEntity<byte[]> result = null;
		  
		  try {
			  HttpHeaders header = new HttpHeaders();
			  header.add("Content-Type",Files.probeContentType(file.toPath()));
			  result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		  } catch(IOException e) {
			  e.printStackTrace();
		  }
		  return result;
	  }
	  
	  @GetMapping(value = "/download" , produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    @ResponseBody
	  public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent,
			  String fileName){//헤더에 user-agent 정보를 ㅇ구하는 코드인듯.
		  log.info("download file: " + fileName);
		  
		  Resource resource = new FileSystemResource("C:\\spring_file_upload\\"+ fileName);
		  //파일의 경로를 저장하는건지 내용 자체를 저장하는건지 애매함.암튼 저장
		  if(resource.exists() == false) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }//리소스가 비어있으면 파일 찾을수 없다고 클라에 보냄
		  
		  String resourceName = resource.getFilename();
		  //파일으 이름을 가져오는데 전체이름인지 단일 파일 이름인지는 모르겠음 log로 확인 ㄱ
		  log.info("resource: " + resource);
          //클라에ㅐ서 찾는 파일 경로인듯.위에 저장한 전체경로가 리턴됨.		  
		  String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1); 
		  //파일이름[C:\spring_file_upload\2020\11\19\2b1527cb-8346-4c02-8c36-3ee56227f9dc_select asyink기존 내용.txt]
		  //위 처럼 있는상황에서 substring(파일이름.indexOf("_")는  이름중 _라는부분의 위치값..어...한 35..?정도의 값을 출력하고 substring은 그부분부터 출력인데 )
		  // 그부분부터 출력하면  35+1번쨰 문자부터 출력이됨 , 즉 " _파일진짜이름" 이 되기 떄문에 +1을 한것. 이걸로 파일 다운로드되는 부분에도 파일네임만이 들어감.
		  HttpHeaders headers = new HttpHeaders();
		  //헤더 생성
		  try {
			  
			  String downloadName = null;
			  if(userAgent.contains("Trident")) {
				  log.info("IE browser");
				  
				  downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
			  } else if(userAgent.contains("Edge")) {
				  log.info("Edge browser");
				  downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				  log.info("Edge name:" + downloadName);
			  } else {
				  log.info("Chrome browser");
				  downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			  }
			  
			  headers.add("Content-Disposition", "attachment; filename=" +downloadName);
			  //이부분이 저장되는 url인식? 느낌인데 이부분 다시꼭 봐야함. 플젝할떄 자세히 꼭 보기
		  } catch(UnsupportedEncodingException e) {
			  e.printStackTrace();
			  
		  }
		  return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	  }
	  
	  @PostMapping("/deleteFile")
	  @ResponseBody
	  public ResponseEntity<String> deleteFile(String fileName, String type){
		  log.info("deleteFile: " + fileName);
		  File file;
		  
		  try {
			  file = new File("C:\\spring_file_upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			  //해당위치의 파일을 utf-8로 한글도 인식 가능하게 디코딩 하는듯.
			  file.delete();
			  //파일을 삭제함
			  if(type.equals("image")) {
				  
				  String largeFileName = file.getAbsolutePath().replace("s_", "");
				  
				  log.info("largeFileName: " + largeFileName);
				  
				  file = new File(largeFileName);
				  
				  file.delete();
			  }//만약 삭제한 파일의 타입이 이미지 파일이면 섬네일 파일을 찾아서 ("s_"로 구별) 같이 삭제해줌.
		  } catch (UnsupportedEncodingException e) {
			  e.printStackTrace();
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  return new ResponseEntity<String>("deleted", HttpStatus.OK);
		  
	  }
}
