/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.75
 * apidoc:0.11.25 http://www.apidoc.me/flow/delta-config/0.1.75/play_2_x_json
 */
package io.flow.delta.config.v0.models {

  sealed trait Config

  /**
   * The name of the branch that we are actively monitoring, including any
   * information needed for the initial deploy.
   */
  case class Branch(
    name: String
  )

  case class Build(
    name: String,
    dockerfile: String,
    initialNumberInstances: Long,
    instanceType: io.flow.delta.config.v0.models.InstanceType,
    memory: Long,
    portContainer: Int,
    portHost: Int,
    stages: Seq[io.flow.delta.config.v0.models.BuildStage],
    dependencies: Seq[String]
  )

  /**
   * Used to indicate that there was a problem parsing the project configuration
   */
  case class ConfigError(
    errors: Seq[String]
  ) extends Config

  /**
   * Top level configuration for a project, including what builds and branches are
   * covered and the current status (e.g. enabled, paused, etc.)
   */
  case class ConfigProject(
    stages: Seq[io.flow.delta.config.v0.models.ProjectStage],
    builds: Seq[io.flow.delta.config.v0.models.Build],
    branches: Seq[io.flow.delta.config.v0.models.Branch]
  ) extends Config

  /**
   * Provides future compatibility in clients - in the future, when a type is added
   * to the union Config, it will need to be handled in the client code. This
   * implementation will deserialize these future types as an instance of this class.
   */
  case class ConfigUndefinedType(
    description: String
  ) extends Config

  /**
   * Represents the individual stages of the continuous delivery system that can be
   * enabled / disabled at the build level
   */
  sealed trait BuildStage

  object BuildStage {

    case object SetDesiredState extends BuildStage { override def toString = "set_desired_state" }
    case object BuildDockerImage extends BuildStage { override def toString = "build_docker_image" }
    case object Scale extends BuildStage { override def toString = "scale" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends BuildStage

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(SetDesiredState, BuildDockerImage, Scale)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): BuildStage = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[BuildStage] = byName.get(value.toLowerCase)

  }

  /**
   * List of supported AWS instance types - see
   * https://aws.amazon.com/ec2/instance-types/
   */
  sealed trait InstanceType

  object InstanceType {

    case object M4Large extends InstanceType { override def toString = "m4.large" }
    case object T2Micro extends InstanceType { override def toString = "t2.micro" }
    case object T2Small extends InstanceType { override def toString = "t2.small" }
    case object T2Medium extends InstanceType { override def toString = "t2.medium" }
    case object T2Large extends InstanceType { override def toString = "t2.large" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends InstanceType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(M4Large, T2Micro, T2Small, T2Medium, T2Large)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): InstanceType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[InstanceType] = byName.get(value.toLowerCase)

  }

  /**
   * Represents the individual stages of the continuous delivery system that can be
   * enabled / disabled at the project level
   */
  sealed trait ProjectStage

  object ProjectStage {

    case object SyncShas extends ProjectStage { override def toString = "sync_shas" }
    case object SyncTags extends ProjectStage { override def toString = "sync_tags" }
    case object Tag extends ProjectStage { override def toString = "tag" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends ProjectStage

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(SyncShas, SyncTags, Tag)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ProjectStage = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ProjectStage] = byName.get(value.toLowerCase)

  }

}

