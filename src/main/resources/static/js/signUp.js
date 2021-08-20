var idCheck = false;            // 아이디
var idckCheck = false;            // 아이디 중복 검사
var pwCheck = false;            // 비번
var pwckCheck = false;            // 비번 확인
var pwckcorCheck = false;        // 비번 확인 일치 확인
var nameCheck = false;            // 이름
var mailCheck = false;            // 이메일
var mailnumCheck = false;        // 이메일 인증번호 확인
var addressCheck = false         // 주소

$(".join_button").attr("type", "button");

$(document).ready(function(){

    $(".join_button").click(function(){
        /* 입력값 변수 */
        var id = $('#loginId').val();                 // id 입력란
        var pw = $('#password').val();                // 비밀번호 입력란
        var name = $('#name').val();            // 이름 입력란
        var mail = $('#email').val();            // 이메일 입력란
        var addr = $('#postcode').val();        // 주소 입력란

        /* 아이디 유효성검사 */
        if(id == ""){
            $('.final_id_ck').css('display','block');
            idCheck = false;
        }else{
            $('.final_id_ck').css('display', 'none');
            idCheck = true;
        }

        /* 비밀번호 유효성 검사 */
        if(pw == ""){
            $('.final_pw_ck').css('display','block');
            pwCheck = false;
        }else{
            $('.final_pw_ck').css('display', 'none');
            pwCheck = true;
        }

        /* 이름 유효성 검사 */
        if(name == ""){
            $('.final_name_ck').css('display','block');
            nameCheck = false;
        }else{
            $('.final_name_ck').css('display', 'none');
            nameCheck = true;
        }

        /* 이메일 유효성 검사 */
        if(mail == ""){
            $('.final_mail_ck').css('display','block');
            mailCheck = false;
        }else{
            $('.final_mail_ck').css('display', 'none');
            mailCheck = true;
        }

        /* 주소 유효성 검사 */
        if(addr == ""){
            $('.final_addr_ck').css('display','block');
            addressCheck = false;
        }else{
            $('.final_addr_ck').css('display', 'none');
            addressCheck = true;
        }

        if(idCheck&&idckCheck&&pwCheck&&nameCheck&&mailCheck&&addressCheck ){
            $(".join_form").attr("action", "/signUp");
            $(".join_form").submit();
        }
        return false;
    });
})

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
                idckCheck = true;
            } else {
                $('.existId').css("display","inline-block");
                $('.possibleId').css("display", "none");
                idckCheck = false;
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
    var warnMsg = $(".mail_input_box_warn");    // 이메일 입력 경고글
    if(mailFormCheck(email)){
        warnMsg.html("이메일이 전송 되었습니다. 이메일을 확인해주세요.");
        warnMsg.css("color", "green");
    } else {
        warnMsg.html("올바르지 못한 이메일 형식입니다.");
        warnMsg.css("color", "red");
        return false;
    }
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

function mailFormCheck(email){
    var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return form.test(email);
}


