package ru.app.Jms_func;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.app.App;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class ReadFile {
    private static final Logger log = (Logger) LoggerFactory.getLogger(ReadFile.class);

    @Value("${readFile.pathFile}")
    private String pathFile;

    public Set<String> listFilesUsingJavaIO() {
        Set<String> listFiles = new HashSet<>();

        listFiles = Stream.of(new File(pathFile).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getPath)
                .collect(Collectors.toSet());

        log.info("\nList files: \n" + listFiles.toString());
        return listFiles;
    }

    public String read_file(String fileName) throws IOException {
        File file = new File(fileName);
        String result = "no data";

        try (RandomAccessFile reader = new RandomAccessFile(file, "r");
             FileChannel channel = reader.getChannel();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }

            ByteBuffer buff = ByteBuffer.allocate(bufferSize);
            while (channel.read(buff) != -1) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }
            result=new String(out.toString());

            log.debug(result);
        }
        return result;
    }

}
