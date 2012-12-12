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
    def user = "account@gmail.com"
    def passwd = "password"

    def btnSend
    def nList = []
    def member = ['P_Unt','P_Lek','Tle','Tar','Aor','P_Dew','Krui','Nok','Ji','Krich','P_1','Win','Blythe','Champ']

    def emails = [
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com',
    	'test@gmail.com'
    ]

    @Listen("onClick = #btnSend")
    def send(){
    	for(int i = 0; i < member.size; i++){
    		def to = emails[i]
        	def subject = "buddy for the new years of celebrate OSDev ."
        	def body = "Hello "+member[i]+" the OSDev have celebrate the new years. you must send secret gift to buddy, \nyour buddy is "+member.get(nList[i]-1)

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
