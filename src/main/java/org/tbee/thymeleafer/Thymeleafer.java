package org.tbee.thymeleafer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import picocli.CommandLine;

import java.io.*;
import java.lang.invoke.CallSite;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * TODO:
 * - graalvm?
 */
@CommandLine.Command(name = "thymeleafer", //
        description = "Uses the Thymeleaf template engine to process Thymeleaf templates from the commandline")
public class Thymeleafer implements Callable<Void> {

    static public void main(String[] args) throws IOException {
        int exitCode = new CommandLine(new Thymeleafer()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode); // Needed so the tests get a result
        }
    }

    @CommandLine.Parameters(index = "0", description = "The template file")
    private String templateFilename = null;

    @CommandLine.Option(names = {"-v", "--values"}, description = "The values (properties) file to be used in the template")
    private File valuesFile = null;

    @CommandLine.Option(names = {"-e", "--encoding"}, description = "The encoding used in the template file")
    private String encoding = "UTF-8";

    @CommandLine.Option(names = {"-o", "--output"}, description = "The file where to write the output")
    private File outputFile = null;

    @Override
    public Void call() throws Exception {
        boolean showTraceOutput = (valuesFile != null);

        // Read the values
        Properties properties = new Properties();
        if (valuesFile != null) {
            if (showTraceOutput) System.out.println("Loading " + valuesFile);
            properties.load(new FileReader(valuesFile));
        }

        // Resolve filenames
        var resolver = new FileTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(encoding);

        // Populate the values
        var context = new Context();
        for (Object key: properties.keySet()) {
            if (showTraceOutput) System.out.println("Setting " + key + "=" + properties.get(key));
            context.setVariable(key.toString(), properties.get(key));
        }

        // Run the engine
        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        var result = templateEngine.process(templateFilename, context);

        // Write the output
        if (outputFile == null) {
            System.out.println(result);
        }
        else {
            try (
                FileWriter fileWriter = new FileWriter(outputFile, Charset.forName(encoding));
            ){
                System.out.println("Writing to " + outputFile.getAbsolutePath());
                fileWriter.write(result);
            }
        }

        // Done
        return null;
    }
}
