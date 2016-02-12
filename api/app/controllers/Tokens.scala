package controllers

import db.{InternalTokenForm, TokensDao}
import io.flow.delta.v0.models.{Token, TokenForm}
import io.flow.delta.v0.models.json._
import io.flow.common.v0.models.User
import io.flow.common.v0.models.json._
import io.flow.play.clients.UserTokensClient
import io.flow.play.util.Validation
import io.flow.postgresql.Authorization
import play.api.mvc._
import play.api.libs.json._

class Tokens @javax.inject.Inject() (
  val userTokensClient: UserTokensClient
) extends Controller with BaseIdentifiedRestController {

  import scala.concurrent.ExecutionContext.Implicits.global

  def get(
    id: Option[Seq[String]],
    userId: Option[String],
    limit: Long = 25,
    offset: Long = 0
  ) = Identified { request =>
    Ok(
      Json.toJson(
        TokensDao.findAll(
          authorization(request),
          ids = optionals(id),
          userId = userId,
          limit = limit,
          offset = offset
        )
      )
    )
  }

  def getById(id: String) = Identified { request =>
    withToken(request.user, id) { token =>
      Ok(Json.toJson(TokensDao.addCleartextIfAvailable(request.user, token)))
    }
  }

  def post() = Identified(parse.json) { request =>
    request.body.validate[TokenForm] match {
      case e: JsError => {
        UnprocessableEntity(Json.toJson(Validation.invalidJson(e)))
      }
      case s: JsSuccess[TokenForm] => {
        TokensDao.create(request.user, InternalTokenForm.UserCreated(s.get)) match {
          case Left(errors) => UnprocessableEntity(Json.toJson(Validation.errors(errors)))
          case Right(token) => Created(Json.toJson(token))
        }
      }
    }
  }

  def deleteById(id: String) = Identified { request =>
    withToken(request.user, id) { token =>
      TokensDao.delete(request.user, token)
      NoContent
    }
  }

  def withToken(user: User, id: String)(
    f: Token => Result
  ) = {
    TokensDao.findById(Authorization.User(user.id), id) match {
      case None => {
        Results.NotFound
      }
      case Some(token) => {
        f(token)
      }
    }
  }

}