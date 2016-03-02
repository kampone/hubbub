package com.grailsinaction

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
class UserControllerSpec extends Specification {

    def "Registering a user with known good parameters"(){
        given: "A set of user parameters"
        params.with {
            loginId = "glen_a_smth"
            password = "winning"
            homepage = "http://blogs.bytecode.com.au/glen"
        }

        and: "A set of profile parameters"
        params['profile.fullName'] = "Glen Smith"
        params['profile.email'] = "glen@bytecode.com.au"
        params['profile.homepage'] = "http://blogs.bytecode.com.au/glen"

        when: "The user is registered"
        request.method = "POST"
        controller.register()

        then: "The user is created, and browser redirected"
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }

}
