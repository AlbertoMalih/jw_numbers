package com.example.jw_numbers.model

data class CitiesContainer(var cities: MutableList<CityDTO> = mutableListOf(), var currentCity: CityDTO = CityDTO(),
                           var cityNames: MutableList<String> = mutableListOf()) {
    fun clear() {
        cities.clear()
        currentCity = CityDTO()
        cityNames.clear()
    }
}