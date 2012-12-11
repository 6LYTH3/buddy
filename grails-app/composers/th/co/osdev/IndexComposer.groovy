package th.co.osdev

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class IndexComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
    }
 	
 	@Listen("onClick = #btnRandom")
    def generator(){
		def iName = $('#nameBox').val()
		def iEmail = $('#emailBox').val()
		def index = getId(iName)
		def result = 0
	
		sleep(3000){
			def iRand = getRandom()
			while(1) {
				if (iRand != index) {
					result = iRand
					break
				}else {
					iRand = getRandom()
				}
			}	
    	}

    	def ibuddy = new Buddy(buddyId: result, name: iName, email: iEmail).save()
		$('#img').setSrc("/images/grails_logo.png")
    }

    def getRandom(){
    	def rand = { k,v ->
			(Math.random() * (k - v + 1) + v) as int
		}

		def buddyList = Buddy.list()
		def irand = rand(0,13)

    	while(1) {
			if (buddyList.find { it == irand}) {
				irand = rand(0,13)
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
}
