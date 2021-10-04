package testCase.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import testCase.security.model.WeatherData;

public interface WeatherDataRepository extends PagingAndSortingRepository<WeatherData,String>,
   JpaSpecificationExecutor<WeatherData> {

   Page<WeatherData> findWeatherDataByLocation(String location, Pageable pageable);
}
