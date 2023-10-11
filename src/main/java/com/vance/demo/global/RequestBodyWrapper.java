package com.vance.demo.global;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request重新包裝
 * 
 * @author Vance
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    private final Charset CHARSET = StandardCharsets.UTF_8;
    private String body;

    public RequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = new String(StreamUtils.copyToByteArray(request.getInputStream()), CHARSET);
    }

    /**
     * 重寫getInputStream，重body中取得請求
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(CHARSET));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
