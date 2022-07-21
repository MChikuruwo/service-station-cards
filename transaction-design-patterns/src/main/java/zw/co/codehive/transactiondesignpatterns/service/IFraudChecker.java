package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.exception.FraudDetectedException;

interface IFraudChecker {
    void runChecks() throws FraudDetectedException;
}

