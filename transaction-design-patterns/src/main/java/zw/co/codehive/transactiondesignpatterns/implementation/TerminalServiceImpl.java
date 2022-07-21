package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.codehive.commons.util.OtherUtils;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.terminal.TerminalRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.terminal.TerminalUpdateDto;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalStatus;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.ServiceStation;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Terminal;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.TerminalRepository;
import zw.co.codehive.transactiondesignpatterns.service.ServiceStationService;
import zw.co.codehive.transactiondesignpatterns.service.TerminalService;


@Service
@Slf4j
public class TerminalServiceImpl implements TerminalService {

    private final ServiceStationService serviceStationService;
    private final TerminalRepository repository;

    public TerminalServiceImpl(ServiceStationService serviceStationService, TerminalRepository repository) {
        this.serviceStationService = serviceStationService;
        this.repository = repository;
    }


    @Override
    public Terminal save(TerminalRequestDto requestDto) {
        ServiceStation serviceStation = serviceStationService.findByStationName(requestDto.getServiceStationName());
        Terminal terminal = Terminal.builder()
                .terminalId(OtherUtils.generateTerminalId())
                .terminalType(requestDto.getTerminalType())
                .imei(requestDto.getImei())
                .status(TerminalStatus.ONLINE)
                .serviceStation(serviceStation)
                .build();
        return repository.save(terminal);
    }

    @Override
    public void update(TerminalUpdateDto terminalUpdateDto) {
        ServiceStation serviceStation = serviceStationService.findByStationName(terminalUpdateDto.getServiceStationName());
        Terminal terminal = Terminal.builder()
                .terminalType(terminalUpdateDto.getTerminalType())
                .status(terminalUpdateDto.getTerminalStatus())
                .serviceStation(serviceStation)
                .build();

        repository.save(terminal);
    }

    @Override
    public Page<Terminal> getAllTerminals(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Terminal getTerminal(Long id) {
        return getOne(id);
    }

    @Override
    public void deleteTerminal(Long id) {
        var terminal = getTerminal(id);

        if (terminal == null) throw new BusinessValidationException("Terminal with id " + id + " does not exist");

        repository.delete(terminal);
    }

    @Override
    public Terminal getOne(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Terminal with id " + id + " does not exist")
        );
    }

    @Override
    public void transfer(TransferRequestDto requestDto) {
        ServiceStation serviceStation = serviceStationService.getOne(requestDto.getServiceStationId());

        Terminal terminal = Terminal.builder()
                .serviceStation(serviceStation)
                .build();

        repository.save(terminal);
    }

    @Override
    public Page<Terminal> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable) {
        return repository.findAllByServiceStation_Id(serviceStationId, pageable);
    }

    @Override
    public Terminal getTerminalByImei(String imei) {
        return repository.findByImei(imei).orElseThrow(() -> new BusinessValidationException("Terminal not registered, please contact the system administrator"));
    }

    @Override
    public Terminal findByTerminalId(String terminalId) {
        var terminal = repository.findByTerminalId(terminalId);

        if (terminal.isEmpty())
            throw new BusinessValidationException("terminal.with.terminal.id: " + terminalId + " not.found");

        return terminal.get();
    }
}