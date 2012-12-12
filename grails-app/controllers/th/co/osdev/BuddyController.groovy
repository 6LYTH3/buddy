package th.co.osdev

import org.springframework.dao.DataIntegrityViolationException

class BuddyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [buddyInstanceList: Buddy.list(params), buddyInstanceTotal: Buddy.count()]
    }

    def create() {
        [buddyInstance: new Buddy(params)]
    }

    def save() {
        def buddyInstance = new Buddy(params)
        if (!buddyInstance.save(flush: true)) {
            render(view: "create", model: [buddyInstance: buddyInstance])
            return
        }

        def iBuddy = Member.get(buddyInstance.buddyId)
        sendMail {
            to "${buddyInstance.email}"
            subject "buddy for celebrate the new years."
            body "Hello ${buddyInstance.name}! , your buddy is "+iBuddy.name
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'buddy.label', default: 'Buddy'), buddyInstance.id])
        redirect(action: "show", id: buddyInstance.id)
    }

    def show(Long id) {
        def buddyInstance = Buddy.get(id)
        if (!buddyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "list")
            return
        }

        [buddyInstance: buddyInstance]
    }

    def edit(Long id) {
        def buddyInstance = Buddy.get(id)
        if (!buddyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "list")
            return
        }

        [buddyInstance: buddyInstance]
    }

    def update(Long id, Long version) {
        def buddyInstance = Buddy.get(id)
        if (!buddyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (buddyInstance.version > version) {
                buddyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'buddy.label', default: 'Buddy')] as Object[],
                          "Another user has updated this Buddy while you were editing")
                render(view: "edit", model: [buddyInstance: buddyInstance])
                return
            }
        }

        buddyInstance.properties = params

        if (!buddyInstance.save(flush: true)) {
            render(view: "edit", model: [buddyInstance: buddyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'buddy.label', default: 'Buddy'), buddyInstance.id])
        redirect(action: "show", id: buddyInstance.id)
    }

    def delete(Long id) {
        def buddyInstance = Buddy.get(id)
        if (!buddyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "list")
            return
        }

        try {
            buddyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'buddy.label', default: 'Buddy'), id])
            redirect(action: "show", id: id)
        }
    }
}
