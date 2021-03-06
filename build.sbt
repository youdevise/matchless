name := "matchless"

organization := "com.timgroup"

// Change to non-SNAPSHOT version when you publish a release
version := "1.0.6-SNAPSHOT"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation")

crossScalaVersions := Seq("2.9.0-1", "2.9.1", "2.9.2", "2.10.0")

libraryDependencies <++= scalaVersion(specs2Dependencies(_, "compile"))

/////////////////////////////////////////////////////////////////////
// Deploy to Sonatype using SBT
// http://www.scala-sbt.org/release/docs/Community/Using-Sonatype.html
/////////////////////////////////////////////////////////////////////

publishMavenStyle := true

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/youdevise/matchless</url>
  <licenses>
    <license>
      <name>MIT license</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:youdevise/matchless.git</url>
    <connection>scm:git:git@github.com:youdevise/matchless.git</connection>
  </scm>
  <developers>
    <developer>
      <id>poetix</id>
      <name>Dominic Fox</name>
      <email>dominic.fox@timgroup.com</email>
    </developer>
    <developer>
      <id>ms-tg</id>
      <name>Marc Siegel</name>
      <email>marc.siegel@timgroup.com</email>
    </developer>
  </developers>
)

// NOTE (2013-04-23, Marc): To publish to Sonatype:
//
// 1. Install sbt-extras as ~/bin/sbt-extras.sh, to handle multiple versions of sbt gracefully (if not already puppeted to your machine)
//      https://github.com/paulp/sbt-extras
//
// 2. Generate and publish your GPG key
//      https://docs.sonatype.org/display/Repository/How+To+Generate+PGP+Signatures+With+Maven
//
// 3. Setup sbt-pgp plugin
//      ~/.sbt/0.12.3/plugins/gpg.sbt:
//          addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8")
//
// 4. Setup local Sonatype credentials
//      ~/.sbt/0.12.3/plugins/sonatype.sbt:
//          credentials += Credentials("Sonatype Nexus Repository Manager",
//                                     "oss.sonatype.org",
//                                     USERNAME,
//                                     PASSWORD)
//
// 5. Bump version
//       For snapshot versions: 1.0.0-SNAPSHOT
//       For releases: 1.0.0
//
// 6. Deploy and Stage to Sonatype for all desired Scala versions
//       sbt-extras.sh ++2.9.1 clean publish-signed    // scalaVersion given on command line
//       sbt-extras.sh ++2.9.2 clean publish-signed    // scalaVersion given on command line
//       sbt-extras.sh ++2.10.0 clean publish-signed   // scalaVersion given on command line
//
// 7. Manually release it to Maven Central
//       https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-8a.ReleaseIt

// NOTE: If you have multiple GPG keys, and it is signing with the wrong one, you must
// do additional steps:
//
//   2.a. List your GPG keys to identify the one you want to sign with
//          gpg --list-keys
//
//   2.b. Edit ~/.gnupg/gpg.conf to set the default-key to the desired key id
//
//   2.c. Launch gpg-agent in your active terminal
//          eval $(gpg-agent --daemon)
//
//   2.d. Uncomment the following line so that SBT will use the GPG app (and its settings)
//useGpg := true

