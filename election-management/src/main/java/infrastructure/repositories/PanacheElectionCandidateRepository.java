package infrastructure.repositories;


import infrastructure.repositories.entities.Election;
import infrastructure.repositories.entities.ElectionCandidate;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

public class PanacheElectionCandidateRepository implements PanacheRepository<ElectionCandidate> {

}
