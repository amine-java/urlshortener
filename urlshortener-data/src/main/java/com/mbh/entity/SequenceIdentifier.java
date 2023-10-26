package com.mbh.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SequenceIdentifier {
    @Id
    @GeneratedValue(generator = "id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "id_seq",
            sequenceName = "id_seq",
            allocationSize = 1
    )
    private Long id;


    /**
     * Retrieves the next value of the id_seq sequence
     * @param entityManager
     * @return next val
     */
    public static Long nextVal(EntityManager entityManager){
        SequenceIdentifier identifier = new SequenceIdentifier();
        entityManager.persist(identifier);
        entityManager.flush();
        return identifier.getId();
    }
}
