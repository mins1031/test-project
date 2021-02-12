<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../includes/header.jsp" %>
    
  <div class="row">
        <div class="col-lg-12">
          <h1 class="page-header">Brand Register</h1>
        </div>
  </div>
  
  
 <style>
	.uploadResult {
		width: 100%;
		background-color: gray;
	}
	
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	
	.uploadResult ul li img {
		width: 100px;
	}
</style>

<style>
	.bigPictureWrapper {
	  position: absolute;
	  display: none;
	  justify-content: center;
	  align-items: center;
	  top:0%;
	  width:100%;
	  height:100%;
	  background-color: gray; 
	  z-index: 100;
	}
	
	.bigPicture {
	  position: relative;
	  display:flex;
	  justify-content: center;
	  align-items: center;
	}
</style>

  <div class="row">
    <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">Board Register</div>    
         <div class="panel-body">
         
           <form role="form" action="/brand/register" method="post">
             <div class="form-group">
               <label>Brand Name</label> <input class="form-control" name="name"> 
             </div>
             <div class="form-group">
               <label>Brand Style</label> <input class="form-control" name="style"> 
             </div>
             <div class="form-group">
               <label>Brand Content</label> <textarea class="form-control" 
                row="3" name="content"></textarea> 
             </div>
             <div class="form-group">
               <label>Brand Price</label> <input class="form-control" name="price"> 
             </div>
             
             <button type="submit" class="btn btn-default">Summit Button</button>
             <button type="reset" class="btn btn-default">Result Button</button>
           </form>
         </div> 
         <!-- end panel body -->
      </div>
      <!-- end panel -->
    </div>
    <!-- /.row -->
   <div class="row">
    <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">File Attach</div>    
         <div class="panel-body">
           <div class="form-group uploadDiv">
              <input type="file" name='uploadFile' multiple>
           </div>
           
           <div class='uploadResult'>
             <ul>
             
             </ul>
           </div>
         </div>
      </div>
    </div>
   </div>
    
  </div> 
  
 <script>
 
   $(document).ready(function(){
       
    	 var formObj = $("form[role='form']");
    	  
    	  $("button[type='submit']").on("click", function(e){
    	    
    	    e.preventDefault();
    	    
    	    console.log("submit clicked");
    	    
    	    var str = "";
    	    
    	    $(".uploadResult ul li").each(function(i, obj){
    	      
    	      var jobj = $(obj);
    	      
    	      console.dir(jobj);
    	      console.log("-------------------------");
    	      console.log(jobj.data("filename"));
    	      
    	      
    	      str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
    	      str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
    	      str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
    	      str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
    	      
    	    });
    	    
    	    console.log(str);
    	    
    	    formObj.append(str).submit();
    	    
    	  }); //.uploadResult ul li에 히든 인풋태그로 위의 값들을 넣어놓고 데이터베이스로 보내줌
	       
    	 var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    	  var maxSize = 5242880; //5MB
    	  
    	  function checkExtension(fileName, fileSize){
    	    
    	    if(fileSize >= maxSize){
    	      alert("파일 사이즈 초과");
    	      return false;
    	    }
    	    
    	    if(regex.test(fileName)){
    	      alert("해당 종류의 파일은 업로드할 수 없습니다.");
    	      return false;
    	    }
    	    return true;
    	  }//파일의 사이즈가 기준을 초과하는지, 파일의 확장자가 막아야할 확장자와 동일한지를 체크하는 함수
    	  
    	  $("input[type='file']").change(function(e){ //사용자가 파일을 인풋태그 안에 올리면 발생, 즉 파일을 선택했을때 동작하는듯. 파일 서버로 등록하는 함수
            /*FormData 인터페이스는 XMLHttpRequest.send()로 쉽게 보내질 수 있는 폼 field와 
			그 값들로 나타나는 key/value쌍들을 쉽게 만들 수 있는 방법을 제공한다. 
			만약에 인코딩 타입이 "multipart/form-data"로 설정이 되어 있으면 폼이 사용하는 것과 같은 포맷으로 사용한다.*/
			var formData = new FormData();//가상 form을 만듬
    	    
			var inputFile = $("input[name='uploadFile']");
    	    
			var files = inputFile[0].files;//위에서 만든 input태그내용대로 첫번째 파일을 적용시킴.
    	    
			for(var i = 0; i < files.length; i++){

			      if(!checkExtension(files[i].name, files[i].size) ){
			        return false;
			      }
			      formData.append("uploadFile", files[i]);
    	      //append: 선택된요소의 마지막에 새로운요소나 컨탠츠 추가 , formdata는 값을 키:벨류로 저장하기 때문에 자바스크립트에서 .으로 접근가능함 
    	      //위내용은 uploadFile이라는 키로 해당 파일을 폼데이터에 넣어 주는 형태임. 
    	    }
    	    
    	    $.ajax({
    	      url: '/uploadAjaxAction',//해당 url호출해 controller 동작
    	      processData: false, 
    	      contentType: false,
    	      data: formData, //위에서 폼형태로 작성완료된 formData를 서버에 보내는 데이터로 활용, 즉 서버에 파일 올리는 거임.
    	      type: 'POST',
    	      dataType:'json', //결과 json형태로 받음
    	        success: function(result){
    	          console.log(result); 
    			 // showUploadResult(result); //업로드 결과 처리 함수 

    	      }
    	    }); //$.ajax
    	  });//당장은 해당 파일 페이지에 올려놓기만 하면 서버에 저장됨.
    	  
    	  
    	  function showUploadResult(uploadResultArr){ //ajax로 등록된후 결과list를 받아와 uploadResult ul에 출력해줌
    		    
    		    if(!uploadResultArr || uploadResultArr.length == 0){ return; }
    		    //result값이 null인지 확인.
    		    var uploadUL = $(".uploadResult ul");
    		    //uploadResult div태그안의 ul태그를 가리키는 변수 선언후 초기화
    		    var str ="";
    		    
    		    $(uploadResultArr).each(function(i, obj){
    		    //제이쿼리를 통해 배열을 처리하거나 받고 싶은때 each를 사용함 each는 매개변수로 받은것을 사용해 for in문과같이
    		    //배열이나 객체의 요소를 검사할수 있는 메서드임 그래서 위 function의 i는 인덱스로 받아온 것의 길이만큼 적용되고
    		    //obj는 해당 인덱스의 객체나 배열임. 결국 밑의 내용은 반복문의 안에 배열이나 리스트의 내용을 가공하는 내용이라고 봐도 무방함.
    		    //==> 아마 
    		        //image type
    		        if(obj.image){
    		          var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
    		          //ajax통신하면 json형태로 받아오는데 객체로 적용이 되나봄 이렇게 .을 통해 접근할수 있는것 보니.
    		          //아무튼 파일의 저장경로와 uuid값과 이름을 fileCallPath라는 변수에 인코딩해서 저장함.
    		          str += "<li data-path='"+obj.uploadPath+"'";
			          str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
			          str +" ><div>";
                      str += "<span> "+ obj.fileName+"</span>"; //해당 파일의 파일이름 을 넣음
                      str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
    		          str += "<img src='/display?fileName="+fileCallPath+"'>";
    		          str += "</div>";
    		          str +"</li>";//str이라는 변수에 위의 문자열을 다담아서 출력되야하는 태그에 출력해주는 방식임
    		        }else{
    		          var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);            
    		            var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
    		              
    		          str += "<li data-path='"+obj.uploadPath+"'";
    			      str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
    				  str +" ><div>";
    		          str += "<span> "+ obj.fileName+"</span>";
    		          str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
    		          str += "<img src='/resources/img/attach.png'></a>";
    		          str += "</div>";
    		          str +"</li>";
    		        } 
    		    });//단순히 해당 파일이 이미지파일인지 아닌 지 구분하여 처리하는 if문임.
    		    
    		    uploadUL.append(str);
    		  }
    	  
    	  $(".uploadResult").on("click","button",function(e){ //x버튼 클릭하면 삭제기능
    		  console.log("delete file");
    		  
    		  var targetFile = $(this).data("file");
    		  var type = $(this).data("type");
    		  
    		  var targetLi = $(this).closet("li");
    		  
    		  $.ajax({
    			  url:'/deleteFile',
    			  data: {fileName: targetFile, type:type},
    			  dataType:'text',
    			  type:'POST',
    			  success: function(result){
    				  alert(result);
    				  targetLi.remove();
    			  }
    		  });
    	  });
    	  
    	  
     });
 </script>
     
<%@ include file="../includes/footer.jsp" %>    