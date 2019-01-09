package com.roshan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Afinn {

    public Map<String, Integer> affinWordBucket;

    public static final String AFINN_FILE = "AFINN.txt";

    private static final Logger LOG = LoggerFactory.getLogger(Afinn.class);

    public Afinn() {
        LOG.info("Loading AFINN words from file : {}", AFINN_FILE);
        affinWordBucket = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(AFINN_FILE).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().length() > 0) {
                    String[] wordParts = line.split("\t");
                    affinWordBucket.put(wordParts[0].trim(), Integer.parseInt(wordParts[1].trim()));
                }
            }
            scanner.close();
        } catch (IOException e) {
            LOG.error("Error reading {} File", AFINN_FILE);
            return;
        }
        LOG.info("Loading AFINN words from file : {} success. Words loaded : {}", AFINN_FILE, affinWordBucket.size());
    }

    public int getAffinSentimentRate(String contentText) {
        int rank = 0;
        String[] split = contentText.split(" ");
        try {
            for (int i = 0; i < split.length; i++) {
                if (affinWordBucket.containsKey(split[i])) {
                    rank += affinWordBucket.get(split[i]);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return rank;
    }


}
