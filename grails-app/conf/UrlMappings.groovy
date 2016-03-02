class UrlMappings {

    static String user = "admin"
	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view: "post/show")
        "500"(view:'/error')
	}
}
