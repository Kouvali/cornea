package org.kouv.cornea.math

import org.joml.*

public inline fun vector3f(block: Vector3f.() -> Unit = {}): Vector3f = Vector3f().apply(block)

public inline fun vector3f(d: Float, block: Vector3f.() -> Unit = {}): Vector3f = Vector3f(d).apply(block)

public inline fun vector3f(x: Float, y: Float, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    Vector3f(x, y, z).apply(block)

public inline fun vector3f(v: Vector3fc, block: Vector3f.() -> Unit = {}): Vector3f = Vector3f(v).apply(block)

public inline fun vector3f(v: Vector3dc, block: Vector3f.() -> Unit = {}): Vector3f = Vector3f(v).apply(block)

public inline fun vector3f(v: Vector3ic, block: Vector3f.() -> Unit = {}): Vector3f = Vector3f(v).apply(block)

public inline fun vector3f(v: Vector2fc, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    Vector3f(v, z).apply(block)

public inline fun vector3f(v: Vector2ic, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    Vector3f(v, z).apply(block)

public inline fun vector3f(xyz: FloatArray, block: Vector3f.() -> Unit = {}): Vector3f = Vector3f(xyz).apply(block)