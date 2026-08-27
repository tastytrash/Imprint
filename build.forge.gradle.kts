plugins {
	id("mod-platform")
	id("net.neoforged.moddev.legacyforge")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed < "1.21.11") {
		replace("Identifier", "ResourceLocation")
		replace("identifier()", "location()")
	}
}

platform {
	loader = "forge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = prop("deps.minecraft")
		}
		required("forge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

legacyForge {
	version = "${prop("deps.minecraft")}-${prop("deps.forge")}"

	validateAccessTransformers = true

	accessTransformers.from(
		rootProject.file("src/main/resources/aw/${sc.current.version}.cfg")
	)

	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "Forge Client (${sc.current.version})"
			programArgument("--username=Dev")
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "Forge Server (${sc.current.version})"
		}
	}

	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
}

repositories {
	mavenCentral()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	maven {
		name = "Cloth Config"
		url = uri("https://maven.shedaniel.me/")
	}
}

dependencies {
	annotationProcessor("org.spongepowered:mixin:${libs.versions.mixin.get()}:processor")
	modImplementation("me.shedaniel.cloth:cloth-config-forge:${prop("deps.cloth_config")}")
}

sourceSets {
	main {
		resources.srcDir(
			"${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated"
		)
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}

afterEvaluate {
	val jarTaskName = if (tasks.findByName("remapJar") != null) "remapJar" else "jar"

	tasks.register<Copy>("copyToBuildAll") {
		dependsOn(jarTaskName)
		val buildAllDir = file("${rootProject.projectDir}/versions-build")
		from(tasks.named(jarTaskName))
		into(buildAllDir)
	}
}

tasks.build {
	dependsOn("copyToBuildAll")
}

tasks.withType<Javadoc>().configureEach {
	options.encoding = "UTF-8"
	(options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

