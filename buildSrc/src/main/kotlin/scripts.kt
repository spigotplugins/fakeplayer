import org.gradle.api.Project

fun Project.getProjectName() = "FakePlayer" + name[0].toUpperCase() + name.substring(1)
