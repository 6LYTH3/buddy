class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/th/co/osdev/index.zul")
		"500"(view:'/error')
	}
}
