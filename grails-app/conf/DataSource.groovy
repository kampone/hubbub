dataSource {
        pooled = true
        jmxExport = true

       /* driverClassName = "com.mysql.jdbc.Driver"
        username = "admintPx9tun"
        password = "TkLw4h7w_M5I"*/
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    format_sql = true
}

// environment specific settings
environments {
    development {
        dataSource {

            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
/*
            dbCreate = "update"
            url = "jdbc:mysql://127.8.66.130:3306/hubbub"*/
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://127.8.66.130:3306/hubbub"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://127.8.66.130:3306/hubbub"
        }
    }
}
