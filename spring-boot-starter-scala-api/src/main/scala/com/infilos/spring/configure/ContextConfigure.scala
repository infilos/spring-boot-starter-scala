package com.infilos.spring.configure

import javax.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */

@Component
class ContextConfigure {

  @Autowired
  private val context: ApplicationContext = null

  @PostConstruct
  def construct(): Unit = {
    ContextConfigure._Configure = this
  }
}

object ContextConfigure {
  private var _Configure: ContextConfigure = _

  def isSpringRunning: Boolean = _Configure != null

  def context: ApplicationContext = {
    if (isSpringRunning) {
      _Configure.context
    } else {
      throw new UnsupportedOperationException("Spring context is unavailable!")
    }
  }

  def inject[T](beanClass: Class[T]): T = context.getBean(beanClass)

  def inject[T](beanName: String): T = context.getBean(beanName).asInstanceOf[T]
}
