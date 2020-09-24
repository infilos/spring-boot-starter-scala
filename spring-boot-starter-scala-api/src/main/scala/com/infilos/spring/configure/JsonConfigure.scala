package com.infilos.spring.configure

import com.fasterxml.jackson.databind.ObjectMapper
import com.infilos.relax.Json4s
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */

@Configuration
@AutoConfigureAfter(Array(classOf[WebMvcAutoConfiguration]))
class JsonConfigure {

  @Bean
  def jackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter =
    new MappingJackson2HttpMessageConverter(objectMapper())

  @Bean
  def objectMapper(): ObjectMapper = {
    Json4s.underMapper() // register custom jackson module to this Bean
  }
}

object JsonConfigure {

  def mapper: ObjectMapper = Json4s.underMapper()
}
