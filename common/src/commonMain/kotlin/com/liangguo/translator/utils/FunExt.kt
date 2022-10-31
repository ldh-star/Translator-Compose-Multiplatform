package com.liangguo.translator.utils

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2022/10/22 18:04
 * 邮箱: 2637614077@qq.com
 */
/**
 * 当一个列表不为空的时候会执行的代码
 */
fun <T> List<T>?.ifNotNullOrEmpty(block: (List<T>) -> Unit) {
    if (!this.isNullOrEmpty()) {
        block(this)
    }
}

/**
 * 将一个集合中的元素进行随机打乱
 * 返回一个新的集合回去
 */
inline fun <reified T> List<T>.randomArrangement(): List<T> {
    val array = toTypedArray()
    val n = size
    for (i in 0 until n) {
        array.swap(Random.nextInt(n), Random.nextInt(n))
    }
    return array.toList()
}

/**
 * 交换集合中两个实体的位置
 */
inline fun <reified T> Array<T>.swap(a: Int, b: Int) {
    if (a < size && b < size) {
        val t = get(a)
        set(a, get(b))
        set(b, t)
    } else throw IllegalArgumentException("输入的两个值不能越界")
}

/**
 * 对字符串进行MD5加密
 */
val String.MD5: String
    get() {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(toByteArray()))
        return bigInt.toString(16)
    }

fun Any?.println() {
    println(this)
}


/**
 * 将 Unicode 解码成 String
 */
fun decodeFromUnicode(encodeText: String): String {
    fun decode1(unicode: String) = unicode.toInt(16).toChar()
    val unicodes = encodeText.split("\\u")
        .map { if (it.isNotBlank()) decode1(it) else null }.filterNotNull()
    return String(unicodes.toCharArray())
}


/**
 * 如果是 T 类型，则强转成T后返回
 */
inline fun <reified T> Any?.asType(): T? {
    return if (this is T) this else null
}

/**
 * 以一个float的范围对float进行约束
 */
fun Float.constraintIn(range: ClosedFloatingPointRange<Float>) =
    if (this < range.start) range.start else if (this > range.endInclusive) range.endInclusive else this
