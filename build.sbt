lazy val akkaHttpVersion = "10.2.1"
lazy val akkaVersion     = "2.6.10"

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin, JavaAppPackaging)
  .settings(
    inThisBuild(
      List(
        organization := "de.innfactory.thro.akka.math",
        scalaVersion := "2.13.3",
        version := "1.0.0"
      )
    ),
    name := "akka-math-demo",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"     % "logback-classic"          % "1.2.3",
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.0.8"         % Test
    )
  )
  .settings(
    dockerExposedPorts in Docker ++= Seq(8080),
    dockerBaseImage in Docker := "openjdk:11.0.6-jre-slim",
    dockerRepository in Docker := Some("eu.gcr.io/innfactory-throsenheim"),
    packageName in Docker := "akka-math"
  )
