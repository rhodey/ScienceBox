package org.anhonesteffort.sciencebox;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class ScienceHttpHandler extends AbstractHandler {

  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);
    response.getWriter().println("<h1>Hello World</h1>");
    response.getWriter().println("baseRequest: " + baseRequest + "<br>");
    response.getWriter().println("request: " + request + "<br>");
  }

}
