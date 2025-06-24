package io.quarkiverse.embedded.postgresql.devui;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.inject.Inject;

import io.quarkus.devui.runtime.config.ConfigDescription;
import io.quarkus.devui.runtime.config.ConfigDescriptionBean;
import io.quarkus.runtime.LaunchMode;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static io.quarkus.runtime.LaunchMode.DEVELOPMENT;

public class EmbeddedPostgreSQLJsonRpcService {

    @Inject
    ConfigDescriptionBean configDescriptionBean;

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String jdbcUrl;

    public int getDatasourcePort() {
        final String port =  LaunchMode.current().equals(DEVELOPMENT)
?  jdbcUrl : configDescriptionBean.getAllConfig().stream()
                    .filter(c -> c.getName().equalsIgnoreCase("quarkus.datasource.jdbc.url")).findFirst().get().getConfigValue().getValue();
        // Define a regex pattern to match numbers
        Pattern pattern = Pattern.compile("\\d+");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(port);

        // Find and print all numbers in the input string
        while (matcher.find()) {
            String number = matcher.group();
            return Integer.parseInt(number);
        }
        return 0;
    }
}
