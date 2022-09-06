package api

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import pojo.PojoTransactions

class Transactions : Base() {

    private val TRANSACTIONS_PATH = "transacoes"
    private val TRANSACTIONS_ID_PATH = "{/idTrasacoes}"
    private val TRANSACTIONS_ID_PARAM = "idTransacoes"

    fun postTransactions(pojoTransactions: PojoTransactions) : Response {
        return Given {
            spec(specBaseToken())
            contentType(ContentType.JSON)
            body(pojoTransactions)
        }When {
            post(TRANSACTIONS_PATH)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }

    fun deleteTransactions(transactionsId:String) : Response {
        return Given {
            spec(specBaseToken())
            pathParam(TRANSACTIONS_ID_PARAM, transactionsId)
        }When {
            delete(TRANSACTIONS_PATH + TRANSACTIONS_ID_PATH)
        }Then {
            log().all()
        }Extract {
            response()
        }
    }
}