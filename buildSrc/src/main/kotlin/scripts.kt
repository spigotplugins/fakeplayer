import org.gradle.api.Project
import org.gradle.api.tasks.bundling.AbstractArchiveTask

fun Project.getProjectName() = "FakePlayer" + name[0].toUpperCase() + name.substring(1)

fun AbstractArchiveTask.define(
  name: String = project.getProjectName(),
  classifier: String? = null,
  version: String? = null
) {
  archiveClassifier.set(classifier)
  archiveClassifier.convention(classifier)
  archiveBaseName.set(name)
  archiveBaseName.convention(name)
  archiveVersion.set(version)
  archiveVersion.convention(version)
}
