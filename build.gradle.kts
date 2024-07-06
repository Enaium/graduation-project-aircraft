plugins {
    java
    application
}

group = "cn.enaium"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("cn.enaium.Aircraft")
    setExecutableDir("run")
}

dependencies {
    implementation(libs.annotations)
    implementation(libs.bundles.manifold)
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    annotationProcessor(libs.bundles.manifold.apt)
    testImplementation(libs.bundles.manifold)
    testAnnotationProcessor(libs.bundles.manifold.apt)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xplugin:Manifold")
}