//package by.vk.city;
//
//import io.micronaut.core.annotation.NonNull;
//import io.micronaut.data.model.query.builder.sql.Dialect;
//import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
//import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
//import io.micronaut.http.annotation.Controller;
//import io.micronaut.http.annotation.Get;
//import jakarta.inject.Singleton;
//import reactor.core.publisher.Flux;
//
//@Controller("/countries")
//public record CityApi(Service service) {
//
//  @Get("/id/cities")
//  public Flux<City> cities(Long id) {
//    return service.cities(id);
//  }
//}
//
//@Singleton
//record Service(Repository repository) {
//
//  Flux<City> cities(Long id) {
//    return Flux.from(repository.findByCountry(id));
//  }
//
//}
//
//@R2dbcRepository(dialect = Dialect.POSTGRES)
//interface Repository extends ReactiveStreamsCrudRepository<City, Long> {
//
//  @NonNull
//  Flux<City> findByCountry(Long id);
//}