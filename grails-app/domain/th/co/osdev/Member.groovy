package th.co.osdev

class Member {

	Long secretId
	String name
    static constraints = {
    	secretId blank:false , unique:true
    	name blank:false , unique:true
    }

    String toString(){
    	return "${secretId}, ${name}"
    }
}
