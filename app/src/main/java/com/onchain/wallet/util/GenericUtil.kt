package com.onchain.wallet.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

object GenericUtil {

    fun getClassType(obj: Any, destClz: KClass<*>) = getClassType(obj::class.java, destClz.java)

    private fun getClassType(clz: Class<*>, destClz: Class<*>): KClass<*>? {
        var c = clz
        var genType: Type?

        while (c.superclass != null) {
            genType = c.genericSuperclass
            if (genType !is ParameterizedType) {
                c = c.superclass as Class<*>
                continue
            } else {
                val types = genType.actualTypeArguments
                for (type in types) {
                    val tClz = type as Class<*>
                    return if (destClz.isAssignableFrom(tClz)) {
                        type.kotlin
                    } else {
                        continue
                    }
                }
                break
            }
        }

        return null
    }

}
