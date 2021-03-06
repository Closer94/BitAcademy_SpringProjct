package com.saltlux.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.saltlux.mysite.service.UserService;
import com.saltlux.mysite.vo.UserVo;


// spring  컨테이너 밖에 존재하므로 직접 코딩
public class LoginInterceptor extends HandlerInterceptorAdapter {
	/* 스프링 컨테이너에 생성된 UserService를 사용*/
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		UserVo authUser = userService.getUser(userVo);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login?result=fail");
			return false;
		}
		
		/* session 처리 */
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		System.out.println("LoginInterceptor : "+authUser);
	
		if("user".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath());
		} else if("admin".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath()+"/admin");
		}
		return false;
	}

}