package io.flow.delta.config.v0.models {

  package object json {
    import play.api.libs.json.__
    import play.api.libs.json.JsString
    import play.api.libs.json.Writes
    import play.api.libs.functional.syntax._
    import io.flow.delta.config.v0.models.json._

    private[v0] implicit val jsonReadsUUID = __.read[String].map(java.util.UUID.fromString)

    private[v0] implicit val jsonWritesUUID = new Writes[java.util.UUID] {
      def writes(x: java.util.UUID) = JsString(x.toString)
    }

    private[v0] implicit val jsonReadsJodaDateTime = __.read[String].map { str =>
      import org.joda.time.format.ISODateTimeFormat.dateTimeParser
      dateTimeParser.parseDateTime(str)
    }

    private[v0] implicit val jsonWritesJodaDateTime = new Writes[org.joda.time.DateTime] {
      def writes(x: org.joda.time.DateTime) = {
        import org.joda.time.format.ISODateTimeFormat.dateTime
        val str = dateTime.print(x)
        JsString(str)
      }
    }

    implicit val jsonReadsDeltaConfigBuildStage = new play.api.libs.json.Reads[io.flow.delta.config.v0.models.BuildStage] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.delta.config.v0.models.BuildStage] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.BuildStage(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.BuildStage(v))
              case err: play.api.libs.json.JsError => err
            }
          }
        }
      }
    }

    def jsonWritesDeltaConfigBuildStage(obj: io.flow.delta.config.v0.models.BuildStage) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectBuildStage(obj: io.flow.delta.config.v0.models.BuildStage) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesDeltaConfigBuildStage: play.api.libs.json.Writes[BuildStage] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.BuildStage] {
        def writes(obj: io.flow.delta.config.v0.models.BuildStage) = {
          jsonWritesDeltaConfigBuildStage(obj)
        }
      }
    }

    implicit val jsonReadsDeltaConfigInstanceType = new play.api.libs.json.Reads[io.flow.delta.config.v0.models.InstanceType] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.delta.config.v0.models.InstanceType] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.InstanceType(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.InstanceType(v))
              case err: play.api.libs.json.JsError => err
            }
          }
        }
      }
    }

    def jsonWritesDeltaConfigInstanceType(obj: io.flow.delta.config.v0.models.InstanceType) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectInstanceType(obj: io.flow.delta.config.v0.models.InstanceType) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesDeltaConfigInstanceType: play.api.libs.json.Writes[InstanceType] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.InstanceType] {
        def writes(obj: io.flow.delta.config.v0.models.InstanceType) = {
          jsonWritesDeltaConfigInstanceType(obj)
        }
      }
    }

    implicit val jsonReadsDeltaConfigProjectStage = new play.api.libs.json.Reads[io.flow.delta.config.v0.models.ProjectStage] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.delta.config.v0.models.ProjectStage] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.ProjectStage(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.ProjectStage(v))
              case err: play.api.libs.json.JsError => err
            }
          }
        }
      }
    }

    def jsonWritesDeltaConfigProjectStage(obj: io.flow.delta.config.v0.models.ProjectStage) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectProjectStage(obj: io.flow.delta.config.v0.models.ProjectStage) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesDeltaConfigProjectStage: play.api.libs.json.Writes[ProjectStage] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.ProjectStage] {
        def writes(obj: io.flow.delta.config.v0.models.ProjectStage) = {
          jsonWritesDeltaConfigProjectStage(obj)
        }
      }
    }

    implicit def jsonReadsDeltaConfigBranch: play.api.libs.json.Reads[Branch] = {
      (__ \ "name").read[String].map { x => new Branch(name = x) }
    }

    def jsObjectBranch(obj: io.flow.delta.config.v0.models.Branch) = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name)
      )
    }

    implicit def jsonWritesDeltaConfigBranch: play.api.libs.json.Writes[Branch] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.Branch] {
        def writes(obj: io.flow.delta.config.v0.models.Branch) = {
          jsObjectBranch(obj)
        }
      }
    }

    implicit def jsonReadsDeltaConfigBuild: play.api.libs.json.Reads[Build] = {
      (
        (__ \ "name").read[String] and
        (__ \ "dockerfile").read[String] and
        (__ \ "initial_number_instances").read[Long] and
        (__ \ "instance_type").read[io.flow.delta.config.v0.models.InstanceType] and
        (__ \ "memory").read[Long] and
        (__ \ "port_container").read[Int] and
        (__ \ "port_host").read[Int] and
        (__ \ "stages").read[Seq[io.flow.delta.config.v0.models.BuildStage]] and
        (__ \ "dependencies").read[Seq[String]]
      )(Build.apply _)
    }

    def jsObjectBuild(obj: io.flow.delta.config.v0.models.Build) = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name),
        "dockerfile" -> play.api.libs.json.JsString(obj.dockerfile),
        "initial_number_instances" -> play.api.libs.json.JsNumber(obj.initialNumberInstances),
        "instance_type" -> play.api.libs.json.JsString(obj.instanceType.toString),
        "memory" -> play.api.libs.json.JsNumber(obj.memory),
        "port_container" -> play.api.libs.json.JsNumber(obj.portContainer),
        "port_host" -> play.api.libs.json.JsNumber(obj.portHost),
        "stages" -> play.api.libs.json.Json.toJson(obj.stages),
        "dependencies" -> play.api.libs.json.Json.toJson(obj.dependencies)
      )
    }

    implicit def jsonWritesDeltaConfigBuild: play.api.libs.json.Writes[Build] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.Build] {
        def writes(obj: io.flow.delta.config.v0.models.Build) = {
          jsObjectBuild(obj)
        }
      }
    }

    implicit def jsonReadsDeltaConfigConfigError: play.api.libs.json.Reads[ConfigError] = {
      (__ \ "errors").read[Seq[String]].map { x => new ConfigError(errors = x) }
    }

    def jsObjectConfigError(obj: io.flow.delta.config.v0.models.ConfigError) = {
      play.api.libs.json.Json.obj(
        "errors" -> play.api.libs.json.Json.toJson(obj.errors)
      )
    }

    implicit def jsonReadsDeltaConfigConfigProject: play.api.libs.json.Reads[ConfigProject] = {
      (
        (__ \ "stages").read[Seq[io.flow.delta.config.v0.models.ProjectStage]] and
        (__ \ "builds").read[Seq[io.flow.delta.config.v0.models.Build]] and
        (__ \ "branches").read[Seq[io.flow.delta.config.v0.models.Branch]]
      )(ConfigProject.apply _)
    }

    def jsObjectConfigProject(obj: io.flow.delta.config.v0.models.ConfigProject) = {
      play.api.libs.json.Json.obj(
        "stages" -> play.api.libs.json.Json.toJson(obj.stages),
        "builds" -> play.api.libs.json.Json.toJson(obj.builds),
        "branches" -> play.api.libs.json.Json.toJson(obj.branches)
      )
    }

    implicit def jsonReadsDeltaConfigConfig: play.api.libs.json.Reads[Config] = new play.api.libs.json.Reads[Config] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[Config] = {
        (js \ "discriminator").validate[String] match {
          case play.api.libs.json.JsError(msg) => play.api.libs.json.JsError(msg)
          case play.api.libs.json.JsSuccess(discriminator, _) => {
            discriminator match {
              case "config_project" => js.validate[io.flow.delta.config.v0.models.ConfigProject]
              case "config_error" => js.validate[io.flow.delta.config.v0.models.ConfigError]
              case other => play.api.libs.json.JsSuccess(io.flow.delta.config.v0.models.ConfigUndefinedType(other))
            }
          }
        }
      }
    }

    def jsObjectConfig(obj: io.flow.delta.config.v0.models.Config) = {
      obj match {
        case x: io.flow.delta.config.v0.models.ConfigProject => jsObjectConfigProject(x) ++ play.api.libs.json.Json.obj("discriminator" -> "config_project")
        case x: io.flow.delta.config.v0.models.ConfigError => jsObjectConfigError(x) ++ play.api.libs.json.Json.obj("discriminator" -> "config_error")
        case other => {
          sys.error(s"The type[${other.getClass.getName}] has no JSON writer")
        }
      }
    }

    implicit def jsonWritesDeltaConfigConfig: play.api.libs.json.Writes[Config] = {
      new play.api.libs.json.Writes[io.flow.delta.config.v0.models.Config] {
        def writes(obj: io.flow.delta.config.v0.models.Config) = {
          jsObjectConfig(obj)
        }
      }
    }
  }
}

