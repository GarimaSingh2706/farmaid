package com.examly.springapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.StateDistrict;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class LocationServiceImpl implements LocationService {

    private List<StateDistrict> stateDistrictList;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/data/StatesAPI.json");

        JsonNode root = mapper.readTree(is);
        JsonNode statesNode = root.get("states");

        TypeReference<List<StateDistrict>> typeRef = new TypeReference<>() {
        };
        stateDistrictList = mapper.readValue(statesNode.traverse(), typeRef);
    }

    public List<StateDistrict> getAllStatesAndDistricts() {
        return stateDistrictList;
    }

    public List<String> getDistrictsByState(String stateName) {
        return stateDistrictList.stream()
                .filter(s -> s.getState().equalsIgnoreCase(stateName))
                .map(StateDistrict::getDistricts)
                .findFirst()
                .orElse(Collections.emptyList());
    }

}
