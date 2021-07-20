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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnEmailVerify.setOnClickListener {
            email = binding.emailText.text.toString()!!
            var code = createEmailCode()
            CoroutineScope(Dispatchers.IO).launch { sendEmail("인증 메일입니다",code!!,email!!) }
        }
        binding.btnSignup.setOnClickListener { signUp() }
    }

    fun signUp(){
        auth?.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.pwText.text.toString())
            ?.addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"계정 생성 성공. 검증용 이메일 전송",Toast.LENGTH_LONG).show()
                    sendVerificationEmail()
                }
                else{

                }
            }
    }

    fun sendVerificationEmail(){
        var user = auth?.currentUser!!

        val url = "https://knuhackthon.page.link/check"
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl(url)
            .setIOSBundleId("com.example.ios")
            .setAndroidPackageName("com.example.knuhackthon",false,null)
            .build()

        user?.sendEmailVerification()?.addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"인증메일이 전송되었습니다. 메일을 확인해 주세요",Toast.LENGTH_LONG).show()
                auth?.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("email",binding.emailText.text.toString())
                intent.putExtra("pw", binding.pwText.text.toString())
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"검증 메일 전송 실패했습니다.",Toast.LENGTH_LONG).show()
            }
        }
        Toast.makeText(this,"계정 생성 성공. 검증용 이메일 전송",Toast.LENGTH_LONG).show()
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


        /*// 파일을 담기 위한 Multipart 생성
        val multipart = MimeMultipart()
        val messageBodyPart = MimeBodyPart()
        val source = FileDataSource(filePath)

        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = fileName
        multipart.addBodyPart(messageBodyPart)

        // 메시지에 파일 담고
        message.setContent(multipart)*/

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