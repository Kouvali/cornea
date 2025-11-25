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
import net.minecraft.block.Blocks
import org.kouv.cornea.elements.*
import org.kouv.cornea.holders.*
import org.kouv.cornea.math.rotateLocalYDegrees

fun create() = elementHolder {
    blockDisplayElement {
        val baseTransformation = transformation {
            translateLocal(-0.5f, -0.5f, -0.5f)
            scaleLocal(0.75f)
        }

        onTick {
            transformation(from = baseTransformation) {
                rotateLocalYDegrees(tickCount * 11.25f)
            }

            startInterpolation(1)
        }

        blockState = Blocks.CAKE.defaultState
    }
}
```