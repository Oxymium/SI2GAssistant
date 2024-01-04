package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

    // GET: ALL
    fun getAllAnnouncements(): Flow<Result<List<Announcement>>>

}