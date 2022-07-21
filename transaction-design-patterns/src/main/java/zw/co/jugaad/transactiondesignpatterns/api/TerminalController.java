package zw.co.jugaad.transactiondesignpatterns.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.transactiondesignpatterns.config.KeyManagerConfiguration;
import zw.co.jugaad.transactiondesignpatterns.dto.response.TerminalResponseDto;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.DeviceDetail;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.LaunchResponseDto;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.TerminalRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.TerminalUpdateDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Terminal;
import zw.co.jugaad.transactiondesignpatterns.response.GenericResponse;
import zw.co.jugaad.transactiondesignpatterns.service.TerminalService;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;


@RestController
@RequestMapping("/api/v1/terminal")
@Slf4j
public class TerminalController {


    private final KeyManagerConfiguration keyManagerConfiguration;
    private final TerminalService terminalService;

    public TerminalController(KeyManagerConfiguration keyManagerConfiguration, TerminalService terminalService) {
        this.keyManagerConfiguration = keyManagerConfiguration;
        this.terminalService = terminalService;
    }

    @PostMapping
    public ApiResponse registerTerminal(@RequestBody TerminalRequestDto requestDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        var terminal = terminalService.save(requestDto);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new TerminalResponseDto(terminal.getTerminalId(), terminal.getImei(), terminal.getTerminalType().name(), terminal.getStatus().name(), terminal.getServiceStation().getStationName()));
    }
    
    @GetMapping("/imei")
    public ApiResponse getTerminalByImei(@RequestParam("imei") String imei) {
        var terminal = terminalService.getTerminalByImei(imei);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new TerminalResponseDto(terminal.getTerminalId(), terminal.getImei(), terminal.getTerminalType().name(), terminal.getStatus().name(), terminal.getServiceStation().getStationName()));
    }

    @GetMapping("/terminalId")
    public ApiResponse getTerminalBTerminalId(@RequestParam("terminalId") String terminalId) {
        var terminal = terminalService.getTerminalByImei(terminalId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new TerminalResponseDto(terminal.getTerminalId(), terminal.getImei(), terminal.getTerminalType().name(), terminal.getStatus().name(), terminal.getServiceStation().getStationName()));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Terminal>> getTerminal(@PathVariable("page") int page,
                                                      @PathVariable("size") int size) {
        return ResponseEntity.ok(terminalService.getAllTerminals(PageRequest.of(page, size)));
    }

    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editTerminal(@RequestParam("terminalId") String terminalId,
                                                        @RequestBody TerminalUpdateDto requestDto)  {
        terminalService.findByTerminalId(terminalId);
        terminalService.update(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @PutMapping("/transfer")
    public ResponseEntity<GenericResponse> transferTerminal(@RequestParam("terminalId") String terminalId,
                                                            @RequestBody TransferRequestDto requestDto) {

      terminalService.findByTerminalId(terminalId);
        terminalService.transfer(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteTerminal(@PathVariable("id") Long id) {
        terminalService.deleteTerminal(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

    @GetMapping("/{service-station-id}/{page}/{size}")
    public ResponseEntity<Page<Terminal>> getTerminalsByServiceStationId(@PathVariable("service-station-id") Long serviceStationId,
                                                                         @PathVariable("page") int page,
                                                                         @PathVariable("size") int size) {

        return ResponseEntity.ok(terminalService.findAllByServiceStation_Id(serviceStationId, PageRequest.of(page, size)));
    }


    @GetMapping("/launch/{imei}")
    public ApiResponse terminalLaunchRequest(@PathVariable("imei") String imei) throws Exception {
        String key = keyManagerConfiguration.getZMKKey();
        log.info("{} device requesting info and got this key: {}", imei, key);
        try {
            var deviceDetail = (DeviceDetail) terminalService.getTerminalByImei(imei);
            LaunchResponseDto launchResponseDto = new LaunchResponseDto();
            launchResponseDto.setKey(key);
            launchResponseDto.setDeviceDetail(deviceDetail);
            return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                    new TerminalResponseDto(deviceDetail.getMerchantId(),deviceDetail.getTerminalId(), deviceDetail.getDeviceType(), deviceDetail.getDeviceStatus(),deviceDetail.getMerchant()));        } catch (Exception ex) {
            throw new AccountNotFoundException("Device not registered. Please contact the CAP10");
        }

    }
}