package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.Hardware;
import org.anhonesteffort.sciencebox.language.Grammar;
import org.anhonesteffort.sciencebox.specific.control.HumidityController;
import org.anhonesteffort.sciencebox.specific.control.TemperatureController;
import org.anhonesteffort.sciencebox.specific.hardware.Blower;
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

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("text/html;charset=utf-8");
    response.getWriter().println("<h1>ScienceBox!</h1>");

    response.getWriter().println("current temperature: " + tempControl.getLastReading() + "<br>");
    response.getWriter().println("current humidity: " + humidityControl.getLastReading() + "<br>");
    response.getWriter().println("target temperature: " + tempControl.getTargetReading() + "<br>");
    response.getWriter().println("target humidity: " + humidityControl.getTargetReading() + "<br>");

    if(baseRequest.getParameter(Grammar.TOKEN_CONTROL_TEMPERATURE) != null) {
      double new_target = Double.parseDouble(baseRequest.getParameter(Grammar.TOKEN_CONTROL_TEMPERATURE));
      response.getWriter().println("new target temperature: " + new_target + "<br>");
      tempControl.setTargetReading(new_target);
    }

    if(baseRequest.getParameter(Grammar.TOKEN_CONTROL_HUMIDITY) != null) {
      double new_target = Double.parseDouble(baseRequest.getParameter(Grammar.TOKEN_CONTROL_HUMIDITY));
      response.getWriter().println("new target humidity: " + new_target + "<br>");
      humidityControl.setTargetReading(new_target);
    }

    if(baseRequest.getParameter(Grammar.TOKEN_HARDWARE_FAN) != null) {
      double new_setting = Double.parseDouble(baseRequest.getParameter(Grammar.TOKEN_HARDWARE_FAN));
      blower.onNewSetting(Hardware.DataType.ON_OFF, new_setting);
      blower.onNewSetting(Hardware.DataType.ON_OFF, new_setting);
    }

    baseRequest.setHandled(true);
  }
}
