package com.nexora.app.data.repository

import com.nexora.app.data.local.AnalysisDao
import com.nexora.app.data.local.AnalysisEntity
import com.nexora.app.data.remote.ApiService
import com.nexora.app.data.remote.AnalysisRequestDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NexoraRepository @Inject constructor(
    private val apiService: ApiService,
    private val analysisDao: AnalysisDao
) {
    val allAnalyses: Flow<List<AnalysisEntity>> = analysisDao.getAllAnalyses()

    suspend fun refreshAnalyses() {
        try {
            val response = apiService.getAnalyses()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach { analysisDao.insertAnalysis(it) }
            }
        } catch (e: Exception) {
            // در حالت آفلاین خطا ندهد
        }
    }

    suspend fun createAnalysis(description: String, objective: String) {
        try {
            val response = apiService.createAnalysis(AnalysisRequestDto(description, objective))
            if (response.isSuccessful && response.body() != null) {
                analysisDao.insertAnalysis(response.body()!!)
            }
        } catch (e: Exception) {
            // مدیریت خطای شبکه
        }
    }
}
