package com.grailsinaction

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User,Post])
class PostControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "Get a users timeline given their id"(){
        given: "A user with postsin the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password")
        chuck.addToPosts(new Post(content: "First post"))
        chuck.addToPosts(new Post(content: "Second post"))
        chuck.save(failOnError: true)

        and: "A loginId parameter"
        params.id = chuck.loginId

        when: "The timeline is invoked"
        def model = controller.timeline()

        then: "the user is in the returned model"
        model.user.loginId == "chuck_norris"
        model.user.posts.size() == 2
    }

    def "Adding a valid new post to the time line"(){
        given: "A mock post service"
        def mockPostService = Mock(PostService)
        1 * mockPostService.createPost(_,_) >> new Post(content: "Mock Post")
        controller.postService = mockPostService

        when: "Controller is invoked"
        def result = controller.addPost("joe_cool", "Posting up a strom")

        then: "redirect to timeline, aflash message tells all is well"
        flash.message ==~ /Added new post: Mock.*/
        response.redirectedUrl == '/post/timeline/joe_cool'
    }

    @Unroll
    def "Testing id of #suppliedId redirects to #expectedUrl"() {
        given:
        params.id = suppliedId

        when:
        controller.index()

        then:
        response.redirectedUrl == expectedUrl

        where:
        suppliedId | expectedUrl
        'joe_cool' | '/post/timeline/joe_cool'
        null       | '/post/timeline/chuck_norris'
    }

}
