package de.innfactory.thro.akka.math

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directive1, Route }

import scala.concurrent.Future
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout
import MathActor._
import akka.http.javadsl.server.Rejections
import akka.http.scaladsl.marshalling.ToResponseMarshallable

import scala.concurrent.ExecutionContext.Implicits.global // use this just in your demo

//#import-json-formats
//#user-routes-class
class MathRoutes(mathActor: ActorRef[MathActor.Command])(implicit val system: ActorSystem[_]) {

  //#user-routes-class
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._ //for bigger projects you could use circe
  import JsonFormats._
  //#import-json-formats

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("demo-app.routes.ask-timeout"))

  private val sleepMax = system.settings.config.getInt("demo-app.services.sleepMax")
  val r                = scala.util.Random

  def isPrime(value: Long): Future[IsPrimeResult] = {
    Thread.sleep(r.nextInt(sleepMax) * 1000)
    mathActor.ask(IsPrime(value, _)).map(actorResult => IsPrimeResult(value, actorResult.value))
  }

  def power(value: Long): Future[PowerResult] = {
    Thread.sleep(r.nextInt(sleepMax) * 1000)
    mathActor.ask(Power(value, _)).map(actorResult => PowerResult(value, actorResult.value))
  }

  val mathRoutes: Route =
    pathPrefix("math") {
      get {
        path("power" / LongNumber) { inputValue =>
          complete(power(inputValue))
        }
      } ~ // Route concat symbol
        get {
          path("isPrime" / LongNumber) { inputValue =>
            complete(isPrime(inputValue))
          }
        }
    }
}
