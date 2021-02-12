<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../includes/header.jsp" %>

<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Read</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
  
 <div class="row">
   <div class="col-lg-12">
     <div class="panel panel-default">
       
       <div class="panel-heading"> Brand Read Page</div>
       <div class="panel-body">
         
         <div class="form-group">
          <label>Bno</label> <input class="form-control" name='bno'
           value='<c:out value="${brand.bno}"/>' readonly="readonly">
         </div>
         
         <div class="form-group">
          <label>Brand Name</label> <input class="form-control" name='name'
           value='<c:out value="${brand.name}"/>' readonly="readonly">
         </div>
         
         <div class="form-group">
          <label>Brand Style</label> <input class="form-control" name='style'
           value='<c:out value="${brand.style}"/>' readonly="readonly">
         </div>
         
         <div class="form-group">
          <label>Brand Content</label> <textarea class="form-control" rows="3" name='content'
           readonly="readonly"><c:out value="${brand.content}"/></textarea>
         </div>
         
         <div class="form-group">
          <label>Brand Price</label> <input class="form-control" name='price'
           value='<c:out value="${brand.price}"/>' readonly="readonly">
         </div>
         
         <button data-oper='modify' class="btn btn-default">Modify</button>
         <button data-oper='list' class="btn btn-info">List</button>
         
         <form id="operForm" action="/brand/modify" method="get">
            <input type='hidden' id='bno' name='bno' value='<c:out value="${brand.bno}"/> '>
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/> '>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/> '>
            <input type='hidden' name='type' value='<c:out value="${cri.type}"/> '>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/> '>
         </form>
       </div>
     </div>
   </div>
 </div>   
  
  <div class='bigPictureWrapper'>
  <div class='bigPicture'>
  </div>
</div>



<style>
.uploadResult {
  width:100%;
  background-color: gray;
}
.uploadResult ul{
  display:flex;
  flex-flow: row;
  justify-content: center;
  align-items: center;
}
.uploadResult ul li {
  list-style: none;
  padding: 10px;
  align-content: center;
  text-align: center;
}
.uploadResult ul li img{
  width: 100px;
}
.uploadResult ul li span {
  color:white;
}
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
  background:rgba(255,255,255,0.5);
}
.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}

.bigPicture img {
  width:600px;
}

</style>



