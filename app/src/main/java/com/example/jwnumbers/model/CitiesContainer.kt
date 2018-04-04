package com.example.jwnumbers.model

data class CitiesContainer(var cities: MutableList<CityDTO> = mutableListOf(), var currentCity: CityDTO = CityDTO(),
                           var cityNames: MutableList<String> = mutableListOf()) {

    fun getCityByName(name: String): CityDTO? {
        cities.forEach { currentCity ->
            if (name == currentCity.name) {
                return currentCity
            }
        }
        return null
    }

    fun getNumberByCity(cityName: String, number: String): NumberDTO? =
            cities.firstOrNull { city -> city.name == cityName }?.numbers?.firstOrNull { currentNumber -> currentNumber.number == number }

    fun clear() {
        cities.clear()
        currentCity = CityDTO()
        cityNames.clear()
    }
}