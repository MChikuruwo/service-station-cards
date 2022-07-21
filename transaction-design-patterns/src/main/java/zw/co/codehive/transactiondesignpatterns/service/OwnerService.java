package zw.co.codehive.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.codehive.transactiondesignpatterns.dto.owner.OwnerRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Owner;

public interface OwnerService {

    Owner save(OwnerRequestDto requestDto);

    void update(Owner owner);

    Page<Owner> getAllOwners(Pageable pageable);

    Owner getOwner(Long id);

    void deleteOwner(Long id);

    Owner findOwnerByCompanyName(String companyName);

    Owner findByOwnerId(String ownerId);
}