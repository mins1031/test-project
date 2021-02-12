<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../includes/header.jsp" %>

     <div class="row">
        <div class="col-lg-12">
          <h1 class="page-header">Tables</h1>
        </div>
     </div>
     
     <div class="row">
        <div class="col-lg-12">
          <div class="panel panel-default">
            <div class="panel-heading">Board List Page
             <button id='regBtn' type="button" class="btn btn-xs pull-right">Register New Board</button>
            </div>
            
            <div class="panel-body">
              <table class="table table-striped table-boarded table-hover">
                <thead>
                 <tr>  
                  <th>#번호</th>
                  <th>브랜드명</th>
                  <th>브랜드스타일</th>
                  <th>브랜드평</th>
                  <th>브랜드가격대</th>
                  <th>작성일</th>
                  <th>수정일</th>
                 </tr>
                </thead>  
                
              <c:forEach items="${Blist}" var="brand">
                <tr>
                 <td><c:out value="${brand.bno}"/></td>
                  <td><a class='move' href='<c:out value="${brand.bno}"/>'>
                  <c:out value="${brand.name}" />   <b>[<c:out 
 value="${brand.replyCnt}"/>]</b> </a></td>
                  <td><c:out value="${brand.style}" /></td>
                  <td><c:out value="${brand.content}" /></td>
                  <td><c:out value="${brand.price}" /></td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.regdate}" /></td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.updateDate}" /></td>
                </tr>
              </c:forEach>    
              </table>            
              
              <div class='row'>
               <div class="col-lg-12">
                
                <form id='searchForm' action="/brand/list" method="get">
                  <select name='type'>
                    <option value=""
                    <c:out value="${pageMaker.cri.type == null? 'selected': ''}"/>>--</option>
                      <option value="N"
                      <c:out value="${pageMaker.cri.type eq 'N'? 'selected': ''}"/>>브랜드명</option>
                      <option value="S"
                      <c:out value="${pageMaker.cri.type eq 'S'? 'selected': ''}"/>>브랜드스타일</option>
                      <option value="C"
                      <c:out value="${pageMaker.cri.type eq 'C'? 'selected': ''}"/>>브랜드내용</option>
                      <option value="P"
                      <c:out value="${pageMaker.cri.type eq 'P'? 'selected': ''}"/>>브랜드가격</option>
                      <option value="NS"
                      <c:out value="${pageMaker.cri.type eq 'NS'? 'selected': ''}"/>>브랜드명or브랜드스타일</option>
                      <option value="NC"
                      <c:out value="${pageMaker.cri.type eq 'NC'? 'selected': ''}"/>>브랜드명or브랜드내용</option>
                      <option value="NP"
                      <c:out value="${pageMaker.cri.type eq 'NP'? 'selected': ''}"/>>브랜드명or브랜드가격</option>
                      <option value="SC"
                      <c:out value="${pageMaker.cri.type eq 'SC'? 'selected': ''}"/>>브랜드스타일or브랜드내용</option>
                      <option value="SP"
                      <c:out value="${pageMaker.cri.type eq 'SP'? 'selected': ''}"/>>브랜드스타일or브랜드가격</option>
                      <option value="CP"
                      <c:out value="${pageMaker.cri.type eq 'CP'? 'selected': ''}"/>>브랜드내용or브랜드가격</option>
                      <option value="NSC"
                      <c:out value="${pageMaker.cri.type eq 'NSC'? 'selected': ''}"/>>브랜드명or브랜드스타일or브랜드내용</option>
                      <option value="NSP"
                      <c:out value="${pageMaker.cri.type eq 'NSP'? 'selected': ''}"/>>브랜드명or브랜드스타일or브랜드가격</option>
                      <option value="NCP"
                      <c:out value="${pageMaker.cri.type eq 'NCP'? 'selected': ''}"/>>브랜드명or브랜드내 or브랜드가격</option>
                      <option value="SCP"
                      <c:out value="${pageMaker.cri.type eq 'SCP'? 'selected': ''}"/>>브랜드명or브랜드내용or브랜드가격</option>
                      <option value="NSCP"
                      <c:out value="${pageMaker.cri.type eq 'NSCP'? 'selected': ''}"/>>브랜드명or브랜드스타일or브랜드내용or브랜드가격</option>
                  </select>
                  <input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/> '/>
                  <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/> '/>
                  <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/> '/>
                  <button class='btn btn-default'>Search</button>
                </form>
               </div>
              </div>
              
              <div class='pull-right'>
                <ul class="pagination">
                 <c:if test ="${pageMaker.prev}">
                   <li class="paginate_button previous">
                     <a href="${pageMaker.startPage -1}">Previous</a>
                   </li> 
                 </c:if>
                 
                 <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                  <li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""} ">
                   <a href="${num}">${num}</a>
                  </li>
                 </c:forEach>
                 
                  <c:if test ="${pageMaker.next}">
                   <li class="paginate_button next">
                     <a href="${pageMaker.endPage +1}">Next</a>
                   </li> 
                 </c:if>   
                 
               <form id='actionForm' action="/brand/list" method="get">
                 <input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
                 <input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
                 <input type='hidden' name='type' value='${pageMaker.cri.type}'>
                 <input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
               </form> <!-- 여기의 form은 list로 돌아올때 가지고 왔다갔다하는 일종의 보존데이터? 같은 느낌으로 페이지의 정보를 가지고 있다가 
                                           페이지 이동할 떄 javascript 구문을 통해 전달해줌.-->
              
               <!--Modat 추가 -->
              <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                 <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                  </div>
                  <div class="modal-body">처리가 완료되었습니다.</div>
                  <div class="modal-footer">
                       <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                       <button type="button" class="btn btn-primary">Save Changes</button>
                  </div>
                 </div>       
                </div> 
              </div>
            </div>
          </div>    
         </div>
     </div> 
     
  <script type="text/javascript">
	$(document).ready(function() {

		var result = '<c:out value="${result}"/>'; //등록시 result라는 데이터로 bno값 보내줌

 	    checkModal(result); //checkModal 메서드 실행

		history.replaceState({}, null, null);
        //history객체는 스택구조로 동작하기때문에 페이지 기록이 쌓여있음.
 		function checkModal(result) {
	
			if (result === '' || history.state) {
				return;
			}//result값이 비었거나 
	
			if (parseInt(result) > 0) {
			  $(".modal-body").html("게시글 " + parseInt(result)+ " 번이 등록되었습니다.");
			} // css class 가 modal-body인 태그의 html의 요소를  괄호안의 내용으로 바꾸어줌/
	
				$("#myModal").modal("show");//id가 myModal인 태그를 보여줌.
		}

			$("#regBtn").on("click", function() {

				self.location = "/brand/register"; //id가 regBtn인 태그(버튼)을 클릭하면 해당 url로 가는 명령

			});

			var actionForm = $("#actionForm"); 

				$(".paginate_button a").on("click",function(e) {

						e.preventDefault();

						console.log('click');

						actionForm.find("input[name='pageNum']").val($(this).attr("href"));
						//find는 actionForm 하위의  name이 pageNum인 input태그를 찾는것임.
						//val은 해당 양식(form)의 내용(값)을 가져오거나 저장하는 메서드
						//attr은 요소의 속성값을 가져오거나 속성을 추가합니다
						//결국 페이지 번호를 누르면 그 번호가 attr로 href값이 val(href)이렇게 되고 이 값이 name이 pageNum인 input의 값으로 됨
						actionForm.submit();
						//마지막으로 submit하면 서버에서 폼값에 맞게 페이지를 다시 보내줌.
						});

					     $(".move").on("click",function(e) {

						 e.preventDefault();
						 actionForm.append("<input type='hidden' name='bno' value='"+
								 $(this).attr("href")+ "'>");
						actionForm.attr("action","/brand/get");
						actionForm.submit();
                        });//get할때 pageNum,amount같이 보내주는 스크립트 코드
                             
					     var searchForm = $("#searchForm");

						$("#searchForm button").on(
								"click",
								function(e) {

									if (!searchForm.find("option:selected")
											.val()) {
										alert("검색종류를 선택하세요");
										return false;// 조건에 아무것도 선택안되고검색버튼 눌렸을때
									}

									if (!searchForm.find(
											"input[name='keyword']").val()) {
										alert("키워드를 입력하세요");
										return false;//키워드에 무언가 없이 검색버튼이 눌렸을떄.
									}

									searchForm.find("input[name='pageNum']")
											.val("1");
									e.preventDefault();//브라우저에서 검색버튼을 클릭하면 페이지는 무조건 1이고 폼태그전송은 막아줌

									searchForm.submit();

								});

		 });
</script>

<%@ include file="../includes/footer.jsp" %>