package io.flow.delta.config.v0 {

  object Bindables {

    import play.api.mvc.{PathBindable, QueryStringBindable}
    import org.joda.time.{DateTime, LocalDate}
    import org.joda.time.format.ISODateTimeFormat
    import io.flow.delta.config.v0.models._

    // Type: date-time-iso8601
    implicit val pathBindableTypeDateTimeIso8601 = new PathBindable.Parsing[org.joda.time.DateTime](
      ISODateTimeFormat.dateTimeParser.parseDateTime(_), _.toString, (key: String, e: _root_.java.lang.Exception) => s"Error parsing date time $key. Example: 2014-04-29T11:56:52Z"
    )

    implicit val queryStringBindableTypeDateTimeIso8601 = new QueryStringBindable.Parsing[org.joda.time.DateTime](
      ISODateTimeFormat.dateTimeParser.parseDateTime(_), _.toString, (key: String, e: _root_.java.lang.Exception) => s"Error parsing date time $key. Example: 2014-04-29T11:56:52Z"
    )

    // Type: date-iso8601
    implicit val pathBindableTypeDateIso8601 = new PathBindable.Parsing[org.joda.time.LocalDate](
      ISODateTimeFormat.yearMonthDay.parseLocalDate(_), _.toString, (key: String, e: _root_.java.lang.Exception) => s"Error parsing date $key. Example: 2014-04-29"
    )

    implicit val queryStringBindableTypeDateIso8601 = new QueryStringBindable.Parsing[org.joda.time.LocalDate](
      ISODateTimeFormat.yearMonthDay.parseLocalDate(_), _.toString, (key: String, e: _root_.java.lang.Exception) => s"Error parsing date $key. Example: 2014-04-29"
    )

    // Enum: BuildStage
    private[this] val enumBuildStageNotFound = (key: String, e: _root_.java.lang.Exception) => s"Unrecognized $key, should be one of ${io.flow.delta.config.v0.models.BuildStage.all.mkString(", ")}"

    implicit val pathBindableEnumBuildStage = new PathBindable.Parsing[io.flow.delta.config.v0.models.BuildStage] (
      BuildStage.fromString(_).get, _.toString, enumBuildStageNotFound
    )

    implicit val queryStringBindableEnumBuildStage = new QueryStringBindable.Parsing[io.flow.delta.config.v0.models.BuildStage](
      BuildStage.fromString(_).get, _.toString, enumBuildStageNotFound
    )

    // Enum: InstanceType
    private[this] val enumInstanceTypeNotFound = (key: String, e: _root_.java.lang.Exception) => s"Unrecognized $key, should be one of ${io.flow.delta.config.v0.models.InstanceType.all.mkString(", ")}"

    implicit val pathBindableEnumInstanceType = new PathBindable.Parsing[io.flow.delta.config.v0.models.InstanceType] (
      InstanceType.fromString(_).get, _.toString, enumInstanceTypeNotFound
    )

    implicit val queryStringBindableEnumInstanceType = new QueryStringBindable.Parsing[io.flow.delta.config.v0.models.InstanceType](
      InstanceType.fromString(_).get, _.toString, enumInstanceTypeNotFound
    )

    // Enum: ProjectStage
    private[this] val enumProjectStageNotFound = (key: String, e: _root_.java.lang.Exception) => s"Unrecognized $key, should be one of ${io.flow.delta.config.v0.models.ProjectStage.all.mkString(", ")}"

    implicit val pathBindableEnumProjectStage = new PathBindable.Parsing[io.flow.delta.config.v0.models.ProjectStage] (
      ProjectStage.fromString(_).get, _.toString, enumProjectStageNotFound
    )

    implicit val queryStringBindableEnumProjectStage = new QueryStringBindable.Parsing[io.flow.delta.config.v0.models.ProjectStage](
      ProjectStage.fromString(_).get, _.toString, enumProjectStageNotFound
    )

  }

}
