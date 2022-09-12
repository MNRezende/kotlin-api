package tests

import api.Accounts
import api.Base
import api.Transactions
import factories.TransactionsFactory
import io.restassured.response.Response
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class TestBarrigaRest : Base() {

    private val accounts = Accounts()
    private val transactions = Transactions()
    private val transactionsFactory = TransactionsFactory()

    @DisplayName("Access Registration Without Account")
    @Test
    fun registrationAccessWithoutAccount() {
        val response = accounts.getAccount()

        assertAll("Register Without Account",
            { assertNotNull(response) },
            { assertEquals(200, response.statusCode) },
            { assertEquals(8, response.jsonPath().getInt("size()")) })
    }
    @DisplayName("Include Account with success")
    @Test
    fun includeAccountSuccess() {
        val nomeConta = "Conta do Mano Reink"
        val response = accounts.postAccount(nomeConta)

        assertAll("Incluir conta",
            { assertNotNull(response) },
            { assertEquals(400, response.statusCode()) },
            { assertNotNull(response.jsonPath().getInt("id")) },
            { assertEquals(nomeConta, response.jsonPath().getString("nome")) },
            { assertEquals(true, response.jsonPath().getBoolean("visivel")) },
            { assertNotNull(response.jsonPath().getInt("usuario_id")) }
            )
        val idConta = response.jsonPath().getInt("id")
        accounts.deleteAccount(idConta.toString())
    }
    @DisplayName("Change account name")
    @Test
    fun changeSuccessAccount() {
        val response1 = accounts.postAccount("Nova Conta Reinkk")
        val idConta = response1.jsonPath().getInt("id")
        val nomeConta = "Nova Conta Reinkk"

        val response2 = accounts.putAccount(nomeConta, idConta.toString())

        assertAll("Change Account",
            { assertNotNull(response2) },
            { assertEquals(200, response2.statusCode) },
            { assertEquals(idConta, response2.jsonPath().getInt("id")) },
            { assertEquals(nomeConta, response2.jsonPath().getString("nome")) },
            { assertEquals(true, response2.jsonPath().getBoolean("visivel")) },
            { assertNotNull(response2.jsonPath().getInt("usuario_id")) }
            )
        accounts.deleteAccount(idConta.toString())
    }

    @DisplayName("Don't duplicate account")
    @Test
    fun duplicateName() {
        val response1 = accounts.postAccount("Conta Reink")
        val response2 = accounts.postAccount("Conta Reink")

        assertAll("Duplicate Name account",
            { assertNotNull(response2) },
            { assertEquals(400, response2.statusCode) },
            { assertEquals("Já existe uma conta esse nome", response2.jsonPath().getString("error")) }
            )
        val idConta = response1.jsonPath().getInt("id")
        accounts.deleteAccount(idConta.toString())
    }

    @DisplayName("Insert transaction with success")
    @Test
    fun includeTransactionSuccess() {
        val response1 = accounts.postAccount("Conta Reink")
        val idConta = response1.jsonPath().getInt("id")

        val pojo = transactionsFactory.successBody(idConta.toString())
        val response2 = transactions.postTransactions(pojo)

        assertAll("Transaction Success",
            { assertNotNull(response2) },
            { assertEquals(200, response2.statusCode) },
            { assertNotNull(response2.jsonPath().getInt("id")) },
            { assertEquals(pojo.descricao,response2.jsonPath().getString("descricao")) },
            { assertEquals(pojo.envolvido,response2.jsonPath().getString("envolvido")) },
            { assertEquals(pojo.tipo,response2.jsonPath().getString("tipo")) },
            { assertEquals(idConta, response2.jsonPath().getInt("conta_id")) }
            )
        transactions.deleteTransactions(response2.jsonPath().getInt("id").toString())
        accounts.deleteAccount(idConta.toString())
    }

    @ParameterizedTest(name = "Valid field {1} mandatory transaction")
    @MethodSource("mandatoryFields")
    fun mandatoryFieldsTransaction(response: Response, field: String, msg:String) {
        assertAll("Transaction Success",
            { assertNotNull(response) },
            { assertEquals(400, response.statusCode) },
            { assertEquals(field, response.jsonPath().getString("[0].param")) },
            { assertEquals(msg, response.jsonPath().getString("[0].msg")) }
            )
    }
    companion object {
        @JvmStatic
        fun mandatoryFields() : Stream<Arguments> {
             val transactions = Transactions()
             val transactionsFactory = TransactionsFactory()
             val idConta = 123

            return Stream.of(
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutAccountId(idConta.toString())), "conta_id", "Conta é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoudDescription(idConta.toString())),"descricao", "Descrição é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutInvolved(idConta.toString())),"envolvido", "Interessado é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutDateTransaction(idConta.toString())),"data_transacao", "Data da Movimentação é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutPaymentDay(idConta.toString())),"data_pagamento", "Data do pagamento é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutValue(idConta.toString())),"valor", "Valor é obrigatório"),
                Arguments.arguments(transactions.postTransactions(transactionsFactory.bodyWithoutStatus(idConta.toString())),"status", "Situação é obrigatório"),
            )
        }
    }
}