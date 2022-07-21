package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.jugaad.fudzocommons.util.OtherUtils;
import zw.co.jugaad.transactiondesignpatterns.dto.owner.OwnerRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.owner.OwnerUpdateDto;
import zw.co.jugaad.transactiondesignpatterns.enums.Role;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Owner;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.OwnerRepository;
import zw.co.jugaad.transactiondesignpatterns.service.OwnerService;
import zw.co.jugaad.transactiondesignpatterns.service.UserService;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserService userService;


    @Override
    public Owner save(OwnerRequestDto requestDto) {

        var user = userService.getUserByUserId(requestDto.getUserId());
        extractUser(user.getMobileNumber());
        var owner = Owner.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmailAddress())
                .mobile(user.getMobileNumber())
                .companyName(requestDto.getCompanyName())
                .role(Role.OWNER)
                .userId(user.getUserId())
                .organisation(user.getOrganisation())
                .ownerId(OtherUtils.generateOwnerId())
                .build();
        return ownerRepository.save(owner);
    }

    @Override
    public void update(Owner owner) {
        var ownerUpdate  = findByOwnerId(owner.getOwnerId());

        owner.setCreatedDate(ownerUpdate.getCreatedDate());

        ownerRepository.save(owner);
    }

    @Override
    public Page<Owner> getAllOwners(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    @Override
    public Owner getOwner(Long id) {
        return ownerRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Organisation with id " + id + " does not exist")
        );
    }

    @Override
    public void deleteOwner(Long id) {
        var owner = getOwner(id);

        if (owner == null) throw new BusinessValidationException("Organisation with id " + id + " does not exist");
        ownerRepository.delete(owner);
    }

    @Override
    public Owner findOwnerByCompanyName(String companyName) {
        var owner = ownerRepository.findOwnerByCompanyName(companyName);

        if (owner.isEmpty())
            throw new BusinessValidationException("owner with company name: "+ companyName + " not found");

        return owner.get();
    }

    @Override
    public Owner findByOwnerId(String ownerId) {
        var owner = ownerRepository.findByOwnerId(ownerId);

        if (owner.isEmpty()) throw new BusinessValidationException("owner.with.owner.id: " + ownerId + " not.found");

        return owner.get();
    }

    public boolean extractUser(String mobileNumber) {
        var user = userService.getUserByMobileNumber(mobileNumber);

        return user != null;
    }
}