package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.TerminalRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.terminal.TerminalUpdateDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Terminal;

public interface TerminalService {

    Terminal save(TerminalRequestDto requestDto);

    void update(TerminalUpdateDto terminalUpdateDto);

    Page<Terminal> getAllTerminals(Pageable pageable);

    Terminal getTerminal(Long id);

    void deleteTerminal(Long id);

    Terminal getOne(Long id);

    void transfer(TransferRequestDto requestDto);

    Page<Terminal> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);

    Terminal getTerminalByImei(String imei);

    Terminal findByTerminalId(String terminalId);
}