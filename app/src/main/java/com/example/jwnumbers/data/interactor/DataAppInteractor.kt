package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.data.db.DbHelper
import com.example.jwnumbers.data.preferences.PreferencesHelper
import com.example.jwnumbers.data.repository.EmptyStoreException
import com.example.jwnumbers.data.repository.NumbersRepositoryHelper
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.schedulers.Schedulers

class DataAppInteractor(private val numbersRepositoryHelper: NumbersRepositoryHelper, private val preferencesHelper: PreferencesHelper,
                        private val dbHelper: DbHelper, private val citiesContainer: CitiesContainer): DataInteractor {

    override fun doDisableAutoConnectCall() {
        preferencesHelper.markDisableAutoConnectToStore()
    }

    override fun doWriteDescriptionIntoDbCall(number: NumberDTO) {
        dbHelper.setDescriptionToDB(number)
    }

    override fun doRefreshDb() {
        dbHelper.refreshStore(preferencesHelper.getStoreId())
    }

    override fun doReceiveNumbersCall(storeId: String): Completable =
            Completable.create { emitterReceiveNumbers ->
                numbersRepositoryHelper.installNumbersFromCurrentStore(storeId).observeOn(Schedulers.computation())
                        .subscribe({
                            numbersIsNotValid(emitterReceiveNumbers)

                            preferencesHelper.setStoreId(storeId)
                            preferencesHelper.markEnabledAutoConnectToStore()

                            sortCities()

                            dbHelper.refreshStore(storeId)
                            dbHelper.setDescriptionsFromDb(citiesContainer)

                            emitterReceiveNumbers.onComplete()

                        }, { error -> emitterReceiveNumbers.onError(error) })
            }

    override fun isEnabledAutoConnectToStore(): Boolean = preferencesHelper.isEnabledAutoConnectToStore()

    override fun getStoreId(): String = preferencesHelper.getStoreId()


    private fun numbersIsNotValid(emitter: CompletableEmitter): Boolean {
        return if (citiesContainer.cities.isEmpty()) {
            emitter.onError(EmptyStoreException())
            true
        } else false
    }

    private fun sortCities() {
        citiesContainer.cities.sortBy { it.name }
        citiesContainer.cityNames = citiesContainer.cities.mapTo(mutableListOf()) { city -> city.name }
        citiesContainer.cityNames.sort()
        citiesContainer.cities.forEach { itCity -> itCity.numbers.sortBy { itNumber -> itNumber.number } }
    }
}