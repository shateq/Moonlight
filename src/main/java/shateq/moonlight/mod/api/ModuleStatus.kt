package shateq.moonlight.mod.api

/**
 * Module orchestration states
 */
enum class ModuleStatus(@get:JvmName("mark") val mark: String, @get:JvmName("legend") val legend: String) {
    BUILT("🔵", "Wbudowane"),
    ON("🟢", "Włączone"),
    WAITING("🟡", "Wyłączone"),
    OFF("🔴", "Niedostępne");

    companion object {
        val note: String

        init {
            val builder = StringBuilder()
            for (status in values()) {
                builder.append(status.mark).append(" - ").append(status.legend).append("\n")
            }
            note = builder.toString()
        }
    }
}
