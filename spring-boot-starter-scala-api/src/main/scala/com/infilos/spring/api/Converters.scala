package com.infilos.spring.api

import org.springframework.web.context.request.async.DeferredResult

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */
object Converters {

  class FutureResult[T](future: Future[T])(implicit context: ExecutionContext) extends DeferredResult[T] {
    future.onSuccess {
      case succed => this.setResult(succed)
    }
    future.onFailure {
      case failed => this.setErrorResult(failed)
    }
  }

  implicit def futureToDeferred[T](future: Future[T])(implicit context: ExecutionContext): DeferredResult[T] = {
    new FutureResult(future)
  }

  implicit def futureToRespond[T](future: Future[T])(implicit context: ExecutionContext): DeferredResult[Respond[T]] = {
    val respond = future.transformWith {
      case Success(value) => Future.successful(Respond.succed(value))
      case Failure(cause) => Future.successful(Respond.failed(cause))
    }
    new FutureResult[Respond[T]](respond)
  }

  implicit def eitherToRespond[T](either: Either[_, T]): Respond[T] = {
    either match {
      case Left(cause)  => Respond.failed(format(cause))
      case Right(value) => Respond.succed(value)
    }
  }

  implicit def futureEitherToRespond[T](futureEither: Future[Either[_, T]])(implicit context: ExecutionContext): DeferredResult[Respond[T]] = {
    val respond = futureEither.transformWith {
      case Success(value) => Future.successful(eitherToRespond(value))
      case Failure(cause) => Future.successful(Respond.failed(cause))
    }
    new FutureResult[Respond[T]](respond)
  }

  implicit def tryToRespond[T](trier: Try[T]): Respond[T] = {
    trier match {
      case Success(value) => Respond.succed(value)
      case Failure(cause) => Respond.failed(cause)
    }
  }

  implicit def format(any: Any): String = any match {
    case ex: Throwable => s"${ex.getClass.getSimpleName}(${ex.getMessage})"
    case null          => "Null"
    case _             => any.toString
  }
}
