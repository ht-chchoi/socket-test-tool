package com.ht.sockettesttool.core.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "message")
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final XmlMessage xmlMessage = new XmlMessage();

    public Builder dest(final String dest) {
      this.header().setDest(dest);
      return this;
    }

    public Builder type(final String type) {
      this.header().setType(type);
      return this;
    }

    public Builder feedback(final String feedback) {
      this.header().setFeedback(feedback);
      return this;
    }

    public Builder uid(final String uid) {
      this.device().setUid(uid);
      return this;
    }

    public Builder uidName(final String uidName) {
      this.device().setUidName(uidName);
      return this;
    }

    public Builder dong(final String dong) {
      this.device().setDong(dong);
      return this;
    }

    public Builder ho(final String ho) {
      this.device().setHo(ho);
      return this;
    }

    public Builder nameReturn(final String name) {
      this.returnXml().setName(name);
      return this;
    }

    public Builder argumentReturn(final String argument) {
      this.returnXml().setArgument(argument);
      return this;
    }

    public Builder commentReturn(final String comment) {
      this.returnXml().setComment(comment);
      return this;
    }

    public Builder nameService(final String name, final int serviceIndex) {
      try {
        this.serviceList().get(serviceIndex).setName(name);
      } catch (Exception e) {
        this.xmlMessage.getBody().getDevice().getServiceList().add(new Body.Device.Service());
        return nameService(name, serviceIndex);
      }
      return this;
    }

    public Builder argumentService(final String argument, final int serviceIndex) {
      try {
        this.serviceList().get(serviceIndex).setArgument(argument);
      } catch (Exception e) {
        this.xmlMessage.getBody().getDevice().getServiceList().add(new Body.Device.Service());
        return argumentService(argument, serviceIndex);
      }
      return this;
    }

    public Builder explicit(final String explicit, final int serviceIndex) {
      try {
        this.serviceList().get(serviceIndex).setExplicit(explicit);
      } catch (Exception e) {
        this.xmlMessage.getBody().getDevice().getServiceList().add(new Body.Device.Service());
        return explicit(explicit, serviceIndex);
      }
      return this;
    }

    public Builder commentService(final String comment, final int serviceIndex) {
      try {
        this.serviceList().get(serviceIndex).setComment(comment);
      } catch (Exception e) {
        this.xmlMessage.getBody().getDevice().getServiceList().add(new Body.Device.Service());
        return commentService(comment, serviceIndex);
      }
      return this;
    }

    public Builder records(final Object records, final int serviceIndex) {
      try {
        this.serviceList().get(serviceIndex).setRecords(records);
      } catch (Exception e) {
        this.xmlMessage.getBody().getDevice().getServiceList().add(new Body.Device.Service());
        return records(records, serviceIndex);
      }
      return this;
    }

    public XmlMessage build() {
      return this.xmlMessage;
    }

    private Header header() {
      if (this.xmlMessage.getHeader() == null) {
        this.xmlMessage.setHeader(new Header());
      }
      return this.xmlMessage.getHeader();
    }

    private Body body() {
      if (this.xmlMessage.getBody() == null) {
        this.xmlMessage.setBody(new Body());
      }
      return this.xmlMessage.getBody();
    }

    private Body.Device device() {
      if (this.body().getDevice() == null) {
        this.xmlMessage.getBody().setDevice(new Body.Device());
      }
      return this.xmlMessage.getBody().getDevice();
    }

    private Body.Return returnXml() {
      if (this.body().getReturnXmlElement() == null) {
        this.xmlMessage.getBody().setReturnXmlElement(new Body.Return());
      }
      return this.xmlMessage.getBody().getReturnXmlElement();
    }

    private List<Body.Device.Service> serviceList() {
      if (this.device().getServiceList() == null) {
        ArrayList<Body.Device.Service> serviceList = new ArrayList<>();
        serviceList.add(new Body.Device.Service());
        this.xmlMessage.getBody().getDevice().setServiceList(serviceList);
      }
      return this.xmlMessage.getBody().getDevice().getServiceList();
    }
  }
}
