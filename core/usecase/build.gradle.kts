plugins {
    id("java-library")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //hilt
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.complier)
}