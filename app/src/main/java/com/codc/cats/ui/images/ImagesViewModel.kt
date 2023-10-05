package com.codc.cats.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.codc.cats.data.mappers.toDomainModel
import com.codc.cats.data.model.Image
import com.codc.cats.data.source.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private var firstTimeLoad = true

    var images = retrieveImages()

    fun retrieveImages(): Flow<PagingData<Image>> {
        if (firstTimeLoad) {
            firstTimeLoad = false
            return imageRepository.getImages().map { pagingData ->
                pagingData.map { imageEntity ->
                    imageEntity.toDomainModel()
                }
            }.cachedIn(viewModelScope)
        }
        return images
    }
}
