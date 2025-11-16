package com.LibBib.spevn.data.firebase

import android.app.Application
import com.LibBib.spevn.R
import com.LibBib.spevn.domain.remoteDB.NetworkChecker
import com.LibBib.spevn.domain.remoteDB.RemoteDatabaseRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val application: Application,
    private val networkChecker: NetworkChecker
) : RemoteDatabaseRepository {

    private var resultFlow = MutableSharedFlow<Int>()


    override fun getActualVersionUseCase(): SharedFlow<Int> {
        val database = Firebase.database.reference

        val versionRef = database.child(FIREBASE_REALTIME_DATABASE_VERSION_NAME)

        versionRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = Integer.valueOf(snapshot.value.toString())
                runBlocking {
                    resultFlow.emit(result)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return resultFlow.asSharedFlow()
    }

    override fun downloadSong(songName: String): Flow<Result<File>> = flow {
        try {
            if (!networkChecker.isConnected())
                emit(Result.failure(Exception(application.getString(R.string.check_internet_connection))))
            val fileName = songName + MP3_FORMAT

            val cachedFile = File(application.cacheDir, fileName)

            if (cachedFile.exists()) {
                emit(Result.success(cachedFile))
                return@flow
            }

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReference()
            val pathString = FIREBASE_STORAGE_PATH + fileName
            val pathReference = storageRef.child(pathString)
            if (fileExists(pathReference)) {
                val bytes = pathReference.getBytes(MAX_SIZE).await()

                cachedFile.writeBytes(bytes)
                emit(Result.success(cachedFile))

            } else {
                emit(Result.failure(Exception(application.getString(R.string.file_doesnt_exist_message))))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private suspend fun fileExists(reference: StorageReference): Boolean {
        return try {
            reference.metadata.await()
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val MP3_FORMAT = ".mp3"
        private const val FIREBASE_STORAGE_PATH = "songs/"
        private const val MAX_SIZE = 10000000L
        private const val FIREBASE_REALTIME_DATABASE_VERSION_NAME = "update"
    }
}