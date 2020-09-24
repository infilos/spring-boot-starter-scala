package com.infilos.spring.rest

import com.infilos.spring.api.Respond
import com.infilos.spring.api.Converters._
import com.infilos.spring.util.Executors
import org.springframework.web.bind.annotation._
import org.springframework.web.context.request.async.DeferredResult

import scala.concurrent.Future

/**
 * @author zhiguang.zhang on 2020-09-24.
 *
 */

@RestController
class HelloController {
  private implicit val ctx = Executors.IODispatcher

  @GetMapping
  def home(): String = "Server is Running!"

  @GetMapping(Array("/hello"))
  def hello(): Respond[String] = Respond.succed("Hello, World!")

  @PostMapping(Array("/whoami"))
  def whoami(@RequestParam iam: String): DeferredResult[Respond[String]] = Future(Respond.succed(s"Hello, $iam!"))
}
