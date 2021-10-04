package testCase.security.rest.dto;

import javax.validation.constraints.NotNull;

public class WeatherDataDto {

   public WeatherDataDto() {
   }

   @NotNull
   private String location;

   @NotNull
   private String timeStamp;

   @NotNull
   private String weatherCondition;

   @NotNull
   private Double temperature;


   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getTimeStamp() {
      return timeStamp;
   }

   public void setTimeStamp(String timeStamp) {
      this.timeStamp = timeStamp;
   }

   public String getWeatherCondition() {
      return weatherCondition;
   }

   public void setWeatherCondition(String weatherCondition) {
      this.weatherCondition = weatherCondition;
   }

   public Double getTemperature() {
      return temperature;
   }

   public void setTemperature(Double temperature) {
      this.temperature = temperature;
   }
}
