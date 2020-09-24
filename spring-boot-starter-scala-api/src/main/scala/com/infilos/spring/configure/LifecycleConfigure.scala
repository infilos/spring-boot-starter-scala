package com.infilos.spring.configure

import java.util.concurrent.TimeUnit

import com.infilos.spring.util.{Executors, Loggable}
import javax.annotation.PostConstruct
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component

import scala.collection.mutable.ArrayBuffer

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */

@Component
class LifecycleConfigure extends ApplicationListener[ContextClosedEvent] with Loggable {

  @PostConstruct
  def construct(): Unit = {
    // setup io-thread-pool shutdown hook
    LifecycleConfigure.registerOnShutdown(() => {
      try {
        Executors.IOThreadPool.shutdown()
        if (!Executors.IOThreadPool.awaitTermination(30, TimeUnit.SECONDS)) {
          log.warn("IO thread pool shutdown gracefully failed.")
        } else {
          log.warn("IO thread pool shutdown gracefully succed.")
        }
      } catch {
        case _: Throwable => Thread.currentThread().interrupt()
      }
    })

    // setup db-thread-pool shutdown hook
    LifecycleConfigure.registerOnShutdown(() => {
      try {
        Executors.DBThreadPool.shutdown()
        if (!Executors.IOThreadPool.awaitTermination(30, TimeUnit.SECONDS)) {
          log.warn("DB thread pool shutdown gracefully failed.")
        } else {
          log.warn("DB thread pool shutdown gracefully succed.")
        }
      } catch {
        case _: Throwable => Thread.currentThread().interrupt()
      }
    })
  }

  override def onApplicationEvent(e: ContextClosedEvent): Unit = {
    LifecycleConfigure.shutdowns.foreach { task =>
      try {
        task.run()
      } catch {
        case _: Throwable => Thread.currentThread().interrupt()
      }
    }
  }
}

object LifecycleConfigure {
  private val shutdowns: ArrayBuffer[Runnable] = ArrayBuffer.empty

  def registerOnShutdown(task: Runnable): Unit = synchronized {
    shutdowns.append(task)
  }
}
