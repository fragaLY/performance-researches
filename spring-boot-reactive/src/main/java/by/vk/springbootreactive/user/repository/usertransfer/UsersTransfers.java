package by.vk.springbootreactive.user.repository.usertransfer;

import by.vk.springbootreactive.transfer.repository.Transfer;
import by.vk.springbootreactive.user.repository.user.User;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "a2b", name = "users_transfers")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersTransfers {

  private UsersTransfersId id;
  private User user;
  private Transfer transfer;
  private State state;
  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UsersTransfers that)) {
      return false;
    }

    if (!Objects.equals(user, that.user)) {
      return false;
    }
    if (!Objects.equals(transfer, that.transfer)) {
      return false;
    }
    if (state != that.state) {
      return false;
    }
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    int result = user != null ? user.hashCode() : 0;
    result = 31 * result + (transfer != null ? transfer.hashCode() : 0);
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
