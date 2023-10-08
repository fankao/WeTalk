## Generating the public/private keys
```bash
keytool -genkey -alias "jwt-sign-key" -keyalg RSA -keystore jwtkeystore.jks -keysize 4096
```