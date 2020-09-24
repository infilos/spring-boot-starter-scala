package com.infilos.spring.configure

import com.infilos.relax.Json4s
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration, Primary}
import org.springframework.context.ApplicationContext

/**
 * @author zhiguang.zhang on 2020-09-22.
 *
 */

@Configuration
class SwaggerConfigure {

  @Autowired
  private val context: ApplicationContext = null

  @Bean
  @Primary
  def modelResolver() = new ModelResolver(Json4s.underMapper())

  @Bean
  def buildOpenApi(): OpenAPI = {
    new OpenAPI()
      .info(new Info().title(if (StringUtils.isNotBlank(context.getApplicationName)) context.getApplicationName else "Server"))
  }
}
