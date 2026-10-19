package edu.virginia.cs4720.footballscoresbr8wr

import retrofit2.http.GET
import retrofit2.http.Path

interface NcaaAPI {
    @GET("scoreboard/football/{division}/{year}/{week}")
    suspend fun getScoreboard(
        @Path("division") division: String,
        @Path("year") year: Int,
        @Path("week") week: Int,
    ): NcaaResponse
}