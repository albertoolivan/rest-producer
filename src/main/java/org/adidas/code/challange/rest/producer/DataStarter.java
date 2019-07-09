package org.adidas.code.challange.rest.producer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataStarter implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(DataStarter.class);

	public static final String COMMA_DELIMITER = ",";

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityDistanceRepository cityDistanceRepository;

	/**
	 * Insert data into database from initial files
	 * 
	 * resources/city_list.txt resources/ city_distance_list.txt
	 */
	@Override
	public void run(String... args) throws Exception {

		Map<String, City> mapCityLoaded = new HashMap<>();

		int countCityInserted = 0;
		int countCityLine = 0;
		List<List<String>> cityList = readFile("city_list.txt");
		for (List<String> line : cityList) {
			countCityLine++;
			if (line.size() >= 2) {
				City city = new City(line.get(0), line.get(1));
				mapCityLoaded.put(line.get(0), city);
				cityRepository.save(city);
				countCityInserted++;
				logger.debug("Save city: {}", city);
			} else {
				logger.warn("Line need 2 parts: {}", line);
			}
		}
		logger.info("Cities inserted: {} of {} lines.", countCityInserted, countCityLine);

		int countDistancesInserted = 0;
		int countDistanceLines = 0;
		List<List<String>> cityDistanceList = readFile("city_distance_list.txt");
		for (List<String> line : cityDistanceList) {
			countDistanceLines++;
			if (line.size() >= 4) {
				String id = line.get(0);
				String cityOriginId = line.get(1);
				String cityDestinationId = line.get(2);
				String distanceKmString = line.get(3);
				int distanceKm = Integer.parseInt(distanceKmString);
				City cityOrigin = mapCityLoaded.get(cityOriginId);
				City cityDestination = mapCityLoaded.get(cityDestinationId);
				if (cityOrigin != null && cityDestination != null) {
					CityDistance cityDistance = new CityDistance(id, cityOrigin, cityDestination, distanceKm);
					cityDistanceRepository.save(cityDistance);
					countDistancesInserted++;
				} else {
					logger.warn("City id not exists! cityOriginId: {} cityDestinationId: {}",
							cityOriginId, cityDestinationId);
				}

			} else {
				logger.warn("Line need 4 parts: {}", line);
			}
		}
		logger.info("Distances inserted {} of {} lines ", countDistancesInserted, countDistanceLines);
	}

	/**
	 * Read a comma separator file and return each line/element into
	 * List<List<String>>
	 * 
	 * Ignore lines starting with # and clean quotes
	 * 
	 * @param fileName
	 * @return List<List<String>>
	 */
	private List<List<String>> readFile(String fileName) {
		List<List<String>> records = new ArrayList<>();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#") || !line.contains(COMMA_DELIMITER)) {
					// ignore comment/empty line
				} else {
					// clean "
					line = line.replace("\"", "");
					String[] values = line.split(COMMA_DELIMITER);
					records.add(Arrays.asList(values));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return records;
	}
}
