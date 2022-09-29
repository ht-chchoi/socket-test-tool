package com.ht.sockettesttool.core.mainpage;

import com.ht.sockettesttool.core.data.XmlMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LayoutService {
  private final Map<String, XmlMessage> xmlMessageMap = new HashMap<>();
  private LayoutService() {
    this.xmlMessageMap.put("default", new XmlMessage(
        new XmlMessage.Header("", "", ""),
        new XmlMessage.Body(
            new XmlMessage.Body.Device("", "", "", "",
                List.of(
                    new XmlMessage.Body.Device.Service("", "", "", "", null))),
            new XmlMessage.Body.Return("", "", ""))));
  }

  private static class LazyHolder {
    public static final LayoutService INSTANCE = new LayoutService();
  }

  public static LayoutService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public String createXmlMessage(final String type) {
    if (type == null || !this.xmlMessageMap.containsKey(type)) {
      log.warn("no xmlMessage saved, type: {}", type);
      return null;
    }

    return this.xmlMessageToString(this.xmlMessageMap.get(type));
  }

  private String xmlMessageToString(final XmlMessage xmlMessage) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(XmlMessage.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(xmlMessage, stringWriter);
      return stringWriter.toString().substring(stringWriter.toString().indexOf("\n") + 1);
    } catch (JAXBException e) {
      log.error("fail to create xml string: ", e);
      return null;
    }
  }
}
