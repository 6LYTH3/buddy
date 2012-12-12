package th.co.osdev



import org.junit.*
import grails.test.mixin.*

@TestFor(BuddyController)
@Mock(Buddy)
class BuddyControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/buddy/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.buddyInstanceList.size() == 0
        assert model.buddyInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.buddyInstance != null
    }

    void testSave() {
        controller.save()

        assert model.buddyInstance != null
        assert view == '/buddy/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/buddy/show/1'
        assert controller.flash.message != null
        assert Buddy.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/buddy/list'

        populateValidParams(params)
        def buddy = new Buddy(params)

        assert buddy.save() != null

        params.id = buddy.id

        def model = controller.show()

        assert model.buddyInstance == buddy
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/buddy/list'

        populateValidParams(params)
        def buddy = new Buddy(params)

        assert buddy.save() != null

        params.id = buddy.id

        def model = controller.edit()

        assert model.buddyInstance == buddy
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/buddy/list'

        response.reset()

        populateValidParams(params)
        def buddy = new Buddy(params)

        assert buddy.save() != null

        // test invalid parameters in update
        params.id = buddy.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/buddy/edit"
        assert model.buddyInstance != null

        buddy.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/buddy/show/$buddy.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        buddy.clearErrors()

        populateValidParams(params)
        params.id = buddy.id
        params.version = -1
        controller.update()

        assert view == "/buddy/edit"
        assert model.buddyInstance != null
        assert model.buddyInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/buddy/list'

        response.reset()

        populateValidParams(params)
        def buddy = new Buddy(params)

        assert buddy.save() != null
        assert Buddy.count() == 1

        params.id = buddy.id

        controller.delete()

        assert Buddy.count() == 0
        assert Buddy.get(buddy.id) == null
        assert response.redirectedUrl == '/buddy/list'
    }
}
