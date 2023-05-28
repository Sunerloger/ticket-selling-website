package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart save(Cart cart);

    List<Cart> findByUserId(Long id);

    void deleteCartBySeatId(Long id);

    void deleteCartById(Long id);

    Cart findCartBySeatIdAndUserId(Long seatId, Long userId);

}

