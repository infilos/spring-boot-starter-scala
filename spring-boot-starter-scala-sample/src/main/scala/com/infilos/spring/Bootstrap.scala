package com.infilos.spring

import com.infilos.spring.configure.ContextConfigure
import com.infilos.spring.util.Loggable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.scheduling.annotation.EnableAsync

/**
 * @author zhiguang.zhang on 2020-09-24.
 *
 */

@EnableAsync
@SpringBootApplication
class Bootstrap

object Bootstrap extends App with Loggable {
  SpringApplication.run(classOf[Bootstrap])

  log.info(s"Spring is running: ${ContextConfigure.isSpringRunning}")
}
