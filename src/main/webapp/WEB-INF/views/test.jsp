<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
     <table>
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
               <tr>
                 <td><c:out value="${brand.bno}"/></td>
                  <td><c:out value="${brand.name}" /></a></td>
                  <td><c:out value="${brand.style}" /></td>
                  <td><c:out value="${brand.content}" /></td>
                  <td><c:out value="${brand.price}" /></td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.regdate}" /></td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.updateDate}" /></td>
                </tr>
      </table>  
</body>
</html>