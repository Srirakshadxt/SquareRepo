package com.sriraksha.squarerepo.di

import com.sriraksha.squarerepo.data.api.SquareApi
import com.sriraksha.squarerepo.data.repository.SquareRepositoryImpl
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSquareApi(retrofit: Retrofit): SquareApi {
        return retrofit.create(SquareApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSquareRepository(
        squareRepositoryImpl: SquareRepositoryImpl
    ): SquareRepository = squareRepositoryImpl


}