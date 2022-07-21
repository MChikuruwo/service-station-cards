package zw.co.codehive.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.owner.OwnerRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.owner.OwnerUpdateDto;
import zw.co.codehive.transactiondesignpatterns.dto.response.OwnerResponseDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Owner;
import zw.co.codehive.transactiondesignpatterns.response.GenericResponse;
import zw.co.codehive.transactiondesignpatterns.service.OwnerService;

@RestController
@RequestMapping("/api/v1/owner")
@Slf4j
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ApiResponse registerOwner(@RequestBody OwnerRequestDto requestDto) throws IllegalAccessException, JsonProcessingException {
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
       var owner =  ownerService.save(requestDto);
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new OwnerResponseDto(owner.getOwnerId(), owner.getFirstName(), owner.getLastName(), owner.getMobile(), owner.getCompanyName(),owner.getOrganisation()));
    }

    @GetMapping("/{owner-id}")
    public ApiResponse getOwner(@RequestParam("ownerId") String ownerId) {
        var owner = ownerService.findByOwnerId(ownerId);

        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new OwnerResponseDto(owner.getOwnerId(), owner.getFirstName(), owner.getLastName(), owner.getMobile(), owner.getCompanyName(),owner.getOrganisation()));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Owner>> getOwners(@PathVariable("page") int page,
                                                 @PathVariable("size") int size) {
        return ResponseEntity.ok(ownerService.getAllOwners(PageRequest.of(page, size)));
    }

    @GetMapping("/company-name")
    public ApiResponse getOwnerByCompanyName(@RequestParam("companyName") String companyName) {
       var owner =   ownerService.findOwnerByCompanyName(companyName);
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new OwnerResponseDto(owner.getOwnerId(), owner.getFirstName(), owner.getLastName(), owner.getMobile(), owner.getCompanyName(),owner.getOrganisation()));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editOwner(@RequestParam("ownerId") String ownerId,
                                                     @RequestBody OwnerUpdateDto updateDto) {
        var owner = ownerService.findByOwnerId(ownerId);
        owner.setCompanyName(updateDto.getCompanyName());
        ownerService.update(owner);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteOwner(@PathVariable("id") Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }
}