<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Files</div>
      <!-- /.panel-heading -->
      <div class="panel-body">
        
        <div class='uploadResult'> 
          <ul>
          </ul>
        </div>
      </div>
      <!--  end panel-body -->
    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->
  
  
   <div class="row">
     <div class="col-lg-12">
      <div class="panel panel-default">
       
       <div class="panel-heading">
        <i class="fa fa-comments fa-fw"></i> Reply
         <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
         </div>
         
          <div class="panel-body">        
      
        <ul class="chat">

        </ul>
        <!-- ./ end ul -->
      </div>
      <!-- /.panel .chat-panel -->

	<div class="panel-footer"></div>
          </div>
         </div>
        </div>
        
  <!-- Reply Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
            aria-labelledby="myModalLabel" aria-hidden="true">
     <div class="modal-dialog">
      <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-labelledby="myModalLabel" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4> 
       </div>
      <div class="modal-body">
       <div class="form-group">
         <label>Reply</label>
         <input class="form-control" name='reply' value='New Reply!!'>
       </div>
       <div class="form-group">
         <label>Replyer</label>
         <input class="form-control" name='replyer' value='replyer'>
       </div>
       <div class="form-group">
         <label>Reply Date</label>
         <input class="form-control" name='replyDate' value=''>
       </div>
       
      </div>
      <div class="modal-footer">
        <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
        <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
        <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
        <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
      </div>   
      </div>
     </div>
    </div>        
    
  <script type="text/javascript" src="/resources/js/reply.js"></script>   
  <script>
  $(document).ready(function() {
  
  var operForm = $("#operForm");  // id가 operForm인 태그의 내용을 operForm변수에 저장
  
  $("button[data-oper='modify']").on("click", function(e){//data-oper타입이 modify인 버튼이 클릭되면 밑의 함수 실행
    
    operForm.attr("action","/brand/modify").submit(); // action brand/modify 주소로 페이지 이동하게 submit됨
    
  });
  
  $("button[data-oper='list']").on("click", function(e){
    
    operForm.find("#bno").remove();//위의 bno의 id값으로 제어해줌 pageNum도 id값넣고 이 명령해주면 pageNum URL안넘어감
    operForm.attr("action","/brand/list")
    operForm.submit();
    
  });  
});
</script>   

 <script>   
   
 $(document).ready(function(){
   var bnoValue = '<c:out value="${brand.bno}" /> '; // 해당 게시물의 bno 값을 bnoValue에 저장
   var replyUL = $(".chat"); //class 가 chat인 태그를 저장 해당chat은 댓글의 내용이 기재되는 pannel-body임,
   
     showList(1); //디폴트 실행이기 떄문에 showList함수에 인자1을 넣어 실행
     
     function showList(page) {
    	 
    	 replyService.getList({bno:bnoValue,page: page||1}, function(replyCnt,list){
    		 //reply.js의 replyService.getList를 실행해 콜백되는 함수의 인자(서버로 부터의 data.정확한 내용은 책400p언저리 참고)를 받아 함수 내용을 실행함
    		 
    		 console.log("replyCno :" + replyCnt);
    		 console.log("list :" + list);
    		 console.log(list);
    		 
    		 if(page == -1){
    			 pageNum = Math.ceil(replyCnt/10.0);
    			 showList(pageNum);
    			 return;
    		 }
    		 
    		 var str="";
    		 if(list == null || list.length == 0){
    			 return;
    	      // 만약 넘어온 인자(DATA) 값이 0이거나 길이가 0이면 chat태그 내의 내용을 ""로 공백처리하고 호출부분으로 돌아감.
    		 }
    		 for (var i = 0, len = list.length || 0; i < len; i++) {
    		       str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
    		       str +="  <div><div class='header'><strong class='primary-font'>["
    		    	   +list[i].rno+"] "+list[i].replyer+"</strong>"; 
    		       str +=" <small class='pull-right text-muted'>"
    		           +replyService.displayTime(list[i].replyDate)+"</small></div>";
    		       str +=" <p>"+list[i].reply+"</p></div></li>";
    		     }
    		 /*넘어온 인자(data)값의 길이 만큼 근데 왜 배열 형태임 list아니였나 list는 특정값 접근할땐 get메서드 써줘야 되는거 아닝,,?배열인듯
    	              암튼  배열이라고 가정하고 .. str변수안에 모든 댓글의 정보를 저장하고 출력하는듯하다....*/
    		 replyUL.html(str);
    		 
    		 showReplyPage(replyCnt)
    	 });
     }
     
     var pageNum = 1;
     var replyPageFooter = $(".panel-footer");
     
     function showReplyPage(replyCnt){
       
       var endNum = Math.ceil(pageNum / 10.0) * 10;
       //if pageNum=2 -> Math.ceil(2 / 10.0) == 0.2-> 1.0 * 10 -> 10이기에 끝넘버 10이고
       //if pageNum=12 -> Math.ceil(12 / 10.0) == 1.2-> 2.0 * 10 -> 20이기에 끝넘버 20임
       var startNum = endNum - 9; 
       
       var prev = startNum != 1;//true가 되려면  페이지가 11개 이상이여야 한다는 식.
       var next = false; //디폴트값 밑의 if에서 조정
    	 
       if(endNum * 10 >= replyCnt){
           endNum = Math.ceil(replyCnt/10.0);
         }//만약 위에서 나온 endNum값에 10을 곱한값이 댓글 총개수보다 크다면 댓글이 next를 띄울 필요가없기고 페이징을 위해 총갯수를 끝넘버구하는 식적용후 저장함
                 
         if(endNum * 10 < replyCnt){
           next = true;
         }
         //만약 댓글 총 개수가 페이지 *10 보다 많다면(댓글 페이지가 10개 이상 필요하면 ) next버튼 활성화!
         var str = "<ul class='pagination pull-right'>";
         
         if(prev){
           str+= "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
         }
    	
         for(var i = startNum ; i <= endNum; i++){
             
             var active = pageNum == i? "active":"";
             
             str+= "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
           }
           
           if(next){
             str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
           }
    	
    	str += "</ul></div>";
        
        console.log(str);
        
        replyPageFooter.html(str);
     }
     
     replyPageFooter.on("click","li a", function(e){ //replyPageFooter를 클릭했을때 li와 a 태그안에 function의 내용을 이벤트처리한다
         e.preventDefault();
         console.log("page click");
         
         var targetPageNum = $(this).attr("href");
         //이벤트가 일어난 개체의 href의 값을  targetPageNum에 저장
         console.log("targetPageNum: " + targetPageNum);
         
         pageNum = targetPageNum;
         //결국 2값을 누르면 targetPageNum에 2가 저장되고 pageNum에 2가 저장되 showList로 값을 넣어서 실행하면 2번쨰로 넘어감,.
         //pageNum설정하는 게 여기 있음 결국 1은 디폴트로 두는 것.
         showList(pageNum);
       });     
     
     
  
     var modal=$(".modal");
     var modalInputReply = modal.find("input[name='reply']");
     var modalInputReplyer = modal.find("input[name='replyer']");
     var modalInputReplyDate = modal.find("input[name='replyDate']");
     
     var modalModBtn = $("#modalModBtn");
     var modalRemoveBtn = $("#modalRemoveBtn");
     var modalRegisterBtn = $("#modalRegisterBtn");
     //각 태그들 input,button들의 id값을 변수들에 저장함. 왜인지 알아야 하는데 일단 진행하도록
     
     $("#addReplyBtn").on("click", function(e){
    	 modal.find("input").val("");
    	 modalInputReplyDate.closest("div").hide();
    	 modal.find("button[id != 'modalClosetBtn']").hide();
    	 
    	 modalRegisterBtn.show();
    	 
    	 $(".modal").modal("show");
     });
     
     modalRegisterBtn.on("click",function(e){
    	
    	 var reply= {
    		reply : modalInputReply.val(),
    		replyer : modalInputReplyer.val(),
    		bno : bnoValue
    	 };
    	 replyService.add(reply, function(result){
    		 
    		 alert(result);
    		 
    		 modal.find("input").val("");
    		 modal.modal("hide");
    		 
    		 showList(-1);//댓글을 등록한 후에 전에 보고 있던 댓글 페이지로 이동하기 위해서 showList와 이곳에 정의.
    	 });
     });
     
     $(".chat").on("click","li",function(){
    	
    	 var rno = $(this).data("rno");
    	 
    	 replyService.get(rno, function(reply){
    		 
    		 modalInputReply.val(reply.reply); // 클릭된 댓글의 rno가 controller의 get에 전해지면 해당댓글의 내용을 가져옴.이부분은 댓글내용을 뿌려줌
    		 modalInputReplyer.val(reply.replyer); // 이부분은 작성자를 해당 inupt태그에 뿌려줌
    		 modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
    		 //마지막 작성(수정)시간을 읽기모드로 뿌려줌
    		 modal.data("rno",reply.rno);
    		 
    		 modal.find("button[id != 'modalCloseBtn']").hide();
    		 modalModBtn.show();
    		 modalRemoveBtn.show();
    		 
    		 $(".modal").modal("show");
    	 });
     });
     
      modalModBtn.on("click", function(e){
    	  
    	  var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
    	  
    	  replyService.update(reply, function(result){
    		  
    		  alert(result);
    		  modal.modal("hide");
    		  showList(pageNum);
    	  });
      });
      
		 modalRemoveBtn.on("click", function(e){
		    	  
		    	  var rno = {rno:modal.data("rno")};
		    	  
		    	  replyService.remove(rno, function(result){
		    		  
		    		  alert(result);
		    		  modal.modal("hide");
		    		  showList(pageNum);
		    	  });
		      });
      
 });
 </script>   
 
