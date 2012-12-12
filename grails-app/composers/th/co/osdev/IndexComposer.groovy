package th.co.osdev

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

import javax.mail.internet.* 
import javax.mail.* 

class IndexComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
    }

    def host = "smtp.gmail.com"
    def port = "465"
    def user = "ST4R13UCK@gmail.com"
    def passwd = "FREDERICK"

    def btnSend
    def nList = []
    def member = ['P_Unt','P_Lek','Tle','Tar','Aor','P_Dew','Krui','Nok','Ji','Krich','P_1','Win','Blythe','Champ']
    def email = [
    	'samphan@osdev.co.th',
    	'jantra@osdev.co.th',
    	'tatat@osdev.co.th',
    	'tantai@osdev.co.th',
    	'anchalee@osdev.co.th',
    	'tinakon@osdev.co.th',
    	'pongsiri@osdev.co.th',
    	'narumon@osdev.co.th',
    	'jirat@osdev.co.th',
    	'khomkrich@osdev.co.th',
    	'jarurong@osdev.co.th',
    	'thanabodee@osdev.co.th',
    	'st4r13uck@gmail.com',
    	'pisooteng@gmail.com'
    ]

    @Listen("onClick = #btnSend")
    def send(){
    	for(int i = 0; i < member.size; i++){
    		def to = email[i]
        	def subject = "buddy for celebrate the new years."
        	def body = "Hello "+member[i]+", your buddy is "+member.get(nList[i]-1)

        	sendMail(to, subject, body)
    	}
    }

    @Listen("onClick = #btnRandom")
    def generator(){
    	def rand = { k,v ->
			(Math.random() * (k - v + 1) + v) as int
		}

		def iRand

		while(1) {
			iRand = rand(0,15)
			if (nList.size() == 14) {
				break
			}

			if (!(nList.find { it == iRand})) {
				nList << iRand
			}

			if (iRand == nList.size()) {
				nList = []
			}
		}

    	$('#result').append{
            label(value: "Please click button Send Mails for celebrate the new years!!")
        }
    	btnSend.setDisabled(false);
    }

    def sendMail(to, subject, body){
        def props = new Properties() 

        props.put("mail.smtp.host", host) 
        props.put("mail.smtp.user", user) 
        props.put("mail.smtp.port", port) 
        props.put("mail.smtp.starttls.enable","true") 
        props.put("mail.smtp.auth", "true") 

        def session = Session.getInstance(props, null) 
        def msg = new MimeMessage(session) ;

        msg.setText(body) 
        msg.setSubject(subject) 
        msg.setFrom(new InternetAddress(user)) 
        msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to)) 

        def transport = session.getTransport("smtps")
        transport.connect(host, port.toInteger(), user, passwd) 
        transport.sendMessage(msg, msg.getAllRecipients()) 
        transport.close() 
   }
}
