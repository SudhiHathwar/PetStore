package com.swagger.tests.configurations;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.EventPublishingTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@TestExecutionListeners({ServletTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, EventPublishingTestExecutionListener.class})
public abstract class AbstractTestNGSpringContextTests implements IHookable, ApplicationContextAware {

    private static final Object lock = new Object();
    private static boolean initialised = false;
    private final TestContextManager testContextManager = new TestContextManager(this.getClass());
    @Nullable
    protected ApplicationContext applicationContext;
    @Nullable
    private Throwable testException;

    public AbstractTestNGSpringContextTests() {
    }

    public final void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @BeforeClass(
            alwaysRun = true
    )
    protected void springTestContextBeforeTestClass() throws Exception {
    }

    @BeforeSuite(
            alwaysRun = true
    )
    public void springTestContextBeforeSuite() throws Exception {
        this.testContextManager.beforeTestClass();
        this.testContextManager.prepareTestInstance(this);
    }

    @BeforeSuite(
            alwaysRun = true,
            dependsOnMethods = {"springTestContextBeforeSuite"}
    )
    public void springTestContextBeforeSuiteInstance() throws Exception {
        this.testContextManager.prepareTestInstance(this);
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("service", "Swagger")
                        .put("version", "v3")
                        .put("url", "http://localhost:8080")
                        .build());
    }

    @BeforeClass(
            alwaysRun = true,
            dependsOnMethods = {"springTestContextBeforeTestClass"}
    )
    protected void springTestContextPrepareTestInstance() throws Exception {
        this.testContextManager.prepareTestInstance(this);
    }

    @BeforeMethod(
            alwaysRun = true

    )
    protected void springTestContextBeforeTestMethod(Method testMethod) throws Exception {
        this.testContextManager.beforeTestMethod(this, testMethod);
    }

    @SneakyThrows
    public void run(IHookCallBack callBack, ITestResult testResult) {
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        boolean beforeCallbacksExecuted = false;

        try {
            this.testContextManager.beforeTestExecution(this, testMethod);
            beforeCallbacksExecuted = true;
        } catch (Throwable var6) {
            this.testException = var6;
        }

        if (beforeCallbacksExecuted) {
            callBack.runTestMethod(testResult);
            this.testException = this.getTestResultException(testResult);
        }

        try {
            this.testContextManager.afterTestExecution(this, testMethod, this.testException);
        } catch (Throwable var7) {
            if (this.testException == null) {
                this.testException = var7;
            }
        }

        if (this.testException != null) {
            this.throwAsUncheckedException(this.testException);
        }

    }

    @AfterMethod(
            alwaysRun = true
    )
    protected void springTestContextAfterTestMethod(Method testMethod) throws Exception {
        try {
            this.testContextManager.afterTestMethod(this, testMethod, this.testException);
        } finally {
            this.testException = null;
        }

    }

    @AfterClass(
            alwaysRun = true
    )
    protected void springTestContextAfterTestClass() throws Exception {
        this.testContextManager.afterTestClass();
    }

    private Throwable getTestResultException(ITestResult testResult) {
        Throwable testResultException = testResult.getThrowable();
        if (testResultException instanceof InvocationTargetException) {
            testResultException = ((InvocationTargetException) testResultException).getCause();
        }

        return testResultException;
    }

    private RuntimeException throwAsUncheckedException(Throwable t) throws Throwable {
        this.throwAs(t);
        throw new IllegalStateException(t);
    }

    private <T extends Throwable> void throwAs(Throwable t) throws Throwable {
        throw t;
    }
}
