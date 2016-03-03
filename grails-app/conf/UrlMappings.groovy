class UrlMappings {

    static String user = "admin"
	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller: 'Post', action: 'index')
        "500"(view:'/error')
	}
}
