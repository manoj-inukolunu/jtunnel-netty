package com.jtunnel.data;


import com.jtunnel.http.HttpRequest;
import com.jtunnel.http.HttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import java.nio.charset.Charset;
import java.util.HashMap;


public interface DataStore {

  default HttpRequest buildHttpRequest(FullHttpRequest httpRequest, String requestId) {
    HttpRequest request = new HttpRequest();
    request.setHttpHeaders(httpRequest.headers().entries());
    request.setTrailers(httpRequest.trailingHeaders().entries());
    request.setContent(httpRequest.content().toString(Charset.defaultCharset()));
    request.setRequestId(requestId);
    request.setInitialLine(httpRequest.method() + " " + httpRequest.uri() + " " + httpRequest.protocolVersion());
    return request;
  }

  default HttpResponse buildHttpResponse(FullHttpResponse httpResponse, String requestId) {
    HttpResponse response = new HttpResponse();
    response.setHttpHeaders(httpResponse.headers().entries());
    response.setTrailers(httpResponse.trailingHeaders().entries());
    response.setContent(httpResponse.content().toString(Charset.defaultCharset()));
    response.setRequestId(requestId);
    response.setInitialLine(httpResponse.protocolVersion() + " " + httpResponse.status());
    return response;
  }

  void add(String requestId, FullHttpRequest request) throws Exception;

  void saveFullTrace(String requestId, FullHttpResponse response) throws Exception;

  HashMap<HttpRequest, HttpResponse> allRequests() throws Exception;

  HttpRequest get(String requestId) throws Exception;

  HttpResponse getResponse(String requestId) throws Exception;
}
