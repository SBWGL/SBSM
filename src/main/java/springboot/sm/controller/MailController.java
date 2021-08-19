package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    //이메일 인증
    /** ajax로 요청했기 때문에 view로 온전히 데이터를 전송하기 위해선 @ResponsBody 어토네이션이 필요함 **/
    @RequestMapping(value="/mailCheck", method= RequestMethod.GET)
    @ResponseBody
    public String mailCheckGET(String email) throws Exception{

        /** 인증번호(난수) 생성 **/
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        log.info(""+checkNum);

        /** 이메일 보내기 **/
        String setFrom = "kongrpt@gmail.com"; //보내는 이메일(yml에서 넣은 이메일과 동일)
        String toMail = email; //수신받을 이메일 (signUpForm에서 입력하는 이메일)
        String title = "회원가입 인증 이메일 입니다."; //보낼 메일의 제목
        String content =
                "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br>" +
                "인증 번호는 " + checkNum + "입니다." +
                "<br>" +
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        //보낼 이메일의 내용

        /** 이메일 전송을 위한 코드 **/
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
        }

        String num = Integer.toString(checkNum);
        return num;
    }

}
