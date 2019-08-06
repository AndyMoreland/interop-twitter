import ScalazBuild._

inThisBuild(
  List(
    organization := "dev.zio",
    homepage := Some(url("https://zio.dev")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "jdegoes",
        "John De Goes",
        "john@degoes.net",
        url("http://degoes.net")
      )
    )
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

pgpPublicRing := file("/tmp/public.asc")
pgpSecretRing := file("/tmp/secret.asc")
releaseEarlyWith := SonatypePublisher
scmInfo := Some(
  ScmInfo(url("https://github.com/zio/interop-twitter/"), "scm:git:git@github.com:zio/interop-twitter.git")
)

lazy val twitter = project
  .in(file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(stdSettings("zio-interop-twitter"))
  .settings(buildInfoSettings)
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio"     %% "zio"                  % "1.0.0-RC11-1",
      "com.twitter" %% "util-core"            % "19.8.0",
      "dev.zio"     %% "core-tests"           % "1.0.0-RC11-1" % Test classifier "tests",
      "org.specs2"  %% "specs2-core"          % "4.7.0" % Test,
      "org.specs2"  %% "specs2-matcher-extra" % "4.7.0" % Test
    )
  )