<script>
$(document).ready(function(){
	  
	  (function(){
	  
	    var bno = '<c:out value="${brand.bno}"/>';
	    
	    $.getJSON("/brand/getAttachList", {bno: bno}, function(arr){
	        
	       console.log(arr);
	       
	       var str = "";
	       
	       $(arr).each(function(i, attach){//데이터 베이스로 부터 해당 게시물의 등록된 파일을 가져와줌
	       
	         //디비에서 이미지면 1이고 아니면 0인데 이게 어떻게 되는지....
	         if(attach.fileType){
	           var fileCallPath =  encodeURIComponent( attach.uploadPath+ "/s_"+attach.uuid +"_"+attach.fileName);
	           
	           str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
	           str += "<img src='/display?fileName="+fileCallPath+"'>";
	           str += "</div>";
	           str +"</li>";
	         }else{
	             
	           str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
	           str += "<span> "+ attach.fileName+"</span><br/>";
	           str += "<img src='/resources/img/attach.png'></a>";
	           str += "</div>";
	           str +"</li>";
	         }
	       });
	       
	       $(".uploadResult ul").html(str);
	       
	       
	     });//end getjson

	    
	  })();//end function
	  
	  $(".uploadResult").on("click","li", function(e){ //업로드되있는 파일 클리하면 발생하는 이벤트 == uploadResult의 li태그가 클릭되면 발생.
	      
		    console.log("view image");
		    
		    var liObj = $(this);
		    //클릭한 대상
		    var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
		    
		    if(liObj.data("type")){
		      showImage(path.replace(new RegExp(/\\/g),"/"));
		    }else {
		      //download 
		      self.location ="/download?fileName="+path
		    }
	  });
	  
	  function showImage(fileCallPath){
		    
		    alert(fileCallPath);
		    
		    $(".bigPictureWrapper").css("display","flex").show();
		    
		    $(".bigPicture")
		    .html("<img src='/display?fileName="+fileCallPath+"' >")
		    .animate({width:'100%', height: '100%'}, 1000);
		    
		  }

		  $(".bigPictureWrapper").on("click", function(e){
		    $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
		    setTimeout(function(){
		      $('.bigPictureWrapper').hide();
		    }, 1000);
		  });

		  $(".bigPictureWrapper").on("click", function(e){
			    $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
			    setTimeout(function(){
			      $('.bigPictureWrapper').hide();
			    }, 1000);
			  });
	  
  });
</script>
 
<%@ include file="../includes/footer.jsp" %>    