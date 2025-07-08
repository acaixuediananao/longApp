package com.newangle.healthy.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * userId:治疗用户id
 * userName:治疗用户名称
 * type:治疗类型 脱毛/美白
 * part:治疗部位 手臂/腿部
 * duration:治疗参数 时长
 * energy:治疗参数 能量大小
 * frequency:治疗参数：频率
 * time:治疗日期时间
 * nurse:护理师
 */

@Entity
data class TreatmentPlanInfo(@PrimaryKey(autoGenerate = true) val uid: Int = 0,
                             @ColumnInfo(name = "userId") val userId:String,
                             @ColumnInfo(name = "userName") val userName : String ?,
                             @ColumnInfo(name = "type") val type : Int,
                             @ColumnInfo(name = "part") val part:String,
                             @ColumnInfo(name = "nurse") val nurse:String,
                             @ColumnInfo(name = "duration") val duration:Long,
                             @ColumnInfo(name = "energy") val energy:Long,
                             @ColumnInfo(name = "frequency") val frequency:Long,
                             @ColumnInfo(name = "time") val time:String,
    )
