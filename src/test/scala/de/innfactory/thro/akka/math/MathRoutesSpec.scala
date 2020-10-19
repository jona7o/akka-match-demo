package de.innfactory.thro.akka.math

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.innfactory.thro.akka.math
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, WordSpec }

class MathRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  lazy val testKit                                         = ActorTestKit()
  implicit def typedSystem                                 = testKit.system
  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  val mathActor   = testKit.spawn(MathActor())
  lazy val routes = new MathRoutes(mathActor).mathRoutes

  // use the json formats to marshal and unmarshall objects in the test
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._
  //#set-up

  //#actual-test
  "MathRoutes" should {
    "return power of 8 (GET /math/power/8)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/math/power/8")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)

        // and no entries should be in the list:
        val expectedResult = PowerResult(8, 8 * 8)

        entityAs[PowerResult] shouldBe expectedResult
      }
    }
    "return isPrime of 21 (GET /math/isPrime/11)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/math/isPrime/11")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)

        // and no entries should be in the list:
        val expectedResult = IsPrimeResult(11, true)

        entityAs[IsPrimeResult] shouldBe expectedResult
      }
    }

  }
}
