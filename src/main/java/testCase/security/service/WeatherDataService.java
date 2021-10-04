package testCase.security.service;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testCase.security.model.WeatherData;
import java.util.Objects;
import testCase.security.repository.WeatherDataRepository;
import testCase.security.repository.specification.WeatherDataSpecification;
import testCase.security.rest.dto.WeatherDataDto;

@Service
@Transactional
public class WeatherDataService {

   private final WeatherDataRepository weatherDataRepository;

   public WeatherDataService(
      WeatherDataRepository weatherDataRepository) {
      this.weatherDataRepository = weatherDataRepository;
   }

   /**
    * Method to get weather information, information can be filtered by parameters such as
    * condition,time,time range,location and temperature. If only a single time is given then the
    * information is filtered to find the exact matching time, if two time variables are given then
    * it is treated as a time range and weather data between the specified time range is retrieved.
    * Both admin and end users have access to this method.
    */

   @Transactional(readOnly = true)
   public Page<WeatherData> getWeatherDataByFilters(String location, String time1, String time2,
      String condition, String temperature, Pageable pageable) {

      Specification<WeatherData> spec = null;

      if (!Objects.equals(location, "")) {
         spec = Specification.where(
            WeatherDataSpecification.locationEquals(location));
      }

      if (!Objects.equals(time2, "") && !Objects.equals(time2, "")) {
         Specification<WeatherData> timeBetweenSpec = WeatherDataSpecification.timeBetween(time1,
            time2);
         spec = spec == null ? timeBetweenSpec : spec.and(timeBetweenSpec);
      } else if (!Objects.equals(time1, "") && Objects.equals(time2, "")) {
         Specification<WeatherData> time1Spec = WeatherDataSpecification.timeEquals(time1);
         spec = spec == null ? time1Spec : spec.and(time1Spec);
      } else if (!Objects.equals(time2, "") && Objects.equals(time1, "")) {
         Specification<WeatherData> time2Spec = WeatherDataSpecification.timeEquals(time2);
         spec = spec == null ? time2Spec : spec.and(time2Spec);
      }

      if (!Objects.equals(condition, "")) {
         Specification<WeatherData> conditionSpec = WeatherDataSpecification.conditionEquals(
            condition);
         spec = spec == null ? conditionSpec : spec.and(conditionSpec);
      }

      if (!Objects.equals(temperature, "")) {
         Specification<WeatherData> temperatureSpec = WeatherDataSpecification.temperatureEquals(
            temperature);
         spec = spec == null ? temperatureSpec : spec.and(temperatureSpec);
      }

      return weatherDataRepository.findAll(spec, pageable);
   }


   /**
    * Method to populate the database. As default it reads from the csv file that has 1 million
    * entries generated, it can be switched with the 10 million one, no need for authorization with
    * jwt token to call this method endpoint.
    */
   @Transactional
   public Object populateDatabase() {
      List<WeatherData> weatherDataList = new ArrayList<>();
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("generated_weather_data.csv");
      CsvParserSettings setting = new CsvParserSettings();
      setting.setHeaderExtractionEnabled(true);
      CsvParser parser = new CsvParser(setting);
      List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
      parseAllRecords.forEach(record -> {
         String[] values = record.getValues();
         String[] parsedValues = values[0].split("\\|");
         WeatherData weatherData = new WeatherData();
         weatherData.setLocation(parsedValues[0]);
         weatherData.setTimeStamp(parsedValues[2]);
         weatherData.setWeatherCondition(parsedValues[3]);
         weatherData.setTemperature(Double.valueOf(parsedValues[4]));
         weatherDataList.add(weatherData);
      });
      weatherDataRepository.saveAll(weatherDataList);
      return "Database populated succesfully";
   }

   /**
    * Method to add new weather information to the database, only accesible by admin users
    */
   @Transactional
   public Object createWeatherData(WeatherDataDto weatherDataDto) {
      WeatherData weatherData = new WeatherData();
      weatherData.setLocation(weatherDataDto.getLocation());
      weatherData.setTimeStamp(weatherDataDto.getTimeStamp());
      weatherData.setWeatherCondition(weatherDataDto.getWeatherCondition());
      weatherData.setTemperature(weatherDataDto.getTemperature());
      weatherDataRepository.save(weatherData);
      return "Weather data created succesfully.";


   }

}
