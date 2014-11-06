package com.picsauditing.employeeguard.lms.model.api;

public class Payload {

  private long id;
  private Command command;
  private String data;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Payload{" +
      "id=" + id +
      ", command=" + command +
      ", data='" + data + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Payload payload = (Payload) o;

    if (id != payload.id) return false;
    if (command != payload.command) return false;
    if (data != null ? !data.equals(payload.data) : payload.data != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + command.hashCode();
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }
}
