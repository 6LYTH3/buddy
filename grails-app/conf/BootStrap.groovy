import th.co.osdev.*

class BootStrap {

    def init = { servletContext ->
    	def user1 = new Member(name: "nok", secretId: 1).save(failOnError: true)
    	def user2 = new Member(name: "blythe", secretId: 2).save(failOnError: true)
    	def user3 = new Member(name: "champ", secretId: 3).save(failOnError: true)
    	def user4 = new Member(name: "aor", secretId: 4).save(failOnError: true)
    }
    def destroy = {
    }
}
