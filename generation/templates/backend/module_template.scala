import com.google.inject.AbstractModule

class Module extends AbstractModule {
  protected def configure: Unit = {
    bind(classOf[InitialData]).asEagerSingleton()
  }
}
