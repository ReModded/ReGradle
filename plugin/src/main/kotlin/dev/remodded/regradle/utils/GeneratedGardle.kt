package dev.remodded.regradle.utils

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

// Project
val Project.sourceSets: SourceSetContainer get() =
    (this as ExtensionAware).extensions.getByName("sourceSets") as SourceSetContainer

val Project.kotlin get() = extensions.getByName<KotlinJvmProjectExtension>("kotlin")
fun Project.kotlin(configure: Action<KotlinJvmProjectExtension>) =
    extensions.configure("kotlin", configure)

val Project.java get() = extensions.getByName<JavaPluginExtension>("java")
fun Project.java(configure: Action<JavaPluginExtension>) =
    extensions.configure("java", configure)

val Project.publishing get() = extensions.getByName<PublishingExtension>("publishing")
fun Project.publishing(configure: Action<PublishingExtension>) =
    extensions.configure("publishing", configure)

// TaskContainer
val TaskContainer.assemble get() = named<DefaultTask>("assemble")
val TaskContainer.jar get() = named<Jar>("jar")
val TaskContainer.shadowJar get() = named<ShadowJar>("shadowJar")
val TaskContainer.build get() = named<DefaultTask>("build")
val TaskContainer.clean get() = named<Delete>("clean")
val TaskContainer.test get() = named<Test>("test")


// SourceSetContainer
val SourceSetContainer.main: NamedDomainObjectProvider<SourceSet>
    get() = this.named<SourceSet>("main")

// DependencyHandler
fun DependencyHandler.api(artifact: String) = add("api", artifact)
fun DependencyHandler.compileOnly(artifact: String) = add("compileOnly", artifact)
fun DependencyHandler.implementation(artifact: String) = add("implementation", artifact)
