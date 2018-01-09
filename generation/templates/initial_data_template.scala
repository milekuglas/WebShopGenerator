import javax.inject.Inject

import {{ model.project.package.name }}.repository._
import {{ model.project.package.name }}.model._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration
import scala.util.Try

class InitialData @Inject() ({{ model.base_product.name|lower() }}Repository: {{ model.base_product.name }}Repository,
                            {% for product in model.products %}
                             {{ product.name|lower() }}Repository: {{ product.name }}Repository
                            {%- if not loop.last -%}
                              ,
                            {% endif %}
                            {% endfor %}
                                )
                            (implicit executionContext: ExecutionContext) {

  def insert(): Unit = {
    val insertData = for {
      _ <- {{ model.base_product.name|lower() }}Repository.create()
      {% for product in model.products %}
      _ <- {{ product.name|lower() }}Repository.create()
      {% endfor %}
    } yield ()

    Try(Await.result(insertData, Duration.Inf))
  }

  insert()
}