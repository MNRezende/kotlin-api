package api

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.TestInstance
import java.net.URL

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Base {

    private val URL = "https://barrigarest.wcaquino.me"
    private val HEADER_KEY = "Authorization"
    private val HEADER_VALUE = "JWT "
    private val LOGIN_KEY_EMAIL = "email"
    private val LOGIN_VALUE_EMAIL = "reinkmakoi@gmail.com.br"
    private val LOGIN_KEY_SENHA = "senha"
    private val LOGIN_VALUE_SENHA = "123456"
    private val PATH_SIGNIN = "signin"
    private val JSON_PATH_TOKEN = "token"

    fun specBase () : RequestSpecification{
        return RequestSpecBuilder()
            .setBaseUri(URL)
            .build()
    }

    fun specBaseToken() : RequestSpecification {
        val token = getToken()
        return RequestSpecBuilder()
            .addRequestSpecification(specBase())
            .addHeader(HEADER_KEY, HEADER_VALUE + token)
            .build()
    }

    fun getToken() : String {
        var loginJson: HashMap<String, String> = HashMap()
        loginJson[LOGIN_KEY_EMAIL] = LOGIN_VALUE_EMAIL
        loginJson[LOGIN_KEY_SENHA] = LOGIN_VALUE_SENHA

        return Given {
            spec(specBase())
            contentType(ContentType.JSON)
            body(loginJson)
        } When {
            post(PATH_SIGNIN)
        } Then {
            log().all()
        } Extract {
            response().jsonPath().getString(JSON_PATH_TOKEN)
        }
    }


}