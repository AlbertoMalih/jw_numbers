package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.data.db.DbHelper
import com.example.jwnumbers.data.db.OnInsertedDescription
import com.example.jwnumbers.data.repository.NumbersRepositoryHelper
import com.example.jwnumbers.data.preferences.PreferencesHelper
import com.example.jwnumbers.model.CitiesContainer

class SplashAppInteractor(private val numbersRepositoryHelper: NumbersRepositoryHelper, val preferencesHelper: PreferencesHelper,
                          private val dbHelper: DbHelper, private val citiesContainer: CitiesContainer) : SplashInteractor {

    override fun doCloseDb() {
        dbHelper.close()
    }

    override fun doRefreshDb() {
        dbHelper.refreshStore()
    }


    override fun doReceiveNumbersCall(listener: OnReceivedNumbers, storeId: String) {
        numbersRepositoryHelper.installNumbersFromCurrentStore(object : OnReceivedNumbers() {
            override fun onSuchReceived() {
                numbersIsNotValid(listener)

                preferencesHelper.setStoreId(storeId)
                preferencesHelper.markEnabledAutoConnectToStore()

                sortCities()

                insertDescriptionsFromDbIntoNumbers(listener)
            }
        }, storeId)
    }

    override fun isEnabledAutoConnectToStore(): Boolean = preferencesHelper.isEnabledAutoConnectToStore()

    override fun getStoreId(): String = preferencesHelper.getStoreId()


    private fun numbersIsNotValid(listener: OnReceivedNumbers): Boolean {
        return if (citiesContainer.cities.isEmpty()) {
            listener.onFailReceived()
            true
        } else false
    }

    private fun insertDescriptionsFromDbIntoNumbers(listener: OnReceivedNumbers) {
        dbHelper.setDescriptionsFromDb(citiesContainer, object : OnInsertedDescription {
            override fun onSuchInserted() {
                listener.onSuchReceived()
            }

            override fun onFailInserted() {
                listener.onFailReceived()
            }
        })
    }

    private fun sortCities() {
        citiesContainer.cities.sortBy { it.name }
        citiesContainer.cityNames = citiesContainer.cities.mapTo(mutableListOf()) { city -> city.name }
        citiesContainer.cityNames.sort()
        citiesContainer.cities.forEach { itCity -> itCity.numbers.sortBy { itNumber -> itNumber.number } }
    }
}


abstract class OnReceivedNumbers {
    abstract fun onSuchReceived()
    open fun onFailReceived() {}
}