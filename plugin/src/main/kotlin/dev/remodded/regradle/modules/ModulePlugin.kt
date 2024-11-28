package dev.remodded.regradle.modules

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import dev.remodded.regradle.ReGradleModulesConfigurationImpl
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.project.getProjectSuffix
import dev.remodded.regradle.project.includeInJar
import dev.remodded.regradle.project.isBuildTarget
import dev.remodded.regradle.project.needsShadow
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.*
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

abstract class ModulePlugin(
    val moduleType: ModuleType,
) : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        println("Applying ReGradle module plugin to ${project.name} (${this@ModulePlugin.moduleType})")

        gradle.extensions.getByType<ReGradleModulesConfigurationImpl>().createModuleExtension(project)

        apply<MavenPublishPlugin>()
        apply<KotlinPluginWrapper>()
        apply<ShadowPlugin>()
//todo: dokka
//
//        apply<DokkaPlugin>()

        val props = project.getPluginProps()

        group = props.group
        version = props.version
        description = props.description

//todo: dokka
//
//        val dokkaOutputDir = project.layout.buildDirectory.get().dir("dokka")
//
//        val javadocJar by project.tasks.registering(Jar::class) {
//            dependsOn(tasks.dokkaHtml)
//            archiveClassifier.set("javadoc")
//            archiveAppendix.set(project.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
//            archiveBaseName.set(props.name)
//            from(dokkaOutputDir)
//        }

        val sourceJar by tasks.registering(Jar::class) {
            archiveClassifier.set("sources")
            archiveAppendix.set(getProjectSuffix())
            archiveBaseName.set(props.name)
            from(sourceSets.main.get().allSource)
        }
//
        val javaVersion = JavaVersion.VERSION_21

        kotlin {
            jvmToolchain(javaVersion.ordinal + 1)
            compilerOptions {
                freeCompilerArgs = listOf("-Xjvm-default=all")
            }
        }

        java {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        tasks {
//            dokkaHtml {
//                outputDirectory.set(file(dokkaOutputDir))
//            }

            assemble {
                dependsOn(shadowJar)
            }

            jar {
                if (needsShadow()) {
                    archiveClassifier.set("clean")
                }
            }

            shadowJar {
                if (!needsShadow()) {
                    enabled = false
                }
                archiveClassifier.set("")
                archiveAppendix.set(project.getProjectSuffix())
                archiveBaseName.set(props.name)

                dependencies {
                    include(project::includeInJar)
                }

                if (isBuildTarget())
                    destinationDirectory.set(rootProject.layout.buildDirectory.get().dir("libs"))
            }
        }


        repositories {
            mavenLocal()
            maven("https://repo.remodded.dev/repository/maven-public/")
            maven("https://repo.remodded.dev/repository/Mojang/")
        }

        dependencies {
            regradleConfiguration.getModuleProjectWithFallback(moduleType.dependency)?.let { add("api", it) }
        }

        publishing {
            publications {
                create<MavenPublication>(name) {
                    val usernameReModded: String? by project
                    val passwordReModded: String? by project

                    afterEvaluate {
                        from(components["java"])
                        groupId = props.group + "." + props.id
                        artifactId = props.name + "-" + project.getProjectSuffix()
//                        todo: dokka
//                        artifact(javadocJar.get())
                        artifact(sourceJar.get())
                    }
                    repositories {
                        maven {
                            name = "ReModded"
                            url = if (project.version.toString().contains("-SNAPSHOT"))
                                uri("https://repo.remodded.dev/repository/maven-snapshots/")
                            else
                                uri("https://repo.remodded.dev/repository/maven-releases/")

                            credentials {
                                username = usernameReModded
                                password = passwordReModded
                            }
                        }
                    }
                }
            }
        }
    }
}
