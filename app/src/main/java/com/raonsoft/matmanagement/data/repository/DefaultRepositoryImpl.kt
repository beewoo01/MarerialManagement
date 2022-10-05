package com.raonsoft.matmanagement.data.repository

import com.raonsoft.matmanagement.data.entity.SamplePhotoEntity
import com.raonsoft.matmanagement.data.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class DefaultRepositoryImpl(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : DefaultRepository {

    /*override suspend fun getComments(id: Int): Response<List<Post>> = apiService.getComments(id)

    override suspend fun getPosts(): Response<List<Post>> = apiService.getPosts()

    override suspend fun getPhotos(): Response<List<SamplePhotoEntity>> = apiService.getPhotos()*/


}