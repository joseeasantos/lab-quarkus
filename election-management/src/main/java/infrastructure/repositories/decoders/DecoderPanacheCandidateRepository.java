package infrastructure.repositories.decoders;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastructure.repositories.PanacheCandidateRepository;
import infrastructure.repositories.PanacheElectionCandidateRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class DecoderPanacheCandidateRepository implements CandidateRepository {

    private final PanacheCandidateRepository panacheCandidateRepository;

    public DecoderPanacheCandidateRepository(PanacheCandidateRepository panacheCandidateRepository) {
        this.panacheCandidateRepository = panacheCandidateRepository;
    }

    @Override
    public void save(List<Candidate> candidates) {
        Panache.withTransaction(()->{
            candidates.forEach(candidate -> panacheCandidateRepository.persist(infrastructure.repositories.entities.Candidate.fromDomain(candidate)));
            return null;
        });
    }

    @Override
    public void save(Candidate candidate) {
        panacheCandidateRepository.persist(infrastructure.repositories.entities.Candidate.fromDomain(candidate));
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        return null;
    }

    @Override
    public List<Candidate> findAll() {
        return CandidateRepository.super.findAll();
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return CandidateRepository.super.findById(id);
    }


    private Predicate[] conditions(CandidateQuery query,
                                   CriteriaBuilder cb,
                                   Root<infrastructure.repositories.entities.Candidate> root) {
        return Stream.of(query.ids().map(id -> cb.in(root.get("id")).value(id)),
                        query.name().map(name -> cb.or(cb.like(cb.lower(root.get("familyName")), name.toLowerCase() + "%"),
                                cb.like(cb.lower(root.get("givenName")), name.toLowerCase() + "%"))))
                .flatMap(Optional::stream)
                .toArray(Predicate[]::new);
    }
}
