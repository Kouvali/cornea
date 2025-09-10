package org.kouv.cornea.math

import org.joml.*

public inline fun matrix4f(block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f().apply(block)

public inline fun matrix4f(from: Matrix3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4x3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4dc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)