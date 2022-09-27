package by.vk.springbootreactivenative.user.repository.user;

import by.vk.springbootreactivenative.user.repository.usertransfer.UsersTransfers;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "a2b", name = "users")
@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  @ToString.Exclude
  private Set<UsersTransfers> transfers;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }

    if (!Objects.equals(firstName, user.firstName)) {
      return false;
    }
    if (!Objects.equals(lastName, user.lastName)) {
      return false;
    }
    if (!Objects.equals(email, user.email)) {
      return false;
    }
    if (!Objects.equals(phone, user.phone)) {
      return false;
    }
    return Objects.equals(transfers, user.transfers);
  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (transfers != null ? transfers.hashCode() : 0);
    return result;
  }
}
