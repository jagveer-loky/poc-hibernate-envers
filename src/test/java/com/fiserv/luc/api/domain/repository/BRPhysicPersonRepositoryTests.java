package com.fiserv.luc.api.domain.repository;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository;
import com.fiserv.luc.api.infrastructure.database.audited.revision.FiservRevision;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.data.history.Revisions;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BRPhysicPersonRepositoryTests {

    /**
     *
     */
    private static final String USERNAME = "f5wwb41";

    /**
     *
     */
    private static final String ANOTHER_USERNAME = "f5wwb42";

    /**
     *
     */
    @Autowired
    UserDetailsManager userDetailsManager;

    /**
     *
     */
    @Autowired
    BRPhysicPersonRepository physicPersonRepository;

    /***
     *
     */
    @PostConstruct
    public void postConstruct() {
        if (!userDetailsManager.userExists(USERNAME))
            userDetailsManager.createUser(User.builder().username(USERNAME).password("password").authorities("ROLE").build());
        if (!userDetailsManager.userExists(ANOTHER_USERNAME))
            userDetailsManager.createUser(User.builder().username(ANOTHER_USERNAME).password("password").authorities("ROLE").build());
    }

    /**
     *
     */
    @Test
    @Sql({
            "/dataset/truncate-all-tables.sql"
    })
    public void insertBRPhysicPersonMustPass() {

        final boolean pep = false;
        final String fatherName = "Israel";
        final String motherName = "Valdina";

        final String name = "Emanuel";
        final LocalDate birthDate = LocalDate.now().minusYears(32);

        final BRPhysicPerson physicPerson = this.physicPersonRepository.save(new BRPhysicPerson(pep, fatherName, motherName, name, birthDate));

        assertThat(physicPerson).isNotNull();
        assertThat(physicPerson.getId()).isNotNull();
        assertThat(physicPerson.getPep()).isNotNull();
        assertThat(physicPerson.getFatherName()).isNotNull();
        assertThat(physicPerson.getMotherName()).isNotNull();

        assertThat(physicPerson.getName()).isNotNull();
        assertThat(physicPerson.getBirthDate()).isNotNull();

        assertThat(pep).isEqualTo(physicPerson.getPep());
        assertThat(fatherName).isEqualTo(physicPerson.getFatherName());
        assertThat(motherName).isEqualTo(physicPerson.getMotherName());

        assertThat(name).isEqualTo(physicPerson.getName());
        assertThat(birthDate).isEqualTo(physicPerson.getBirthDate());

    }

    /**
     *
     */
    @Test
    @WithUserDetails(USERNAME)
    @Sql({
            "/dataset/truncate-all-tables.sql"
    })
    void historyFromBRPhysicPersonMustPass() {

        final BRPhysicPerson john = new BRPhysicPerson();
        john.setName("John");
        john.setFatherName("Father name of John");
        john.setMotherName("Mother name of John");
        john.setPep(false);
        john.setBirthDate(LocalDate.now().minusYears(32));

        // create
        final BRPhysicPerson saved = physicPersonRepository.save(john);
        assertThat(saved).isNotNull();

        saved.setName("Jonny");

        // update
        final BRPhysicPerson updated = physicPersonRepository.save(saved);
        assertThat(updated).isNotNull();

        // delete
        physicPersonRepository.delete(updated);

        //// REVISION ASSERTS FROM HERE ////

        final Revisions<Long, BRPhysicPerson> revisions = physicPersonRepository.findRevisions(updated.getId());

        final Iterator<Revision<Long, BRPhysicPerson>> revisionIterator = revisions.iterator();

        checkNextRevision(revisionIterator, "John", RevisionType.INSERT, USERNAME);
        checkNextRevision(revisionIterator, "Jonny", RevisionType.UPDATE, USERNAME);
        checkNextRevision(revisionIterator, null, RevisionType.DELETE, USERNAME);

        assertThat(revisionIterator.hasNext()).isFalse();

    }

    /**
     *
     */
    @Test
    @WithUserDetails(ANOTHER_USERNAME)
    @Sql({
            "/dataset/truncate-all-tables.sql"
    })
    void historyFromBRPhysicPersonMustPassWithAnotherUser() {

        final BRPhysicPerson maria = new BRPhysicPerson();
        maria.setName("Mary");
        maria.setFatherName("Father name of John");
        maria.setMotherName("Mother name of John");
        maria.setPep(false);
        maria.setBirthDate(LocalDate.now().minusYears(32));

        // create
        final BRPhysicPerson saved = physicPersonRepository.save(maria);
        assertThat(saved).isNotNull();

        saved.setName("Maria");

        // update
        final BRPhysicPerson updated = physicPersonRepository.save(saved);
        assertThat(updated).isNotNull();

        updated.setFatherName("Father name of Maria");
        updated.setMotherName("Mother name of Maria");

        final BRPhysicPerson updatedDadsOfMaria = physicPersonRepository.save(updated);
        assertThat(updatedDadsOfMaria).isNotNull();

        //// REVISIONS ASSERTS FROM HERE ////

        final Revisions<Long, BRPhysicPerson> revisions = physicPersonRepository.findRevisions(updatedDadsOfMaria.getId());

        final Iterator<Revision<Long, BRPhysicPerson>> revisionIterator = revisions.iterator();

        checkNextRevision(revisionIterator, "Mary", RevisionType.INSERT, ANOTHER_USERNAME);
        checkNextRevision(revisionIterator, "Maria", RevisionType.UPDATE, ANOTHER_USERNAME);
        checkNextRevision(revisionIterator, "Maria", RevisionType.UPDATE, ANOTHER_USERNAME);

        assertThat(revisionIterator.hasNext()).isFalse();

    }

    /**
     * @param revisionIterator the iterator to be tested.
     * @param name             the expected name of the Person referenced by the Revision.
     * @param revisionType     the type of the revision denoting if it represents an insert, update or delete.
     * @param username         User that make the action
     */
    private void checkNextRevision(final Iterator<Revision<Long, BRPhysicPerson>> revisionIterator, final String name, final RevisionType revisionType, final String username) {

        assertThat(revisionIterator.hasNext()).isTrue();
        final Revision<Long, BRPhysicPerson> revision = revisionIterator.next();
        assertThat(revision.getEntity().getName()).isEqualTo(name);
        assertThat(revision.getMetadata().getRevisionType()).isEqualTo(revisionType);
        assertThat(((FiservRevision) revision.getMetadata().getDelegate()).getUsername()).isEqualTo(username.toUpperCase());

    }

}
