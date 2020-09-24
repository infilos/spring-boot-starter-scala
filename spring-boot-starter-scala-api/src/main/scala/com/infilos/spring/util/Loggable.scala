package com.infilos.spring.util

import org.slf4j.{Logger, LoggerFactory}

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */

trait Loggable {
  def log: Logger = LoggerFactory.getLogger(this.getClass)
}
