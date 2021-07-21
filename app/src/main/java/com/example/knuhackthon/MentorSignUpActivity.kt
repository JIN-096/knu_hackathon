package com.example.knuhackthon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.knuhackthon.databinding.ActivityMentorSignUpBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MentorSignUpActivity : AppCompatActivity() {

    val binding by lazy { ActivityMentorSignUpBinding.inflate(layoutInflater) }
    var auth : FirebaseAuth? = null
    var email : String? = null
    var pw : String? = null
    var code : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        code = createEmailCode()
        binding.btnEmailVerify.setOnClickListener {
            email = binding.emailText.text.toString()!! + "@knu.ac.kr"
            CoroutineScope(Dispatchers.IO).launch { sendEmail("인증 메일입니다",code!!,email!!) }
            Toast.makeText(this,"인증 메일 발송 완료",Toast.LENGTH_SHORT).show()
        }
        binding.btnSignup.setOnClickListener { signUp() }
        binding.btnEmailConfirm.setOnClickListener {
            var inputCode = binding.emailverifyText.text.toString()
            if(inputCode.equals(code)){
                Toast.makeText(this,"인증 성공.",Toast.LENGTH_SHORT).show()
                binding.emailverifyText.isClickable = false
                binding.emailverifyText.isEnabled = false
                binding.emailverifyText.isFocusable = false
            }
            else{
                Toast.makeText(this,"인증번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                binding.emailverifyText.setText("")
            }
        }
    }

    fun signUp(){
        auth?.createUserWithEmailAndPassword(binding.emailText.text.toString()+"@knu.ac.kr", binding.pwText.text.toString())
            ?.addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"회원 가입 성공",Toast.LENGTH_SHORT).show()
                    auth?.signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"회원 가입 실패, 재시도 해주세요",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
    }

    suspend fun sendEmail(
        title: String,      // 메일 제목
        body: String,       // 메일 내용
        dest: String,       // 받는 메일 주소
    )
    {
        // 보내는 메일 주소와 비밀번호
        val username = "";
        val password = "";

        val props = Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 비밀번호 인증으로 세션 생성
        val session = Session.getInstance(props,
            object: javax.mail.Authenticator() {
                override  fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password);
                }
            })

        // 메시지 객체 만들기
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(username))
        // 수신자 설정, 여러명으로도 가능
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(dest))
        message.subject = title
        message.setText(body)

        // 전송
        Transport.send(message)
    }

     fun createEmailCode(): String? { //이메일 인증코드 생성
        val str = arrayOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9")
        var newCode = String()
        for (x in 0..7) {
            val random = (Math.random() * str.size).toInt()
            newCode += str[random]
        }
        return newCode
    }
}