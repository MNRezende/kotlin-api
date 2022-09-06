package factories

import pojo.PojoTransactions

class TransactionsFactory {

    private val pojoTransactions = PojoTransactions()

    fun successBody(idConta: String) : PojoTransactions {
        pojoTransactions.conta_id = idConta
        pojoTransactions.descricao = "descricao"
        pojoTransactions.envolvido = "Reink Makoi"
        pojoTransactions.tipo = "REC"
        pojoTransactions.data_transacao = "06/09/2022"
        pojoTransactions.data_pagamento = "06/09/2022"
        pojoTransactions.valor = 10f
        pojoTransactions.status = true
        return pojoTransactions
    }

    fun bodyWithoutAccountId(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.conta_id = null
        return body
    }
    fun bodyWithoudDescription(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.descricao = null
        return body
    }
    fun bodyWithoutInvolved(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.envolvido = null
        return body
    }
    fun bodyWithoutDateTransaction(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.data_transacao = null
        return body
    }
    fun bodyWithoutPaymentDay(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.data_pagamento = null
        return body
    }
    fun bodyWithoutType(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.tipo = null
        return body
    }
    fun bodyWithoutValue(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.valor = null
        return body
    }
    fun bodyWithoutStatus(idConta: String) : PojoTransactions {
        val body = successBody(idConta)
        body.status = null
        return body
    }
}