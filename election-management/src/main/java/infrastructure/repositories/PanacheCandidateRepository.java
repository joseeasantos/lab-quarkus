package infrastructure.repositories;

import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastructure.repositories.entities.Candidate;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public class PanacheCandidateRepository implements PanacheRepository<Candidate>{

}
