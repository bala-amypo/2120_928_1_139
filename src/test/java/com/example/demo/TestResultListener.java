package com.example.demo;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestResultListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Test Started: " + result.getMethod().getMethodName(), true);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        String message = result.getMethod().getMethodName() + " - PASS";
        System.out.println(message);
        Reporter.log(message, true);
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String message = result.getMethod().getMethodName() + " - FAIL";
        System.out.println(message);
        Reporter.log(message, true);
        if (result.getThrowable() != null) {
            Reporter.log("Error: " + result.getThrowable().getMessage(), true);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String message = result.getMethod().getMethodName() + " - SKIP";
        System.out.println(message);
        Reporter.log(message, true);
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - FAILED BUT WITHIN SUCCESS PERCENTAGE");
    }
}