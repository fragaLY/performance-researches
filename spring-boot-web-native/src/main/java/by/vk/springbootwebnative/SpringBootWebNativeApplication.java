package by.vk.springbootwebnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(types = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class, access = {
    TypeAccess.RESOURCE})
public class SpringBootWebNativeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootWebNativeApplication.class, args);
  }

}
