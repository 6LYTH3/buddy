package th.co.osdev

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

import javax.mail.internet.* ;
import javax.mail.* ;


class IndexComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
    }
 	
    def host = "smtp.gmail.com"
    def port = "465"
    def user = "ST4R13UCK@gmail.com"
    def passwd = "FREDERICK"

 	@Listen("onClick = #btnRandom")
    def generator(){
		def iName = $('#nameBox').val()
		def iEmail = $('#emailBox').val()
		def index = getId(iName)
		def result = 0
	
		def iRand = getRandom()
		while(1) {
			//without me
			if (iRand != index) {
				result = iRand
				break
			}else {
				iRand = getRandom()
			}
		}	
    
		sleep(3000)
		def iBuddy = Member.get(result)
		def to = iEmail
        def subject = "buddy for celebrate the new years."
        def body = "Hello "+iName+", your buddy is "+iBuddy.name

    	def buddyInstance = new Buddy(buddyId: result, name: iName, email: iEmail)
        if (buddyInstance.save(flush: true)) {
           sendMail(to, subject, body)
        }
		$('#img').setSrc("/images/grails_logo.png")
    }

    def getRandom(){
    	def rand = { k,v ->
			(Math.random() * (k - v + 1) + v) as int
		}

		def buddyList = Buddy.list()
		def irand = rand(0,5)

    	while(1) {
			if (buddyList.find{ it == irand}) {
				irand = rand(0,5)
			}else {
				break
			}
		}
		return irand
    }

    def getId(name){
    	def lists = Member.list()
    	for(iname in lists) {
    		if ((iname.name).equals(name)) {
    			return iname.secretId
    		}
    	}
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
