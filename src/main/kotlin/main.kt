fun main() {
    read().then(::parse).then(::validAddress).then(::notBlank).then(::send)
        .onSuccess { println("All good: $it") }
        .onFailure { println("Exception has been thrown $it") }
}

fun read(): Result<List<String>> = Result.success(listOf("", "", ""))

fun parse(inputs: List<String>): Result<Email> = Result.success(Email(to = inputs.first(), subject = inputs[1], body = inputs[2]))

fun send(email: Email): Result<Email> = Result.success(email)

data class Email(val to: String, val subject: String, val body: String)

fun validAddress(email: Email): Result<Email> =
        if (email.to.contains("@")) {
            Result.success(email)
        } else {
            Result.failure(Throwable("Invalid email"))
        }

fun notBlank(email: Email): Result<Email> =
        if (email.subject != "" && email.body != "") {
            Result.success(email)
        } else {
            Result.failure(Throwable("Empty subject or body"))
        }
infix fun <T,U> Result<T>.then(f: (T) -> Result<U>) =
        f(this.getOrThrow())