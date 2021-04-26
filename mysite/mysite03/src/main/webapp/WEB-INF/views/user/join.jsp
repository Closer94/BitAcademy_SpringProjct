<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.6.0.js"  ></script>
<!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script> -->

<script>
$(function(){ // 화면 load가 끝날 때 실행되는 이벤트 함수라고 생각하면 됨...?
	
	$("#email-check").change(function(){ // email 입력 변경 이벤트
		$("#img-check").hide();
		$("#btn-check").show();
	}); // email-check change
	
	$("#btn-check").click(function(){ // 클릭 이벤트 함수 등록 : 비동기
		let email = $("#email-check").val();
		if (email == '') {
			return; // 이메일 입력하지 않고 버튼을 누르면 통신x
		} 
		
		let url = "${pageContext.request.contextPath}/api/user/existemail?email="+email;
		
		$.ajax({
			url : url,
			asyc : true, // 비동기
			data: '',
			dataType: "json",
			success : function(response){
				// String을 자바스크리브 객체로 받음
				if (response.result !='success'){
					console.error(response.message);
					return;
				}

				if (response.data == true) {
					alert("이미 존재하는 이메일입니다. 다른 이메일은 사용해주세요.");
					$("#email-check").val('').focus();
					$("#join_btn").attr("disabled", true);
					return;
				} 
				
				alert("사용가능한 email입니다.");
				$("#password").focus();
				$("#img-check").show();
				$("#btn-check").hide();
				$("#join_btn").attr("disabled", false);
			},
		error : function(xhr, status, e){
				console.log(e);
		}
			
		}); // $("#btn-check").click
		
		/* 약관 체크, 이메일 입력 인증 완료 및 필수 정보 모두 다 썻는지 확인 
		$("#join_btn").click(function(){ 
		//	if ($("#name").val().trim() != '')*/  
	}); // $("#join_btn").click 
})

</script>
</head>
<body>
	<div id="container">
		<c:import url ="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="join" method="post" action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">＊ 이름 (필수)</label>
					<input id="name" name="name" type="text" value="" required="required">

					<label class="block-label" for="email">＊ 이메일 (필수)</label>
					<input id="email-check" name="email" type="text" value="" required="required">
					<img  id="img-check" src="${pageContext.request.contextPath }/assets/images/check.png"  style="width:16px; display:none"   >
					<input type="button" value="id 중복체크" id="btn-check">
					
					<label class="block-label">＊ 패스워드 (필수)</label>
					<input name="password" type="password" value="" id="password" required="required">
					
					<fieldset>
						<legend>＊ 성별 (필수)</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y" required="required">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기" id="join_btn" disabled="disabled"> 
					
				</form>
			</div>
		</div>
		<c:import url ="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>