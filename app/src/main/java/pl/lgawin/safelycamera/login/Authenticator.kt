package pl.lgawin.safelycamera.login

interface Authenticator {

    fun checkPassword(password: String): Boolean
}
