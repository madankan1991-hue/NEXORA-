package com.nexora.app.data.remote

import com.nexora.app.data.local.AnalysisEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AnalysisRequestDto(val description: String, val objective: String)

interface ApiService {
    @GET("/api/v1/analyses")
    suspend fun getAnalyses(): Response<List<AnalysisEntity>>

    @POST("/api/v1/analyses")
    suspend fun createAnalysis(@Body request: AnalysisRequestDto): Response<AnalysisEntity>
}
