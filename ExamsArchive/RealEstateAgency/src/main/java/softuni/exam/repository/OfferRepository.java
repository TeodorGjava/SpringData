package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.enums.ApartmentType;

import java.util.List;

// TODO:
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByApartmentApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType apartmentType);

}
