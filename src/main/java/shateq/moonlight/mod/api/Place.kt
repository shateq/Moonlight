package shateq.moonlight.mod.api

import net.dv8tion.jda.api.entities.Guild

data class Place(val guild: Guild) {
    override fun toString(): String {
        return "Place{${guild.name}, ${guild.idLong}}"
    }
}
