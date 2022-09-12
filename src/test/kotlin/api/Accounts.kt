package api

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response

class Accounts : Base() {

    private val PATH_ACCOUNT = "contas"
    private val PATH_ID_ACCOUNT = "/{idConta}"
    private val PATH_ID_ACCOUNT_PARAM = "idConta"

    private fun bodyAccount(nomeConta: String) : String {
        return "{\n" +
                " \"nome\" : \"$nomeConta\"\n"
                "}"
    }

    fun getAccount() : Response {
        return Given {
            spec(specBaseToken())
        }When {
            get(PATH_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun postAccount(nomeConta: String) : Response {
        return Given{
            spec(specBaseToken())
            contentType(ContentType.JSON)
            body(bodyAccount(nomeConta))
        }When {
            post(PATH_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun putAccount(nomeConta: String, idConta:String) : Response {
        return Given {
            spec(specBaseToken())
            pathParam(PATH_ID_ACCOUNT_PARAM, idConta)
            contentType(ContentType.JSON)
            body(bodyAccount(nomeConta))
        }When{
            put(PATH_ACCOUNT+PATH_ID_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun deleteAccount(idConta: String) : Response {
        return Given {
            spec(specBaseToken())
            pathParam(PATH_ID_ACCOUNT_PARAM, idConta)
        }When {
            delete(PATH_ACCOUNT+PATH_ID_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }
}