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

    private fun bodyAccount(accountName: String) : String {
        return "{\n" +
                " \"nome\" : \"$accountName\"\n"
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

    fun postAccount(accountName: String) : Response {
        return Given{
            spec(specBaseToken())
            contentType(ContentType.JSON)
            body(bodyAccount(accountName))
        }When {
            post(PATH_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun putAccount(accountName: String, idAccount:String) : Response {
        return Given {
            spec(specBaseToken())
            pathParam(PATH_ID_ACCOUNT_PARAM, idAccount)
            contentType(ContentType.JSON)
            body(bodyAccount(accountName))
        }When{
            put(PATH_ACCOUNT+PATH_ID_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun deleteAccount(idAccount: String) : Response {
        return Given {
            spec(specBaseToken())
            pathParam(PATH_ID_ACCOUNT_PARAM, idAccount)
        }When {
            delete(PATH_ACCOUNT+PATH_ID_ACCOUNT)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }
}