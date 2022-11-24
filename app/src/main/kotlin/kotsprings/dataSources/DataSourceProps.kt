package kotsprings.dataSources

import org.springframework.beans.factory.BeanClassLoaderAware
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
abstract class DataSourceProps: BeanClassLoaderAware, InitializingBean {

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private val driverClassName: String? = null

    /**
     * JDBC URL of the database.
     */
    private val url: String? = null

    /**
     * Login username of the database.
     */
    private val username: String? = null

    /**
     * Login password of the database.
     */
    private val password: String? = null

}