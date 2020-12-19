package pl.lgawin.safelycamera.utils

fun String.byteArrayFromHexString() = this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
fun ByteArray.toHexString() = this.joinToString("") { String.format("%02X", (it.toInt() and 0xFF)) }
