package com.infilos.spring.api

import com.fasterxml.jackson.annotation.JsonIgnore
import com.infilos.spring.api.Respond._

/**
 * @author zhiguang.zhang on 2020-09-21.
 *
 */
final case class Respond[+T](code: Int, error: String, retry: Boolean = false, data: T) {

  @JsonIgnore
  def keepRetry(): Respond[T] = this.copy(retry = true)

  @JsonIgnore
  def isSucced: Boolean = code == SuccedCode

  @JsonIgnore
  def isValidSucced: Boolean = code == SuccedCode && data != null

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Respond[_]]

  override def equals(obj: Any): Boolean = obj match {
    case that: Respond[_] =>
      this.canEqual(that) &&
        this.code == that.code &&
        this.error == that.error &&
        this.retry == that.retry &&
        this.data == that.data
    case _ => false
  }

  override def hashCode(): Int = (code, error, retry, data).##

  override def toString: String = s"Respond(code=$code,error=$error,needRetry=$retry,data=$data)"
}

object Respond {

  val SuccedCode: Int = 0
  val FailedCode: Int = -1

  def succed[T](data: T): Respond[T] =
    Respond(SuccedCode, null, retry = false, data)

  def succed[T](code: Int, data: T): Respond[T] =
    Respond(code, null, retry = false, data)

  def succed[T](code: Int): Respond[T] =
    Respond(code, null, retry = false, null.asInstanceOf[T])

  def succed[T](): Respond[T] =
    Respond(SuccedCode, null, retry = false, null.asInstanceOf[T])

  def failed[T](error: String): Respond[T] =
    Respond(FailedCode, error, retry = false, null.asInstanceOf[T])

  def failed[T](code: Int, error: String): Respond[T] =
    Respond(code, error, retry = false, null.asInstanceOf[T])

  def failed[T](cause: Throwable): Respond[T] =
    Respond(FailedCode, s"${cause.getClass.getSimpleName}(${cause.getMessage})", retry = false, null.asInstanceOf[T])

  def failed[T](code: Int, cause: Throwable, retry: Boolean = false): Respond[T] =
    Respond(code, s"${cause.getClass.getSimpleName}(${cause.getMessage})", retry = retry, null.asInstanceOf[T])

  def failed[T](): Respond[T] =
    Respond(FailedCode, null, retry = false, null.asInstanceOf[T])
}
