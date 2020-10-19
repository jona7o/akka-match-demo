package de.innfactory.thro.akka.math

//#json-formats
import spray.json.DefaultJsonProtocol

object JsonFormats {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val isPrimeJsonFormat = jsonFormat2(IsPrimeResult)
  implicit val powerJsonFormat   = jsonFormat2(PowerResult)
}
//#json-formats
