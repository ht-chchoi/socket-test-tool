package com.ht.sockettesttool.core.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMessage {
  private Header header;
  private Body body;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Header {
    private String dest;
    private String type;
    private String feedback;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Body {
    private Device device;
    @XmlElement(name = "return")
    private Return returnXmlElement;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Device {
      private String uid;
      private String uidName;
      private String dong;
      private String ho;
      @XmlElement(name = "service")
      private List<Service> serviceList;

      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      public static class Service {
        private String name;
        private String argument;
        private String explicit;
        private String comment;
        private Object records;
      }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Return {
      private String name;
      private String argument;
      private String comment;
    }
  }
}
