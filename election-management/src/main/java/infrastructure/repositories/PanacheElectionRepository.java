package infrastructure.repositories;


import infrastructure.repositories.entities.Election;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

public class PanacheElectionRepository implements PanacheRepository<Election> {

}
