package zw.co.jugaad.transactiondesignpatterns.service;

import zw.co.jugaad.transactiondesignpatterns.exception.FraudDetectedException;

interface IFraudChecker {
    void runChecks() throws FraudDetectedException;
}

