package io.openliberty.guides.testing;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.provider.jsrjsonb.JsrJsonbProvider;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class LibertyContainer extends GenericContainer<LibertyContainer> {

    static final Logger LOGGER = LoggerFactory.getLogger(LibertyContainer.class);

    private String baseURL;
    

    public LibertyContainer(final String dockerImageName) {
        super(dockerImageName);
        waitingFor(Wait.forLogMessage("^.*CWWKF0011I.*$", 1)); // wait for smarter planet message by default
    }

    public <T> T createRestClient(Class<T> clazz, String applicationPath) {
        List<Class<?>> providers = new ArrayList<>();
        System.out.println("DEBUG: About to create the rest client");
        providers.add(JsrJsonbProvider.class);
        System.out.println("DEBUG: Finish creating the rest client");
        String urlPath = getBaseURL();
        if (applicationPath != null)
            urlPath += applicationPath;
        return JAXRSClientFactory.create(urlPath, clazz, providers);
    }

    public <T> T createRestClient(Class<T> clazz) {
        return createRestClient(clazz, null);
    }

    public String getBaseURL() throws IllegalStateException {
        if (baseURL != null)
            return baseURL;
        if (!this.isRunning())
            throw new IllegalStateException("Container must be running to determine hostname and port");
        baseURL = "http://" + this.getContainerIpAddress() + ':' + this.getFirstMappedPort();
        return baseURL;
    }

}