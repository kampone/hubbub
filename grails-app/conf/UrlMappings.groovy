class UrlMappings {

    static String user = "admin"
	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller: "post", action: 'timeline', id: user)
        "500"(view:'/error')
	}
}
