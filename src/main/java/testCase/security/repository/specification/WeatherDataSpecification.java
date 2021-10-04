package testCase.security.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import testCase.security.model.WeatherData;
import testCase.security.model.WeatherData_;

public class WeatherDataSpecification {

   public static Specification<WeatherData> locationEquals(String location) {
      return (root, query, cb) ->
         cb.equal(
            root.get(WeatherData_.location), location);
   }

   public static Specification<WeatherData> timeEquals(String timeStamp) {
      return (root, query, cb) ->
         cb.equal(
            root.get(WeatherData_.timeStamp), timeStamp);
   }

   public static Specification<WeatherData> timeBetween(String time1, String time2) {
      return (root, query, cb) ->
            cb.between(root.get(WeatherData_.timeStamp),time1,time2);
   }

   public static Specification<WeatherData> conditionEquals(String condition) {
      return (root, query, cb) ->
         cb.equal(
            root.get(WeatherData_.weatherCondition), condition);
   }

   public static Specification<WeatherData> temperatureEquals(String temperature) {
      return (root, query, cb) ->
         cb.equal(
            root.get(WeatherData_.temperature), temperature);
   }

}
