package pl.lgawin.safelycamera.security

interface TokenHolder {
    var token: String?
}

object CryptoConfiguration : TokenHolder {

    const val algorithm = "PBEWITHSHA256AND256BITAES-CBC-BC"

    override var token: String? = null
}
