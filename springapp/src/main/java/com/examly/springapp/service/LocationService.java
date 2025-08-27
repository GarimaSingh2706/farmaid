package com.examly.springapp.service;

import java.util.List;

public interface LocationService {

    List<?> getAllStatesAndDistricts();

    List<String> getDistrictsByState(String state);

}
