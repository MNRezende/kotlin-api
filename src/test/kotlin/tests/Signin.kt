package tests

import api.Base
import org.junit.jupiter.api.Test

class Signin : Base() {

    @Test
    fun testSingnin() {
        val token = getToken()
        println(token)
    }
    @Test
    fun testInvalidSingnin() {
        val token = getToken()
        println(specBaseToken())
    }

}