# Android Cleaning Service

> This project is still in development phase

A library to clean up your Android project

## Include in your project

In your `build.gradle`

```
repositories {
  ...
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.esafirm:android-cleaning-service:Tag'
}
```

## Library Usage

```kotlin
// Specify config such as dry run
val config = CleaningServiceConfig(dryRun = false)

// Specify removers
val removers = listOf(
  DrawableFileRemover(),
  StringXmlRemover()
)

// List all your module directories
val moduleSrcDirs = listOf(
   "/Users/esafirm/androidapp/app"
)

// Run the cleaning service
removers.map { it.remove(moduleSrcDirs, extension) }
```

## Runing Library Test

```
$ ./gradlew lib:test
```

## Add Plugin to Your Project

We haven't tried including the plugin with Jitpack, so currently
the tested way to use the plugin is via included build

Please check sample `settings.gradle` for the setup

## Plugin Usage

There are two tasks that you can run after applies the plugin

1. `./gradlew cleaningServiceAll`

This will run cleaning service to all of your projects path

2. `./gradlew <module>:cleaningService`

This will run cleaning service to that particular module and all modules that
affected by it

## License

This project is originally comes from [Konifar's Gradle Unused Resource Remover Plugin](https://github.com/konifar/gradle-unused-resources-remover-plugin) under Apache 2.0 License

This project make the code can be used outside Gradle's plugin and also change the programming language from Groovy to Kotlin

```
Copyright 2018 Yusuke Konishi
Modification Copyright 2021 Esa Firman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
