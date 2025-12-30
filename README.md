# cornea

A library that provides Kotlin DSL and extension functions
for [polymer-virtual-entity](https://github.com/patbox/polymer). It allows you to write elements and element holders in
DSL style and use useful extension functions.

> Note: Until version 1.0.0, the API may change without prior notice.

## Gradle Setup

### Kotlin

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    modImplementation("io.github.kouvali:cornea:YOUR_VERSION")
}
```

### Groovy

```groovy
repositories {
    mavenCentral()
}

dependencies {
    modImplementation "io.github.kouvali:cornea:YOUR_VERSION"
}
```

## Usage

Here's a quick example:

```kotlin
fun create() = elementHolder {
    blockDisplayElement {
        transformation {
            translateLocal(-0.5f, -0.5f, -0.5f)
            scaleLocal(0.75f)
        }

        onTick {
            transform {
                rotateLocalY(11.25f.toRadians())
            }

            startInterpolation(1)
        }

        blockState = Blocks.CAKE.defaultState
    }
}
```