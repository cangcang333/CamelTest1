package com.spzhu.camel;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        HttpMessage message = (HttpMessage)exchange.getIn();
        InputStream bodyStream = (InputStream)message.getBody();
        String inputContext = this.analysisMessage(bodyStream);
        bodyStream.close();

        if (exchange.getPattern() == ExchangePattern.InOut) {
            Message outMessage = exchange.getOut();
            outMessage.setBody(inputContext + " || out");

        }

    }

    private String analysisMessage(InputStream bodyStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] contextBytes = new byte[4096];
        int realLen;
        while ((realLen = bodyStream.read(contextBytes, 0, 4096)) != -1) {
            outStream.write(contextBytes, 0, realLen);
        }

        try {
            return new String(outStream.toByteArray(), "UTF-8");

        } finally {
            outStream.close();
        }
    }
}
