package {{ package.name }}.service

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.{GetCategory, PostCategory}
import {{ package.name }}.repository.CategoryRepository

@ImplementedBy(classOf[CategoryServiceImpl])
trait CategoryService {
  def getAll: Future[Seq[GetCategory]]
  def get(id: Long): Future[Option[GetCategory]]
  def save(processorFull: PostCategory): Future[GetCategory]
  def delete(id: Long): Future[Int]
  def update(id: Long, processorFull: PostCategory): Future[Int]
  def getAllSubcategories(id: Long): Future[Seq[GetCategory]]
}

@Singleton()
class CategoryServiceImpl @Inject()(categoryRepository: CategoryRepository)(
    implicit executionContext: ExecutionContext) extends CategoryService {

  def getAll: Future[Seq[GetCategory]] = {
    categoryRepository.all().map(_.map(GetCategory.categoryToGetCategory))
  }

  def get(id: Long): Future[Option[GetCategory]] = {
    categoryRepository.get(id).map(_.map(GetCategory.categoryToGetCategory))
  }

  def save(processorFull: PostCategory): Future[GetCategory] = {
    categoryRepository
      .insert(processorFull)
      .map(GetCategory.categoryToGetCategory)
  }

  def delete(id: Long): Future[Int] = {
    categoryRepository.delete(id)
  }

  def update(id: Long, processorFull: PostCategory): Future[Int] = {
    categoryRepository.update(id, processorFull)
  }

  def getAllSubcategories(id: Long): Future[Seq[GetCategory]] = {
    categoryRepository.getAllSubcategories(id).map(_.map(GetCategory.categoryToGetCategory))
  }
}
