import java.io.Serializable;

/**
 * Created by dionb on 6-2-2017.
 */
public abstract class Human implements Serializable {

  private String name;

  public Human(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
