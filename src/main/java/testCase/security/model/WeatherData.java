package testCase.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WEATHER_DATA")
public class WeatherData {

   @Id
   @Column(name = "ID")
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   private String id;

   @Column(name = "LOCATION", length = 50)
   @NotNull
   private String location;

   @Column(name = "TIMESTAMP", length = 50)
   @NotNull
   private String timeStamp;

   @Column(name = "WEATHER_CONDITION", length = 10)
   @NotNull
   private String weatherCondition;

   @Column(name = "TEMPERATURE")
   @NotNull
   private Double temperature;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

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
