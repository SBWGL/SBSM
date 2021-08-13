//아이디 중복검사
$('#loginId').on("propertychange change keyup paste input", function(){
	var memberId = $('#loginId').val();	// #loginid 에 입력되는 값
	var data = {memberId : memberId}// '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'

	$.ajax({
		type : "post",
		url : "/memberIdCheck", /** DP_MemberController 클래스의 memberIdChk와 연결 **/
		data : data,
        success : function(result){
            if(result != 'fail'){
                $('.possibleId').css("display","inline-block");
                $('.existId').css("display", "none");
            } else {
                $('.existId').css("display","inline-block");
                $('.possibleId').css("display", "none");
            }
        }// success 종료
	}); // ajax 종료
});

//인증번호 이메일 전송
var code = ""; //이메일전송 인증번호 저장위한 코드
$(".mailCheckButton").click(function(){
    var email = $("#email").val(); //입력한 이메일
    var checkInput = $(".mailCheckInput"); // 인증번호 입력란
    var checkBox = $(".mailCheckInputBox"); // 인증번호 입력란 박스
    $.ajax({
        type:"GET",
        url:"mailCheck?email=" + email,
        success:function(data){
            checkInput.attr("disabled",false);
            // attr 은 속성 값을 가져옴 즉 checkBox에 들어있는 .mailCheckInput의 disabled 속성을 가져오고 False 변환
            checkBox.attr("id", "mailCheckInputBoxTrue");
            code = data;
        }
    });

});

//인증번호 확인
$(".mailCheckInput").blur(function(){
    var inputCode = $(".mailCheckInput").val(); // 입력코드
    var checkResult = $("#codeWar"); // 비교 결과

    if(inputCode == code){ // 일치할 경우
        console.log("성공")
        checkResult.html("인증번호가 일치합니다.");
        checkResult.attr("class", "correct");
    } else { // 일치하지 않을 경우
        console.log("실패")
        checkResult.html("인증번호를 다시 확인해주세요.");
        checkResult.attr("class", "incorrect");
    }
});



