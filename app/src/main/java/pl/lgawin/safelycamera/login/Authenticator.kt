package pl.lgawin.safelycamera.login

interface Authenticator {

    /**
     * Validates provided password returning security token or null if password is invalid
     */
    fun checkPassword(password: String): String?
}
