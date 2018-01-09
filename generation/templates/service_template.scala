package {{ package.name }}.service

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import dto.Get{{ product.name }}
import repository.{{ product.name }}Repository


@Singleton()
class {{ product.name }}Service @Inject()({{ product.name|lower() }}Repository:
{{- product.name }}Repository)(implicit executionContext: ExecutionContext) {

  def getAll: Future[Seq[Get{{ product.name }}]] = {
    {{ product.name|lower() }}Repository.all().map(_.map(Get{{ product.name }}.{{ product.name|lower }}ToGet{{ product.name }}))
  }

  def get(id: Long): Future[Option[Get{{ product.name }}]] = {
    {{ product.name|lower() }}Repository.get(id).map(_.map(Get{{ product.name }}.{{ product.name|lower() }}ToGet{{ product.name }}))
  }
}
