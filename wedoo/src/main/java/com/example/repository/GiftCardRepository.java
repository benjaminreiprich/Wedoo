package com.example.repository;

import com.example.model.GiftCard;
import com.example.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*@Repository
public class GiftRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String QUERY_GIFTCARD_BY_USER_ID = "SELECT * FROM GIFTCARD WHERE user_id = :userId";


    public GiftRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<GiftCard> getGiftCardsByUserId (Integer userId) {

        Query query = entityManager.createNamedQuery(QUERY_GIFTCARD_BY_USER_ID)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    public GiftCard addGiftCard (GiftCard giftCard) throws SaveGiftCardError {
        try {
            entityManager.merge(giftCard);
            return giftCard;
        } catch (Exception e) {
            throw new SaveGiftCardError("Une erreur est survenue lors de la sauvegarde de la giftcard");
        }
    }*/

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard,Integer> {

    List<GiftCard> findGiftCardByUserId(Integer userId);
}
