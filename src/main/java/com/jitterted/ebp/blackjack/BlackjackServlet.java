package com.jitterted.ebp.blackjack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlackjackServlet implements Servlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
    String head = "<head><title>Blackjack</title></head>";
    String h1 = "<h1>Welcome to Blackjack!</h1>";
    String dealerDiv = "<div id='dealer'></div>";
    String playerDiv = "<div id='player'></div>";
    String bodyTag = "<body>" + h1 + dealerDiv + playerDiv + "</body>";
    response.getWriter().println("<html>" + head + bodyTag + "</html>");
  }

  @Override
  public void init(ServletConfig servletConfig) throws jakarta.servlet.ServletException {

  }

  @Override
  public ServletConfig getServletConfig() {
    return null;
  }

  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws jakarta.servlet.ServletException, IOException {

  }

  @Override
  public String getServletInfo() {
    return null;
  }

  @Override
  public void destroy() {

  }
}
