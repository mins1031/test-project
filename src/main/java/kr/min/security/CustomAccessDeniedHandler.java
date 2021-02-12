package kr.min.security;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler { //implements AccessDeniedHandler
     
	public void handle(HttpServletRequest request,
			HttpServletResponse response, AccessDeniedException accessException)
	  throws IOException, ServletException {
		
		log.error("Access Denied Handler");
		
		log.error("Redirect.......");
		
		response.sendRedirect("/accessError");
	}
}
