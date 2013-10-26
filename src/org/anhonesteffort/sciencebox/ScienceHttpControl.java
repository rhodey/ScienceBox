package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.active.Blower;
import org.anhonesteffort.sciencebox.language.Grammar;
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
public class ScienceHttpControl extends AbstractHandler {

  private static final String TARGET_TEMP_PARAM_NAME     = "temp";
  private static final String TARGET_HUMIDITY_PARAM_NAME = "humidity";
  private static final String BLOWER_STATUS_PARAM_NAME   = "blower";

  private TemperatureController tempControl;
  private HumidityController humidityControl;
  private Blower blower;

  public ScienceHttpControl(TemperatureController tempControl, HumidityController humidityControl, Blower blower) {
    this.tempControl = tempControl;
    this.humidityControl = humidityControl;
    this.blower = blower;
  }

  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);
    response.getWriter().println("<h1>ScienceBox!</h1>");

    response.getWriter().println("current temperature: " + tempControl.getLastReading() + "<br>");
    response.getWriter().println("current humidity: " + humidityControl.getLastReading() + "<br>");
    response.getWriter().println("current target temperature: " + tempControl.getTarget() + "<br>");
    response.getWriter().println("current target humidity: " + humidityControl.getTarget() + "<br>");

    if(baseRequest.getParameter(TARGET_TEMP_PARAM_NAME) != null) {
      double new_target = Double.parseDouble(baseRequest.getParameter(TARGET_TEMP_PARAM_NAME));
      response.getWriter().println("new target temperature: " + new_target + "<br>");
      tempControl.setTarget(new_target);
    }

    if(baseRequest.getParameter(TARGET_HUMIDITY_PARAM_NAME) != null) {
      double new_target = Double.parseDouble(baseRequest.getParameter(TARGET_HUMIDITY_PARAM_NAME));
      response.getWriter().println("new target humidity: " + new_target + "<br>");
      humidityControl.setTarget(new_target);
    }

    if(baseRequest.getParameter(BLOWER_STATUS_PARAM_NAME) != null) {
      if(baseRequest.getParameter(BLOWER_STATUS_PARAM_NAME).toUpperCase().equals("ON"))
        blower.onNewSetting(Grammar.SettingType.ON_OFF, 1);
      else
        blower.onNewSetting(Grammar.SettingType.ON_OFF, 0);
    }
  }
}
