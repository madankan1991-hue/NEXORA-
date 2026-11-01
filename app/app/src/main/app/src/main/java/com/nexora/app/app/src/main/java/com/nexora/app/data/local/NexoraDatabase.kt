package com.nexora.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "analyses")
data class AnalysisEntity(
    @PrimaryKey val id: String,
    val description: String,
    val objective: String,
    val status: String,
    val createdAt: Long,
    val confidenceScore: Float,
    val decisionState: String
)

@Dao
interface AnalysisDao {
    @Query("SELECT * FROM analyses ORDER BY createdAt DESC")
    fun getAllAnalyses(): Flow<List<AnalysisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)

    @Query("DELETE FROM analyses WHERE id = :id")
    suspend fun deleteAnalysis(id: String)
}

@Database(entities = [AnalysisEntity::class], version = 1, exportSchema = false)
abstract class NexoraDatabase : RoomDatabase() {
    abstract fun analysisDao(): AnalysisDao
}
