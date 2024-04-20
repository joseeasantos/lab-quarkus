package infrastructure.repositories.decoders;

import domain.*;
import infrastructure.repositories.PanacheElectionCandidateRepository;
import infrastructure.repositories.PanacheElectionRepository;
import infrastructure.repositories.entities.ElectionCandidate;
import io.quarkus.hibernate.reactive.panache.Panache;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class DecoderPanacheElectionRepository implements ElectionRepository {

    private final PanacheElectionRepository electionRepository;
    private final PanacheElectionCandidateRepository electionCandidateRepository;

    public DecoderPanacheElectionRepository(PanacheElectionRepository electionRepository,
                                            PanacheElectionCandidateRepository electionCandidateRepository){
        this.electionRepository = electionRepository;
        this.electionCandidateRepository = electionCandidateRepository;
    }


    @Override
    public void submit(Election election) {
        Panache.withTransaction(() ->{
            electionRepository.persist(infrastructure.repositories.entities.Election.fromDomain(election));


            election.votes()
                    .entrySet()
                    .stream()
                    .map(entry -> ElectionCandidate.fromDomain(election, entry.getKey(), entry.getValue()))
                    .forEach(electionCandidateRepository::persist);
            return null;
        });
    }

    @Override
    public List<Election> findAll() {
        return electionRepository.streamAll()
                .onItem().castTo(domain.Election.class)
                .collect().asList().await().indefinitely();

    }
}
