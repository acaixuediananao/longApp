package com.newangle.healthy.persistence.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.newangle.healthy.bean.TreatmentPlanInfo

@Dao
interface TreatmentDao {

    @Insert
    suspend fun insertUser(treatmentPlanInfo: TreatmentPlanInfo)

    @Query("SELECT * FROM user WHERE uid IS :userId ")
    suspend fun findTreatmentInfoByUserId(userId :Long) : List<TreatmentPlanInfo>
}