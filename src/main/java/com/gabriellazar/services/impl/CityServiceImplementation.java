package com.gabriellazar.services.impl;

import com.gabriellazar.exception.InvalidDataException;
import com.gabriellazar.models.City;
import com.gabriellazar.repositories.CityRepository;
import com.gabriellazar.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityServiceImplementation implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public boolean isValidCity(String city) {
     return cityRepository.findByCityName(city).isPresent();

    }

    @Override
    public double getPriceByCity(String cityName) {
       City city = cityRepository.findByCityName(cityName).orElseThrow(
               () -> new InvalidDataException("City not found with this name :: " + cityName));
       return city.getPrice();
    }

    @Override
    public double getStateTaxByCity(String cityName) {
        City city = cityRepository.findByCityName(cityName).orElseThrow(
                () -> new InvalidDataException("City not found with this name :: " + cityName));
        return city.getState().getStateTax();
    }

    @Override
    public String getStateByCity(String cityName) {
        City city = cityRepository.findByCityName(cityName).orElseThrow(
                () -> new InvalidDataException("City not found with this name :: " + cityName));
        return city.getState().getStateAbv();
    }

    @Override
    public List<String> getCities() {
        return cityRepository.findAll().stream()
                .map(city -> city.getCityName() + " $" + String.format("%.2f",city.getPrice()))
                .sorted()
                .collect(Collectors.toList());
    }


}
