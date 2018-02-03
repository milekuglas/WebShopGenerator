import javax.inject.Inject

import {{ model.project.package.name }}.repository._
import {{ model.project.package.name }}.model._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration
import scala.util.Try

class InitialData @Inject() ({{ model.base_product.name|lower() }}Repository: {{ model.base_product.name }}Repository,
                            {% for product in model.products %}
                             {{ product.name|lower() }}Repository: {{ product.name }}Repository,
                            {% endfor %}
                            categoryRepository: CategoryRepository
                                )
                            (implicit executionContext: ExecutionContext) {

  def createDatabase(): Unit = {
    val create = for {
      _ <- categoryRepository.create()
      _ <- {{ model.base_product.name|lower() }}Repository.create()
      {% for product in model.products %}
      _ <- {{ product.name|lower() }}Repository.create()
      {% endfor %}
    } yield ()

    val insert = for {
      count <- categoryRepository.count() if count == 0
      _ <- categoryRepository.insert(InitialData.categories)
      } yield ()

    Try(Await.result(create, Duration.Inf))
    Try(Await.result(insert, Duration.Inf))
  }

  createDatabase()
}

object InitialData {

  def categories = Seq(
  {% for category in categories %}
    Category({{ ids[category.name] }}, "{{ category.name }}"
    {%- if category.subcategory.name != null -%}
    , Some({{- ids[category.subcategory.name] }}))
    {%- endif %}
    {%- if category.subcategory.name == null -%}
    , None)
    {%- endif %}
    {%- if not loop.last -%}
        ,
    {% endif %}
  {% endfor %}
  )

}