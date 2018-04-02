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

    fun getNumberByCity(cityName: String, id: String): NumberDTO =
            cities.first { city -> city.name == cityName }.numbers.first { currentNumber -> currentNumber.id == id }

    fun clear() {
        cities.clear()
        currentCity = CityDTO()
        cityNames.clear()
    }
}