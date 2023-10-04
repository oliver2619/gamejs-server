package pluto

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault
import io.smallrye.config.WithName

@ConfigMapping(prefix = "gamejs")
interface GamejsConfig {

    @WithName("lobby")
    fun lobby(): LobbyConfig?

    @WithName("session")
    fun session(): SessionConfig?

    @WithName("player")
    fun player(): PlayerConfig?

    interface LobbyConfig {

        @WithName("maximum")
        fun maximum(): Int?
    }

    interface SessionConfig {

        @WithName("maximum")
        fun maximum(): Int?
    }

    interface PlayerConfig {

        @WithName("maximum")
        fun maximum(): Int?
    }
}