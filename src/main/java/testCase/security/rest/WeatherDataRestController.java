package testCase.security.rest;


import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testCase.security.model.WeatherData;
import testCase.security.rest.dto.WeatherDataDto;
import testCase.security.service.WeatherDataService;

@RestController
@RequestMapping()
public class WeatherDataRestController {

   private final WeatherDataService weatherDataService;

   public WeatherDataRestController(
      WeatherDataService weatherDataService) {

      this.weatherDataService = weatherDataService;
   }

   @GetMapping("/weather")
   public Page<WeatherData> findAllDataByFilters(
      @RequestParam(name = "location", required = false, defaultValue = "") String location,
      @RequestParam(name = "time1", required = false, defaultValue = "") String time1,
      @RequestParam(name = "time2", required = false, defaultValue = "") String time2,
      @RequestParam(name = "condition", required = false, defaultValue = "") String condition,
      @RequestParam(name = "temperature", required = false, defaultValue = "") String temperature,
      @PageableDefault() Pageable pageable) {
      return this.weatherDataService.getWeatherDataByFilters(location,time1,time2,condition,temperature,pageable);
   }

   @GetMapping("/populate")
   public Object populateDatabase(){
      return weatherDataService.populateDatabase();
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PostMapping("/weather-add")
   public Object addWeatherData(@Valid @RequestBody WeatherDataDto weatherDataDto) {
      return this.weatherDataService.createWeatherData(weatherDataDto);
   }

}
