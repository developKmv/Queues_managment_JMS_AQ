package ru.app;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import ru.app.Jms_func.ReadFile;
import ru.app.Jms_func.SampleJmsMessageSender;
import ru.app.config.AppConf;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {

    private static final Logger log = (Logger) LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        // System.out.println("hello");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConf.class);

        SampleJmsMessageSender sjs = ctx.getBean("sampleJmsMessageSender", SampleJmsMessageSender.class);
        ReadFile rf = ctx.getBean(ReadFile.class);

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {

                switch (scanner.next()) {
                    case ("sendAll"):
                        Set<String> fileList = rf.listFilesUsingJavaIO();

                        fileList.forEach(file -> {
                            try {
                                sjs.simpleSend(rf.read_file(file));
                                log.info(String.format("send file name: %s",file));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //System.out.println(file);
                        });
                        break;
                    case ("close"):
                        return;
                }

            }
        }
        ;
        log.info("return");
        ctx.close();
    }
}
