package zw.co.codehive.transactiondesignpatterns.projection;

public interface TerminalResultDto {

    Long getId();


    String getTerminalId();


    String getImei();


    String getTerminalType();


    String getStatus();

    Long getStationId();

    String getServiceStation();

}
