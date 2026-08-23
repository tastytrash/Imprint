plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = when (prop("deps.minecraft")) {
				"1.21.7" -> "[1.21.6,1.21.7]"
				"26.1.2" -> "[26.1,26.1.2]"
				"26.2" -> "[26.2,)"
				else -> prop("deps.minecraft")
			}
		}
		required("neoforge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

neoForge {
	version = prop("deps.neoforge")
	accessTransformers.from(rootProject.file("src/main/resources/aw/${stonecutter.current.version}.cfg"))
	validateAccessTransformers = true

	if (hasProperty("deps.parchment")) parchment {
		val (mc, ver) = prop("deps.parchment").split(':')
		mappingsVersion = ver
		minecraftVersion = mc
	}

	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "NeoForge Client (${stonecutter.current.version})"
			programArgument("--username=Dev")
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "NeoForge Server (${stonecutter.current.version})"
		}
	}

	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
	sourceSets["main"].resources.srcDir("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
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
	implementation("me.shedaniel.cloth:cloth-config-neoforge:${prop("deps.cloth_config")}")
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}

tasks.register<Copy>("copyToBuildAll") {
	dependsOn("jar")
	val buildAllDir = file("${rootProject.projectDir}/versions-build")
	from(tasks.named<Jar>("jar").get().archiveFile)
	into(buildAllDir)
}

tasks.build {
	dependsOn("copyToBuildAll")
}
