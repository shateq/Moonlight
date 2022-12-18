package shateq.moonlight.mod

enum class ModuleStatus(@get:JvmName("mark") val mark: String, @get:JvmName("legend") val legend: String) {
    ON("🟢", "Włączone"),
    BUILT("🔵", "Wbudowane"),
    OFF("🔴", "Wyłaczone"),
    WAITING("🟡", "Oczekiwane"),
    SPECIAL("🟠", "Specjalne")
}
