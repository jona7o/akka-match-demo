package de.innfactory.thro.akka.math

import java.math.BigInteger

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.immutable

sealed trait HTTPResponse
final case class IsPrimeResult(inputValue: Long, outputValue: Boolean) extends HTTPResponse
final case class PowerResult(inpuValue: Long, outputValue: Long)       extends HTTPResponse

object MathActor {
  // actor protocol
  sealed trait Command
  final case class IsPrime(value: Long, replyTo: ActorRef[IsPrimeResponse]) extends Command
  final case class Power(value: Long, replyTo: ActorRef[PowerResponse])     extends Command

  final case class IsPrimeResponse(value: Boolean)
  final case class PowerResponse(value: Long)

  private def isPrime(n: Long) = !(2L to n - 1).exists(n % _ == 0)
  private def power(n: Long)   = n * n

  def apply(): Behavior[Command] = registry()

  private def registry(): Behavior[Command] =
    Behaviors.receiveMessage {
      case IsPrime(value, replyTo) =>
        replyTo ! IsPrimeResponse(isPrime(value))
        Behaviors.same
      case Power(value, replyTo)   =>
        replyTo ! PowerResponse(power(value))
        Behaviors.same
    }
}
