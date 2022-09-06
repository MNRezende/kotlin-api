package factories

import pojo.PojoRegister

class RegisterFactory {
    var registerpojo = PojoRegister()

    fun registerSuccess() : PojoRegister {
        registerpojo.email = "reinkmakoi@gmail.com.br"
        registerpojo.senha = "123456"
        return registerpojo
    }

    fun userNotFoundRegister() : PojoRegister {
        registerpojo.email = "reinkmakoi@gmail.com"
        registerpojo.senha = "123456"
        return registerpojo
    }

    fun registerUserUnsuccess () : PojoRegister {
        val registerpojo = registerSuccess()
        registerpojo.senha = null
        return registerpojo
    }
}