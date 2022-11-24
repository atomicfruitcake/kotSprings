package kotsprings.dataSources

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Bean(name = ["datasourceMain"])
@ConfigurationProperties("spring.datasource.main")
@Primary
fun dataSourceMain(): DataSource? {
    return DataSourceBuilder.create().build()
}

@Bean(name = ["datasourceTest"])
@Profile("test")
@ConfigurationProperties("spring.datasource.test")
fun dataSourceTest(): DataSource? {
    return DataSourceBuilder.create().build()
}