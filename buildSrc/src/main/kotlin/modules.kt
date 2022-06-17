import org.gradle.api.Project

private val jarModule = setOf(
  "Api",
  "Plugin",
  "V1_18_R1",
  "V1_18_R2",
).fakePlayer()

private val publishingModule = setOf(
  "Api",
).fakePlayer()

private val pluginModule = setOf(
  "Plugin",
).fakePlayer()

fun Project.isPublishing() = publishingModule.contains(getQualifiedProjectName())

fun Project.isPlugin() = pluginModule.contains(getQualifiedProjectName())

fun Project.isJar() = jarModule.contains(getQualifiedProjectName())

private fun Iterable<String>.fakePlayer() = map { "FakePlayer$it" }
